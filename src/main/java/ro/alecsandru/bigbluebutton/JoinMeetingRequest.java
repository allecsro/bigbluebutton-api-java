package ro.alecsandru.bigbluebutton;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

import java.util.Date;

/**
 * Wrapper for join meeting request:
 * @see  <a href="https://docs.bigbluebutton.org/dev/api.html#join">Join meeting API</a>
 */
@Value
@Builder
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class JoinMeetingRequest {
	String fullName;
	String meetingID;
	String password;
	Date createTime;
	String userID;
	String webVoiceConf;
	String configToken;
	String defaultLayout;
	String avatarURL;
	Boolean redirect;
	String clientURL;
	Boolean joinViaHtml5;
	Boolean guest;

	@JsonProperty("userdata-bbb_show_participants_on_login")
	Boolean showParticipantsOnLogin;

	@JsonProperty("userdata-bbb_hide_presentation")
	Boolean hidePresentation;

	@JsonProperty("userdata-bbb_auto_swap_layout")
	Boolean autoSwapLayout;

	@JsonProperty("userdata-bbb_custom_style_url")
	String customCssStyleUrl;

	@JsonProperty("userdata-bbb_custom_style")
	String customCssStyle;
}
