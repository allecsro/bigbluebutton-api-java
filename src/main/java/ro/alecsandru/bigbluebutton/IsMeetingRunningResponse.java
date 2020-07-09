package ro.alecsandru.bigbluebutton;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class IsMeetingRunningResponse extends BaseResponse {
	boolean running;
}
