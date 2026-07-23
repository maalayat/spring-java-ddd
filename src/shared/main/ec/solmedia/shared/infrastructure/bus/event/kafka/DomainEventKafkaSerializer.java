package ec.solmedia.shared.infrastructure.bus.event.kafka;

import ec.solmedia.shared.domain.event.bus.DomainEvent;
import java.nio.charset.StandardCharsets;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Serializer;

public class DomainEventKafkaSerializer implements Serializer<DomainEvent> {

  @Override
  public byte[] serialize(String topic, DomainEvent data) {
    try {
      return DomainEventMixIn.objectMapper().writeValueAsString(data).getBytes(StandardCharsets.UTF_8);
    } catch (JsonProcessingException exception) {
      throw new SerializationException("Error serializing domain event", exception);
    }
  }
}
