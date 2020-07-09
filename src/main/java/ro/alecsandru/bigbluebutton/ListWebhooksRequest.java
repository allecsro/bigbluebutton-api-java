package ro.alecsandru.bigbluebutton;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

/**
 * Wrapper for listing webhooks:
 * @see  <a href="https://docs.bigbluebutton.org/dev/webhooks.html#hookslist">Hooks/List API</a>
 */
@Value
@Builder
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class ListWebhooksRequest {
	String meetingID;
}
