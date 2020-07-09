package ro.alecsandru.bigbluebutton;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

import java.util.Map;

/**
 * Wrapper for create meeting request:
 * @see  <a href="https://docs.bigbluebutton.org/dev/api.html#create">Create meeting API</a>
 */
@Value
@Builder
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class CreateMeetingRequest {
	String name;
	String meetingID;
	String attendeePW;
	String moderatorPW;
	String welcome;
	String dialNumber;
	String voiceBridge;
	Integer maxParticipants;
	String logoutURL;
	Boolean record;
	Long duration;
	Boolean isBreakout;
	String parentMeetingID;
	Integer sequence;
	Boolean freeJoin;
	String moderatorOnlyMessage;
	Boolean autoStartRecording;
	Boolean allowStartStopRecording;
	Boolean webcamsOnlyForModerator;
	String logo;
	String bannerText;
	String bannerColor;
	String copyright;
	Boolean muteOnStart;
	Boolean allowModsToUnmuteUsers;
	Boolean lockSettingsDisableCam;
	Boolean lockSettingsDisableMic;
	Boolean lockSettingsDisablePrivateChat;
	Boolean lockSettingsDisablePublicChat;
	Boolean lockSettingsDisableNote;
	Boolean lockSettingsLockedLayout;
	Boolean lockSettingsLockOnJoin;
	Boolean lockSettingsLockOnJoinConfigurable;
	GuestPolicyType guestPolicy;

	@JsonIgnore
	Map<String, String> meta;
}
