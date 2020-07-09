package ro.alecsandru.bigbluebutton;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

/**
 * Wrapper for creating a webhook:
 * @see  <a href="https://docs.bigbluebutton.org/dev/webhooks.html#hookscreate">Hooks/Create API</a>
 */
@Value
@Builder
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class CreateWebhookRequest {
	String meetingID;
	String callbackURL;
	Boolean getRaw;
}
