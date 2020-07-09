package ro.alecsandru.bigbluebutton;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Wrapper for create webhook response:
 * @see  <a href="https://docs.bigbluebutton.org/dev/webhooks.html#hookscreate">Hooks/Create API</a>
 */
@Data
@NoArgsConstructor(access = AccessLevel.PACKAGE)
public class CreateWebhookResponse extends BaseResponse {
	private String hookID;
	private Boolean permanentHook;
	private Boolean rawData;
}
