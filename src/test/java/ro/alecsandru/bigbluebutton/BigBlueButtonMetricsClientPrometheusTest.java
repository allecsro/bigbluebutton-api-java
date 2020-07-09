package ro.alecsandru.bigbluebutton;

import lombok.AccessLevel;
import lombok.Getter;
import org.apache.http.impl.client.CloseableHttpClient;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class BigBlueButtonMetricsClientPrometheusTest extends BigBlueButtonBaseApiTest{

	private BigBlueButtonMetricsClientPrometheus client;

	@Mock
	@Getter(AccessLevel.PACKAGE)
	private CloseableHttpClient httpClient;


	@BeforeEach
	void setUp() {
		final String endpoint = "https://acme.com/metrics";
		this.client = new BigBlueButtonMetricsClientPrometheus(endpoint, httpClient);
	}

	@Test
	void getMetrics() {
		setResponse(
			"# HELP process_max_fds Maximum number of open file descriptors.\n" +
			"# TYPE process_max_fds gauge\n" +
			"process_max_fds 1.048576e+06\n" +
			"# HELP bbb_meetings Number of BigBlueButton meetings\n" +
			"# TYPE bbb_meetings gauge\n" +
			"bbb_meetings 5.0\n" +
			"# HELP bbb_meetings_participants Total number of participants in all BigBlueButton meetings\n" +
			"# TYPE bbb_meetings_participants gauge\n" +
			"bbb_meetings_participants 1.0\n" +
			"# HELP bbb_meetings_listeners Total number of listeners in all BigBlueButton meetings\n" +
			"# TYPE bbb_meetings_listeners gauge\n" +
			"bbb_meetings_listeners 0.0\n" +
			"# HELP bbb_meetings_voice_participants Total number of voice participants in all BigBlueButton meetings\n" +
			"# TYPE bbb_meetings_voice_participants gauge\n" +
			"bbb_meetings_voice_participants 1.0\n" +
			"# HELP bbb_meetings_video_participants Total number of video participants in all BigBlueButton meetings\n" +
			"# TYPE bbb_meetings_video_participants gauge\n" +
			"bbb_meetings_video_participants 0.0\n" +
			"# HELP bbb_meetings_participant_clients Total number of participants in all BigBlueButton meetings by client\n" +
			"# TYPE bbb_meetings_participant_clients gauge\n" +
			"bbb_meetings_participant_clients{type=\"html5\"} 1.0\n" +
			"bbb_meetings_participant_clients{type=\"dial-in\"} 0.0\n" +
			"bbb_meetings_participant_clients{type=\"flash\"} 0.0\n" +
			"# HELP bbb_recordings_processing Total number of BigBlueButton recordings processing\n" +
			"# TYPE bbb_recordings_processing gauge\n" +
			"bbb_recordings_processing 16.0\n" +
			"# HELP bbb_recordings_processed Total number of BigBlueButton recordings processed\n" +
			"# TYPE bbb_recordings_processed gauge\n" +
			"bbb_recordings_processed 100.0\n" +
			"# HELP bbb_recordings_published Total number of BigBlueButton recordings published\n" +
			"# TYPE bbb_recordings_published gauge\n" +
			"bbb_recordings_published 1600.0\n" +
			"# HELP bbb_recordings_unpublished Total number of BigBlueButton recordings unpublished\n" +
			"# TYPE bbb_recordings_unpublished gauge\n" +
			"bbb_recordings_unpublished 5000.0\n" +
			"# HELP bbb_recordings_deleted Total number of BigBlueButton recordings deleted\n" +
			"# TYPE bbb_recordings_deleted gauge\n" +
			"bbb_recordings_deleted 120.0"
		);

		Metrics metrics = client.getMetrics();

		Assertions.assertNotNull(metrics);
		Assertions.assertEquals(5, metrics.getMeetingsCount());
		Assertions.assertEquals(1, metrics.getParticipantsCount());
		Assertions.assertEquals(0, metrics.getListenersCount());
		Assertions.assertEquals(1, metrics.getVoiceParticipantsCount());
		Assertions.assertEquals(0, metrics.getVideoParticipantsCount());
		Assertions.assertEquals(100, metrics.getRecordingsProcessedCount());
		Assertions.assertEquals(16, metrics.getRecordingsProcessingCount());
		Assertions.assertEquals(1600, metrics.getRecordingsPublishedCount());
		Assertions.assertEquals(5000, metrics.getRecordingsUnpublishedCount());
		Assertions.assertEquals(120, metrics.getRecordingsDeletedCount());
	}

}