package ro.alecsandru.bigbluebutton;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.BasicHttpEntity;
import org.apache.http.impl.client.CloseableHttpClient;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public abstract class BigBlueButtonBaseApiTest {

	abstract CloseableHttpClient getHttpClient();

	void setResponse(String responseString) {
		BasicHttpEntity httpEntity = new BasicHttpEntity();
		httpEntity.setContent(new ByteArrayInputStream(responseString.getBytes()));

		CloseableHttpResponse httpResponse = mock(CloseableHttpResponse.class);

		try {
			when(getHttpClient().execute((any(HttpGet.class)))).thenReturn(httpResponse);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		when(httpResponse.getEntity()).thenReturn(httpEntity);
	}
}
