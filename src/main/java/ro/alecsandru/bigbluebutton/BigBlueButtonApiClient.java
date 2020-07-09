package ro.alecsandru.bigbluebutton;

import java.io.Closeable;

public interface BigBlueButtonApiClient extends Closeable {
	CreateMeetingResponse createMeeting(CreateMeetingRequest createMeetingRequest);
	JoinMeetingResponse joinMeeting(JoinMeetingRequest joinMeetingRequest);
	GetMeetingsResponse getMeetings();
	GetMeetingInfoResponse getMeetingInfo(GetMeetingInfoRequest getMeetingInfoRequest);
	IsMeetingRunningResponse isMeetingRunning(IsMeetingRunningRequest isMeetingRunningRequest);
	EndMeetingResponse endMeeting(EndMeetingRequest endMeetingRequest);

	CreateWebhookResponse createWebhook(CreateWebhookRequest createWebhookRequest);
	DestroyWebhookResponse destroyWebhook(DestroyWebhookRequest destroyWebhookRequest);
	ListWebhooksResponse listWebhooks(ListWebhooksRequest listWebhooksRequest);
}
