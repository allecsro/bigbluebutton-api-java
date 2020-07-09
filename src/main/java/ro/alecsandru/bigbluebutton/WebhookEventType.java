package ro.alecsandru.bigbluebutton;


import java.util.HashMap;

public enum WebhookEventType {
	MEETING_CREATED("meeting-created"),
	USER_JOINED("user-joined"),
	USER_AUDIO_VOICE_DISABLED("user-audio-voice-disabled"),
	USER_AUDIO_VOICE_ENABLED("user-audio-voice-enabled"),
	USER_PRESENTER_UNASSIGNED("user-presenter-unassigned"),
	USER_PRESENTER_ASSIGNED("user-presenter-assigned"),
	USER_CAM_BROADCAST_START("user-cam-broadcast-start"),
	USER_CAM_BROADCAST_END("user-cam-broadcast-end"),
	USER_LEFT("user-left"),
	MEETING_ENDED("meeting-ended");

	private static final HashMap<String, WebhookEventType> instances;

	static {
		instances = new HashMap<>();
		for (WebhookEventType instance : WebhookEventType.values()) {
			instances.put(instance.getName(), instance);
		}
	}

	public static WebhookEventType fromName(String name) {
		return instances.get(name);
	}

	private final String name;

	WebhookEventType(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
}
