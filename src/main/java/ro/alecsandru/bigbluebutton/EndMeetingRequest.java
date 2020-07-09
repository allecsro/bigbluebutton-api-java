package ro.alecsandru.bigbluebutton;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class EndMeetingRequest {
	String meetingID;
	String password;
}
