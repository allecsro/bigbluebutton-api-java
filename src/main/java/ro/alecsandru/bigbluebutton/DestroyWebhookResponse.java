package ro.alecsandru.bigbluebutton;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Wrapper for destroy webhook response:
 * @see  <a href="https://docs.bigbluebutton.org/dev/webhooks.html#hooksdestroy">Hooks/Destroy API</a>
 */
@Data
@NoArgsConstructor(access = AccessLevel.PACKAGE)
public class DestroyWebhookResponse extends BaseResponse {
	private Boolean removed;
}
