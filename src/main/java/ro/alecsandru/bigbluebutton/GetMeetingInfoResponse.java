package ro.alecsandru.bigbluebutton;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.Map;

/**
 * Wrapper for getMeetingInfoAnchor response:
 * @see  <a href="https://docs.bigbluebutton.org/dev/api.html#getmeetinginfo">getMeetingInfo API</a>
 */
@Data
@NoArgsConstructor(access = AccessLevel.PACKAGE)
public class GetMeetingInfoResponse extends BaseResponse {
	private String meetingName;
	private String meetingID;
	private String internalMeetingID;
	private Long createTime;

	@JsonFormat(pattern="EEE MMM dd HH:mm:ss ZZZ yyyy")
	private Date createDate;
	private String voiceBridge;
	private String dialNumber;
	private String attendeePW;
	private String moderatorPW;
	private Boolean running;
	private Long duration;
	private Boolean hasUserJoined;
	private Boolean recording;
	private Boolean hasBeenForciblyEnded;
	private Long startTime;
	private Long endTime;
	private Integer participantCount;
	private Integer listenerCount;
	private Integer voiceParticipantCount;
	private Integer videoCount;
	private Integer maxUsers;
	private Integer moderatorCount;
	private Integer attendees;

	@JsonIgnore
	private Map<String, String> metadata;
	private Boolean isBreakout;
}
