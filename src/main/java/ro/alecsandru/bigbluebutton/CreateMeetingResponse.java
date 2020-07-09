package ro.alecsandru.bigbluebutton;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * Wrapper for create meeting response:
 * @see  <a href="https://docs.bigbluebutton.org/dev/api.html#create">Create meeting API</a>
 */
@Data
@NoArgsConstructor(access = AccessLevel.PACKAGE)
public class CreateMeetingResponse extends BaseResponse {
	private String meetingID;
	private String internalMeetingID;
	private String parentMeetingID;
	private String attendeePW;
	private String moderatorPW;
	private long createTime;
	private String voiceBridge;
	private String dialNumber;

	@JsonFormat(pattern="EEE MMM dd HH:mm:ss ZZZ yyyy")
	private Date createDate;
	private boolean hasUserJoined;
	private long duration;
	private boolean hasBeenForciblyEnded;
}
