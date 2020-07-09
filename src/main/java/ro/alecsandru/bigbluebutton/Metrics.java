package ro.alecsandru.bigbluebutton;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Metrics {
	/** Number of BigBlueButton meetings */
	@JsonProperty("bbb_meetings")
	private int meetingsCount;

	/** Total number of participants in all BigBlueButton meetings */
	@JsonProperty("bbb_meetings_participants")
	private int participantsCount;

	/** Total number of listeners in all BigBlueButton meetings */
	@JsonProperty("bbb_meetings_listeners")
	private int listenersCount;

	/** Total number of voice participants in all BigBlueButton meetings */
	@JsonProperty("bbb_meetings_voice_participants")
	private int voiceParticipantsCount;

	/** Total number of video participants in all BigBlueButton meetings */
	@JsonProperty("bbb_meetings_video_participants")
	private int videoParticipantsCount;

	/** Total number of BigBlueButton recordings processing */
	@JsonProperty("bbb_recordings_processing")
	private int recordingsProcessingCount;

	/** Total number of BigBlueButton recordings processed */
	@JsonProperty("bbb_recordings_processed")
	private int recordingsProcessedCount;

	/** Total number of BigBlueButton recordings published */
	@JsonProperty("bbb_recordings_published")
	private int recordingsPublishedCount;

	/** Total number of BigBlueButton recordings unpublished */
	@JsonProperty("bbb_recordings_unpublished")
	private int recordingsUnpublishedCount;

	/** Total number of BigBlueButton recordings deleted */
	@JsonProperty("bbb_recordings_deleted")
	private int recordingsDeletedCount;
}
