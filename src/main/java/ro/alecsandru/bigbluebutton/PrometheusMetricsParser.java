package ro.alecsandru.bigbluebutton;

import java.io.Reader;

public interface PrometheusMetricsParser {
	Metrics parse(Reader reader);
}
