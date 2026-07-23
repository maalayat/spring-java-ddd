package ec.solmedia.shared.infrastructure.bus.event.kafka;

import ec.solmedia.shared.domain.event.bus.DomainEvent;
import java.io.IOException;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;

public class DomainEventKafkaDeserializer implements Deserializer<DomainEvent> {

  @Override
  public DomainEvent deserialize(String topic, byte[] data) {
    if (data == null) {
      return null;
    }

    try {
      return (DomainEvent) DomainEventMixIn.objectMapper().readValue(data, DomainEvent.class);
    } catch (IOException exception) {
      throw new SerializationException("Error deserializing domain event", exception);
    }
  }
}
