package ro.alecsandru.bigbluebutton;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor(access = AccessLevel.PACKAGE)
public class GetMeetingsResponse extends BaseResponse {
	private List<Meeting> meetings;

	@Data
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	@JsonIgnoreProperties(ignoreUnknown = true)
	public static final class Meeting {
		private String meetingName;
		private String meetingID;
		private String internalMeetingID;
		private Long createTime;

		@JsonFormat(pattern = "EEE MMM dd HH:mm:ss ZZZ yyyy")
		private Date createDate;
		private String voiceBridge;
		private String dialNumber;
		private String attendeePW;
		private String moderatorPW;
		private Boolean running;
		private Long duration;
		private Boolean hasUserJoined;
		private Boolean recording;
		private Boolean autoStartRecording;
		private Boolean allowStartStopRecording;
		private Boolean hasBeenForciblyEnded;
		private Boolean lockSettingsDisablePrivateChat;
		private Boolean lockSettingsDisablePublicChat;
		private Boolean lockSettingsDisableNote;
		private Boolean lockSettingsDisableCam;
		private Boolean lockSettingsDisableMic;
		private Boolean lockSettingsLockedLayout;
		private Boolean lockSettingsLockOnJoin;
		private Boolean lockSettingsLockOnJoinConfigurable;
		private Boolean webcamsOnlyForModerator;
		private Boolean allowModsToUnmuteUsers;
		private Boolean muteOnStart;
		private Long startTime;
		private Long endTime;
		private Integer participantCount;
		private Integer listenerCount;
		private Integer voiceParticipantCount;
		private Integer videoCount;
		private Integer maxUsers;
		private Integer moderatorCount;
		private List<Attendee> attendees;
		@JsonIgnore
		private Map<String, String> metadata;
		private Boolean isBreakout;
		private String parentMeetingID;
		private Integer sequence;
		private Boolean freeJoin;
		private String welcome;
		private String copyright;
		private String bannerText;
		private String bannerColor;
		private String logo;
		private String logoutURL;
		private GuestPolicyType guestPolicy;
		private String moderatorOnlyMessage;
	}

	@Data
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	@JsonIgnoreProperties(ignoreUnknown = true)
	public static final class Attendee {
		private String userID;
		private String fullName;
		private String role;
		private Boolean isPresenter;
		private Boolean isListeningOnly;
		private Boolean hasJoinedVoice;
		private Boolean hasVideo;
		private String clientType;
	}
}