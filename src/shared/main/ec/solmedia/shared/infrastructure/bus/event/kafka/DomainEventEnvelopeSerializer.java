package ec.solmedia.shared.infrastructure.bus.event.kafka;

import ec.solmedia.shared.domain.event.bus.DomainEvent;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.HashMap;
import java.util.Map;

public class DomainEventEnvelopeSerializer {

  private final ObjectMapper objectMapper;

  public DomainEventEnvelopeSerializer(ObjectMapper objectMapper) {
    this.objectMapper = objectMapper;
  }

  public String serialize(DomainEvent domainEvent) {
    try {
      return objectMapper.writeValueAsString(envelope(domainEvent));
    } catch (JsonProcessingException exception) {
      throw new RuntimeException("Error serializing domain event", exception);
    }
  }

  private Map<String, Object> envelope(DomainEvent domainEvent) {
    final var attributes = eventAttributes(domainEvent);
    final var data = new HashMap<String, Object>();
    data.put(DomainEventKafkaEnvelope.DATA_ID, domainEvent.eventId());
    data.put(DomainEventKafkaEnvelope.DATA_TYPE, domainEvent.eventName());
    data.put(DomainEventKafkaEnvelope.DATA_OCCURRED_ON, domainEvent.occurredOn());
    data.put(DomainEventKafkaEnvelope.DATA_ATTRIBUTES, attributes);

    final var envelope = new HashMap<String, Object>();
    envelope.put(DomainEventKafkaEnvelope.ENVELOPE_DATA, data);
    envelope.put(DomainEventKafkaEnvelope.ENVELOPE_META, new HashMap<>());

    return envelope;
  }

  private Map<String, Object> eventAttributes(DomainEvent domainEvent) {
    final var attributes = objectMapper.convertValue(domainEvent, HashMap.class);
    attributes.remove(DomainEventKafkaEnvelope.DATA_TYPE);
    attributes.remove(DomainEventKafkaEnvelope.EVENT_ID);
    attributes.remove(DomainEventKafkaEnvelope.OCCURRED_ON);
    attributes.put(
        DomainEventKafkaEnvelope.ATTRIBUTE_ID,
        attributes.remove(DomainEventKafkaEnvelope.AGGREGATE_ID)
    );

    return attributes;
  }
}
