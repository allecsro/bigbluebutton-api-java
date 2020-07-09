package ro.alecsandru.bigbluebutton;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Wrapper for listing webhooks response:
 * @see  <a href="https://docs.bigbluebutton.org/dev/webhooks.html#hookslist">Hooks/List API</a>
 */
@Data
@NoArgsConstructor(access = AccessLevel.PACKAGE)
public class ListWebhooksResponse extends BaseResponse {
	private List<Hook> hooks;

	@Data
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	public static final class Hook {
		private String hookID;
		private String callbackURL;
		private String meetingID;
		private Boolean permanentHook;
		private Boolean rawData;
	}
}