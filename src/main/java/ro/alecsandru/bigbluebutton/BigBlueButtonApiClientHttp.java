package ro.alecsandru.bigbluebutton;

import lombok.NonNull;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.impl.client.CloseableHttpClient;

import java.io.IOException;

public class BigBlueButtonApiClientHttp extends BigBlueButtonApiClientHttpBase implements BigBlueButtonApiClient {

	public BigBlueButtonApiClientHttp(String secret, String endpoint, HttpClientConnectionManager connectionManager) {
		super(secret, endpoint, connectionManager);
	}

	BigBlueButtonApiClientHttp(String secret, String endpoint, CloseableHttpClient httpClient) {
		super(secret, endpoint, httpClient);
	}

	@Override
	public void close() throws IOException {
		super.close();
	}

	@Override
	public CreateMeetingResponse createMeeting(@NonNull CreateMeetingRequest createMeetingRequest) {
		return doApiRequest("create", createMeetingRequest, CreateMeetingResponse.class);
	}

	@Override
	public GetMeetingInfoResponse getMeetingInfo(@NonNull GetMeetingInfoRequest getMeetingInfoRequest) {
		return doApiRequest("getMeetingInfo", getMeetingInfoRequest, GetMeetingInfoResponse.class);
	}

	@Override
	public IsMeetingRunningResponse isMeetingRunning(@NonNull IsMeetingRunningRequest isMeetingRunningRequest) {
		return doApiRequest("isMeetingRunning", isMeetingRunningRequest, IsMeetingRunningResponse.class);

	}

	@Override
	public EndMeetingResponse endMeeting(@NonNull EndMeetingRequest endMeetingRequest) {
		return doApiRequest("end", endMeetingRequest, EndMeetingResponse.class);
	}

	@Override
	public JoinMeetingResponse joinMeeting(@NonNull JoinMeetingRequest joinMeetingRequest) {
		return doApiRequest("join", joinMeetingRequest, JoinMeetingResponse.class);
	}

	@Override
	public GetMeetingsResponse getMeetings() {
		return doApiRequest("getMeetings", new Object(), GetMeetingsResponse.class);
	}

	@Override
	public CreateWebhookResponse createWebhook(CreateWebhookRequest createWebhookRequest) {
		return doApiRequest("hooks/create", createWebhookRequest, CreateWebhookResponse.class);
	}

	@Override
	public DestroyWebhookResponse destroyWebhook(DestroyWebhookRequest destroyWebhookRequest) {
		return doApiRequest("hooks/destroy", destroyWebhookRequest, DestroyWebhookResponse.class);
	}

	@Override
	public ListWebhooksResponse listWebhooks(ListWebhooksRequest listWebhooksRequest) {
		return doApiRequest("hooks/list", listWebhooksRequest, ListWebhooksResponse.class);
	}
}
