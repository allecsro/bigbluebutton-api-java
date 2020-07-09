package ro.alecsandru.bigbluebutton;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class WebhookNotificationParserTest {

	private WebhookNotificationParser webhookNotificationParser;

	@BeforeEach
	void setUp() {
		webhookNotificationParser = new WebhookNotificationParser();
	}

	@Test
    void testParseSingle() throws Exception {
		String eventString = ("{" +
			"'data':{" +
				"'type':'event'," +
				"'id':'user-audio-voice-disabled'," +
				"'attributes':{" +
					"'meeting':{" +
						"'internal-meeting-id':'f4bb6c99171239942e6504510759325575fc12d4-1588748434827'," +
						"'external-meeting-id':'2d0263276c35fda3c05ccbca426f3c053f261cc6'" +
					"}," +
					"'user':{" +
						"'internal-user-id':'w_86dmmtzncydq'," +
						"'external-user-id':'w_86dmmtzncydq'," +
						"'listening-only':false," +
						"'sharing-mic':false" +
					"}" +
				"}," +
				"'event':{" +
					"'ts':1588749522911" +
				"}" +
			"}" +
		"}").replaceAll("'", "\"");

		List<WebhookNotification> notifications = webhookNotificationParser.parse(eventString);

		assertEquals(1, notifications.size());
		assertEquals(WebhookEventType.USER_AUDIO_VOICE_DISABLED.getName(), notifications.get(0).id);
	}

	@Test
	void testParserMultiple() throws Exception {
		String eventString = (
			"[" +
				"{" +
					"'data':{" +
						"'type':'event'," +
						"'id':'meeting-created'," +
						"'attributes':{" +
							"'meeting':{" +
								"'internal-meeting-id':'219eccfbd99768b067919ab6180aa4412890549a-1588757596393'," +
								"'external-meeting-id':'8d319a9d808711ea947b0242ac1400073b6e12c9'," +
								"'name':'Mon salon'," +
								"'is-breakout':false," +
								"'duration':0," +
								"'create-time':1588757596393," +
								"'create-date':'Wed May 06 09:33:16 UTC 2020'," +
								"'moderator-pass':'fe214679'," +
								"'viewer-pass':'83867982'," +
								"'record':false," +
								"'voice-conf':'36239'," +
								"'dial-number':'613-555-1234'," +
								"'max-users':20," +
								"'metadata':{}" +
							"}" +
						"}," +
						"'event':{" +
							"'ts':1588757596424" +
						"}" +
					"}" +
				"}" +
			"]"
		).replaceAll("'", "\"");

		List<WebhookNotification> notifications = webhookNotificationParser.parse(eventString);

		assertEquals(1, notifications.size());
		assertEquals(WebhookEventType.MEETING_CREATED.getName(), notifications.get(0).id);
	}
}