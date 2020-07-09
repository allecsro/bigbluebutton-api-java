package ro.alecsandru.bigbluebutton;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.BufferedReader;
import java.io.Reader;
import java.lang.reflect.Field;
import java.util.AbstractMap;
import java.util.AbstractMap.SimpleEntry;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class PrometheusTextMetricsParser implements PrometheusMetricsParser {
	public Metrics parse(Reader reader) {
		Map<String, String> metricsMap = new BufferedReader(reader).lines()
			.filter(line -> !line.startsWith("#"))
			.map(line -> {
				String[] parts = line.split("\\s");
				if (parts.length == 2) {
					return new AbstractMap.SimpleEntry<>(parts[0], parts[1]);
				} else {
					return null;
				}
			})
			.filter(Objects::nonNull)
			.collect(Collectors.toMap(SimpleEntry::getKey,SimpleEntry::getValue));

		Metrics metrics = new Metrics();

		try {
			for (Field field : Metrics.class.getDeclaredFields()) {
				field.setAccessible(true);
				JsonProperty[] annotations = field.getAnnotationsByType(JsonProperty.class);
				String serializedName = annotations.length > 0 ? annotations[0].value() : field.getName();

				if (metricsMap.containsKey(serializedName)) {
					Object value = metricsMap.get(serializedName);
					if (field.getType().equals(int.class)) {
						value = (int)Float.parseFloat(metricsMap.get(serializedName));
					} else if (field.getType().equals(double.class)) {
						value = (double)Float.parseFloat(metricsMap.get(serializedName));
					} else if (field.getType().equals(float.class)) {
						value = Float.parseFloat(metricsMap.get(serializedName));
					}

					field.set(metrics, value);
				}
			}
		} catch (Exception e) {
			throw new BigBlueButtonException(e);
		}


		return metrics;
	}
}
