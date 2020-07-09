package ro.alecsandru.bigbluebutton;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

import java.util.Map;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class WebhookNotification {
	String id;
	String type;
	Event event;
	Attributes attributes;

	@Data
	@JsonIgnoreProperties(ignoreUnknown = true)
	public static final class Event {
		private long ts;
	}

	@Data
	@JsonIgnoreProperties(ignoreUnknown = true)
	public static final class Attributes {
		private Meeting meeting;
		private User user;
	}

	@Data
	@JsonNaming(PropertyNamingStrategy.KebabCaseStrategy.class)
	@JsonIgnoreProperties(ignoreUnknown = true)
	public static final class Meeting {
		private String internalMeetingId;
		private String externalMeetingId;
		private String name;
		private String moderatorPass;
		private String viewerPass;
		private Boolean isBreakout;
		private Long duration;
		private Long createTime;
		private Boolean record;
		private Integer maxUsers;
		private String voiceConf;
		private String dialNumber;
		private Map<String, String> metadata;
	}

	@Data
	@JsonNaming(PropertyNamingStrategy.KebabCaseStrategy.class)
	@JsonIgnoreProperties(ignoreUnknown = true)
	public static final class User {
		private String internalUserId;
		private String externalUserId;
		private Boolean listeningOnly;
		private Boolean sharingMic;
	}
}
