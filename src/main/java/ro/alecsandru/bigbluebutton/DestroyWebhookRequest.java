package ro.alecsandru.bigbluebutton;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

/**
 * Wrapper for destroy webhook request:
 * @see  <a href="https://docs.bigbluebutton.org/dev/webhooks.html#hooksdestroy">Hooks/Destroy API</a>
 */
@Value
@Builder
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class DestroyWebhookRequest {
	String hookID;
}
