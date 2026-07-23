package ec.solmedia.shared.infrastructure.bus.event.kafka;

import ec.solmedia.shared.domain.event.bus.DomainEvent;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;

public class DomainEventKafkaDeserializer implements Deserializer<DomainEvent> {

  private final ObjectMapper objectMapper;

  public DomainEventKafkaDeserializer(ObjectMapper objectMapper) {
    this.objectMapper = objectMapper;
  }

  @Override
  public DomainEvent deserialize(String topic, byte[] data) {
    if (data == null) {
      return null;
    }

    try {
      final var envelope = objectMapper.readValue(data, Map.class);
      final var eventData = data(envelope);
      final var attributes = attributes(eventData);
      attributes.put(DomainEventKafkaEnvelope.DATA_TYPE, eventData.get(DomainEventKafkaEnvelope.DATA_TYPE));
      attributes.put(
          DomainEventKafkaEnvelope.AGGREGATE_ID,
          attributes.get(DomainEventKafkaEnvelope.ATTRIBUTE_ID)
      );
      attributes.remove(DomainEventKafkaEnvelope.ATTRIBUTE_ID);
      attributes.put(DomainEventKafkaEnvelope.EVENT_ID, eventData.get(DomainEventKafkaEnvelope.DATA_ID));
      attributes.put(DomainEventKafkaEnvelope.OCCURRED_ON, eventData.get(DomainEventKafkaEnvelope.DATA_OCCURRED_ON));

      return objectMapper.convertValue(attributes, DomainEvent.class);
    } catch (IOException exception) {
      throw new SerializationException("Error deserializing domain event", exception);
    }
  }

  @SuppressWarnings("unchecked")
  private Map<String, Object> data(Map<String, Object> envelope) {
    return (Map<String, Object>) envelope.get(DomainEventKafkaEnvelope.ENVELOPE_DATA);
  }

  @SuppressWarnings("unchecked")
  private Map<String, Object> attributes(Map<String, Object> eventData) {
    return new HashMap<>((Map<String, Object>) eventData.get(DomainEventKafkaEnvelope.DATA_ATTRIBUTES));
  }
}
