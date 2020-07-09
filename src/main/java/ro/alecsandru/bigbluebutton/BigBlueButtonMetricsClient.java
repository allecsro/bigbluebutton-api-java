package ro.alecsandru.bigbluebutton;

import java.io.Closeable;

public interface BigBlueButtonMetricsClient extends Closeable {
	Metrics getMetrics();
}
