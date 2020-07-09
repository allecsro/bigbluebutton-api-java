package ro.alecsandru.bigbluebutton;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Wrapper for create meeting response:
 * @see  <a href="https://docs.bigbluebutton.org/dev/api.html#create">Create meeting API</a>
 */
@Data
@NoArgsConstructor(access = AccessLevel.PACKAGE)
public class JoinMeetingResponse extends BaseResponse {
	@JsonProperty("meeting_id")
	private String internalMeetingID;

	@JsonProperty("user_id")
	private String userID;

	@JsonProperty("auth_token")
	private String authToken;

	@JsonProperty("session_token")
	private String sessionToken;

	private String url;

	private String guestStatus;
}
