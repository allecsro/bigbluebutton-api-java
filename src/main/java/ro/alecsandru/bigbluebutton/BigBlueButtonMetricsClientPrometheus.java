package ro.alecsandru.bigbluebutton;

import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;

public class BigBlueButtonMetricsClientPrometheus implements BigBlueButtonMetricsClient {

	private final String endpoint;
	private final CloseableHttpClient httpClient;
	private final PrometheusMetricsParser parser;

	public BigBlueButtonMetricsClientPrometheus(
			String username, String password, String endpoint, HttpClientConnectionManager connectionManager) {

		CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
		credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(username, password));

		this.endpoint = endpoint;
		this.httpClient = HttpClientBuilder
			.create()
			.setConnectionManager(connectionManager)
			.setDefaultCredentialsProvider(credentialsProvider)
			.disableRedirectHandling()
			.setDefaultRequestConfig(
				RequestConfig.custom()
					.setSocketTimeout(500)
					.setConnectTimeout(500)
					.setConnectionRequestTimeout(500)
					.setCookieSpec(CookieSpecs.IGNORE_COOKIES)
					.build()
			)
			.build();

		this.parser = new PrometheusTextMetricsParser();
	}

	BigBlueButtonMetricsClientPrometheus(String endpoint, CloseableHttpClient httpClient) {
		this.endpoint = endpoint;
		this.httpClient = httpClient;

		this.parser = new PrometheusTextMetricsParser();
	}

	@Override
	public void close() throws IOException {
        this.httpClient.close();
	}

	@Override
	public Metrics getMetrics() {
		HttpGet request = new HttpGet(endpoint);

		try (CloseableHttpResponse response = httpClient.execute(request)) {
			try (Reader reponseReader = new InputStreamReader(response.getEntity().getContent(), StandardCharsets.UTF_8)) {
				return parser.parse(reponseReader);
			}
		} catch (IOException e) {
			throw new BigBlueButtonException("Failed to get BigBlueButton metrics", e);
		}
	}
}
