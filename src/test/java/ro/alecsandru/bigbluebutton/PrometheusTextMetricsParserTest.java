package ro.alecsandru.bigbluebutton;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class PrometheusTextMetricsParserTest {

	private PrometheusMetricsParser parser;

	@BeforeEach
	void setUp() {
		this.parser = new PrometheusTextMetricsParser();
	}

	@Test
	void ignoreComments() {
		String rawMetrics =
			"# HELP bbb_meetings Number of BigBlueButton meetings\n" +
				"# TYPE bbb_meetings gauge\n" +
				"bbb_meetings 1.0";

		Metrics metrics =
			this.parser.parse(new InputStreamReader(new ByteArrayInputStream(rawMetrics.getBytes()), UTF_8));

		assertNotNull(metrics);
		assertEquals(1, metrics.getMeetingsCount());
	}

	@Test
	void unknownMetric() {
		String rawMetrics = "dummy 1.0";

		Metrics metrics =
			this.parser.parse(new InputStreamReader(new ByteArrayInputStream(rawMetrics.getBytes()), UTF_8));

		assertNotNull(metrics);
	}

	@Test
	void allMetrics() {
		String rawMetrics =
			"# HELP bbb_meetings Number of BigBlueButton meetings\n" +
			"# TYPE bbb_meetings gauge\n" +
			"bbb_meetings 1.0\n" +
			"# HELP bbb_meetings_participants Total number of participants in all BigBlueButton meetings\n" +
			"# TYPE bbb_meetings_participants gauge\n" +
			"bbb_meetings_participants 1.0\n" +
			"# HELP bbb_meetings_listeners Total number of listeners in all BigBlueButton meetings\n" +
			"# TYPE bbb_meetings_listeners gauge\n" +
			"bbb_meetings_listeners 2.0\n" +
			"# HELP bbb_meetings_voice_participants Total number of voice participants in all BigBlueButton meetings\n" +
			"# TYPE bbb_meetings_voice_participants gauge\n" +
			"bbb_meetings_voice_participants 1.0\n" +
			"# HELP bbb_meetings_video_participants Total number of video participants in all BigBlueButton meetings\n" +
			"# TYPE bbb_meetings_video_participants gauge\n" +
			"bbb_meetings_video_participants 1.0\n" +
			"# HELP bbb_recordings_processing Total number of BigBlueButton recordings processing\n" +
			"# TYPE bbb_recordings_processing gauge\n" +
			"bbb_recordings_processing 0.0\n" +
			"# HELP bbb_recordings_processed Total number of BigBlueButton recordings processed\n" +
			"# TYPE bbb_recordings_processed gauge\n" +
			"bbb_recordings_processed 0.0\n" +
			"# HELP bbb_recordings_published Total number of BigBlueButton recordings published\n" +
			"# TYPE bbb_recordings_published gauge\n" +
			"bbb_recordings_published 500000.0\n" +
			"# HELP bbb_recordings_unpublished Total number of BigBlueButton recordings unpublished\n" +
			"# TYPE bbb_recordings_unpublished gauge\n" +
			"bbb_recordings_unpublished 0.0\n" +
			"# HELP bbb_recordings_deleted Total number of BigBlueButton recordings deleted\n" +
			"# TYPE bbb_recordings_deleted gauge\n" +
			"bbb_recordings_deleted 0.0";

		Metrics metrics =
			this.parser.parse(new InputStreamReader(new ByteArrayInputStream(rawMetrics.getBytes()), UTF_8));

		assertNotNull(metrics);
		assertEquals(1, metrics.getMeetingsCount());
		assertEquals(2, metrics.getListenersCount());
		assertEquals(1, metrics.getParticipantsCount());
		assertEquals(1, metrics.getVideoParticipantsCount());
		assertEquals(1, metrics.getVoiceParticipantsCount());
		assertEquals(0, metrics.getRecordingsDeletedCount());
		assertEquals(0, metrics.getRecordingsProcessedCount());
		assertEquals(500000, metrics.getRecordingsPublishedCount());
		assertEquals(0, metrics.getRecordingsUnpublishedCount());
		assertEquals(0, metrics.getRecordingsUnpublishedCount());
	}
}