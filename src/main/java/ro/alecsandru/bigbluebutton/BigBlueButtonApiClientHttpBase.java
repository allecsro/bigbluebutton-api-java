package ro.alecsandru.bigbluebutton;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.xml.deser.FromXmlParser.Feature;
import lombok.NonNull;
import lombok.SneakyThrows;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.IOUtils;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import static java.nio.charset.StandardCharsets.UTF_8;

class BigBlueButtonApiClientHttpBase implements Closeable {
	private static final String CHECKSUM_PARAMETER = "checksum";

	private final XmlMapper xmlMapper;
	protected final CloseableHttpClient httpClient;

	private final String secret;
	private final String endpoint;

	public BigBlueButtonApiClientHttpBase(
			@NonNull String secret, @NonNull String endpoint,
			@NonNull HttpClientConnectionManager connectionManager) {
		this(
			secret,
			endpoint,
			HttpClientBuilder
				.create()
				.setConnectionManager(connectionManager)
				.disableRedirectHandling()
				.setDefaultRequestConfig(
					RequestConfig.custom()
						.setSocketTimeout(500)
						.setConnectTimeout(500)
						.setConnectionRequestTimeout(500)
						.setCookieSpec(CookieSpecs.IGNORE_COOKIES)
						.build()
				)
				.build()
		);
	}

	BigBlueButtonApiClientHttpBase(
		@NonNull String secret, @NonNull String endpoint, @NonNull CloseableHttpClient httpClient) {
		this.secret = secret;
		this.endpoint = endpoint;
		this.httpClient = httpClient;

		this.xmlMapper = new XmlMapper();
		this.xmlMapper.enable(Feature.EMPTY_ELEMENT_AS_NULL);
		this.xmlMapper.enable(DeserializationFeature.ACCEPT_EMPTY_ARRAY_AS_NULL_OBJECT);
		this.xmlMapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
		this.xmlMapper.setLocale(Locale.ENGLISH);
	}

	@Override
	public void close() throws IOException {
        this.httpClient.close();
	}

	@SneakyThrows
	<T extends  BaseResponse> T doApiRequest(String command, Object data, Class<T> responseType) {
		HttpGet request = new HttpGet(endpoint + "/" + command);

		// Add the parameters to the request query string
		List<NameValuePair> parameters = this.getRequestParameters(data);
		String checksum = this.buildChecksum(command, parameters);
		parameters.add(new BasicNameValuePair(CHECKSUM_PARAMETER, checksum));

		try {
			URI uri = new URIBuilder(request.getURI()).addParameters(parameters).build();
			request.setURI(uri);
		} catch (URISyntaxException e) {
			throw new BigBlueButtonException(e);
		}

		try (CloseableHttpResponse response = httpClient.execute(request)) {
			try (Reader responseReader = new InputStreamReader(response.getEntity().getContent(), UTF_8)) {
				// Remove new line characters are these create some issue with deserialisation of empty arrays
				String responseString = IOUtils.toString(responseReader)
					.replace("\n", "")
					.replace("\r", "");

				try (StringReader reader = new StringReader(responseString)) {
					T apiResponse = xmlMapper.readValue(reader, responseType);

					if (apiResponse.getReturncode() == null || !apiResponse.getReturncode().equals("SUCCESS")) {
						throw new BigBlueButtonException(apiResponse.getMessage());
					}

					return apiResponse;
				}
			}
		} catch (IOException e) {
			throw new BigBlueButtonException("Failed BigBlueButton request: " + command, e);
		}
	}

	String buildChecksum(String name, List<NameValuePair> queryParams) {
		String queryString = queryParams.stream()
			.map(p -> p.getName() + "=" + encodeValue(p.getValue()))
			.collect(Collectors.joining("&"));

		return DigestUtils.sha1Hex(name + queryString + this.secret);
	}

	List<NameValuePair> getRequestParameters(Object obj) {
		List<NameValuePair> queryParams = new ArrayList<>();

		try {
			for (Field f : obj.getClass().getDeclaredFields()) {
				f.setAccessible(true);
				if (f.get(obj) != null) {
					JsonProperty[] annotations = f.getAnnotationsByType(JsonProperty.class);

					String serializedName = annotations.length > 0 ? annotations[0].value() : f.getName();

					queryParams.add(new BasicNameValuePair(serializedName, String.valueOf(f.get(obj))));
				}
			}
		} catch (Exception e) {
			throw new BigBlueButtonException(e);
		}

		return queryParams;
	}

	private static String encodeValue(String value) {
		try {
			return URLEncoder.encode(value, UTF_8.toString());
		} catch (UnsupportedEncodingException ex) {
			throw new BigBlueButtonException(ex.getCause());
		}
	}
}
