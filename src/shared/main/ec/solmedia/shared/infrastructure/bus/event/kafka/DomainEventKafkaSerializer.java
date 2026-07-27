package ec.solmedia.shared.infrastructure.bus.event.kafka;

import ec.solmedia.shared.domain.event.bus.DomainEvent;
import java.nio.charset.StandardCharsets;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Serializer;

public class DomainEventKafkaSerializer implements Serializer<DomainEvent> {

  private final DomainEventEnvelopeSerializer serializer;

  public DomainEventKafkaSerializer(DomainEventEnvelopeSerializer serializer) {
    this.serializer = serializer;
  }

  @Override
  public byte[] serialize(String topic, DomainEvent data) {
    try {
      return serializer.serialize(data).getBytes(StandardCharsets.UTF_8);
    } catch (RuntimeException exception) {
      throw new SerializationException("Error serializing domain event", exception);
    }
  }
}
