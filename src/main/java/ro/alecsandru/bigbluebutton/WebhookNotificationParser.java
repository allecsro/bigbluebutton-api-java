package ro.alecsandru.bigbluebutton;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NonNull;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static java.util.Collections.singletonList;

public class WebhookNotificationParser {
	private static final String DATA_FIELD = "data";

	private final ObjectMapper objectMapper;

	public WebhookNotificationParser() {
		this.objectMapper = new ObjectMapper();
	}

	public List<WebhookNotification> parse(@NonNull String json) throws IOException {
		JsonNode jsonNode = objectMapper.readTree(json);
		if (jsonNode.isArray()) {
			Spliterator<JsonNode> spliterator = Spliterators.spliteratorUnknownSize(jsonNode.elements(), 0);
			return StreamSupport.stream(spliterator, false)
				.map(node -> node.get(DATA_FIELD))
				.map(node -> {
					try {
						return objectMapper.treeToValue(node, WebhookNotification.class);
					} catch (JsonProcessingException e) {
						throw new BigBlueButtonException(e);
					}
				})
				.filter(Objects::nonNull)
				.collect(Collectors.toList());
		} else {
			return singletonList(objectMapper.treeToValue(jsonNode.get(DATA_FIELD), WebhookNotification.class));
		}
	}
}
