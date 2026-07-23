package ec.solmedia.shared.infrastructure.bus.event.kafka;

import ec.solmedia.shared.domain.Service;
import ec.solmedia.shared.domain.event.bus.DomainEvent;
import ec.solmedia.shared.domain.event.bus.EventBus;
import ec.solmedia.shared.infrastructure.bus.event.mysql.MySqlEventBus;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;
import org.apache.kafka.common.KafkaException;
import org.springframework.kafka.core.KafkaTemplate;

@Service
public class KafkaEventBus implements EventBus {

  private final KafkaTemplate<String, DomainEvent> kafkaTemplate;
  private final MySqlEventBus failoverPublisher;

  public KafkaEventBus(KafkaTemplate<String, DomainEvent> kafkaTemplate, MySqlEventBus mySqlEventBus) {
    this.kafkaTemplate = kafkaTemplate;
    this.failoverPublisher = mySqlEventBus;
  }

  @Override
  public void publish(List<DomainEvent> events) {
    events.forEach(this::publish);
  }

  private void publish(DomainEvent domainEvent) {
    try {
      kafkaTemplate.send(domainEvent.eventName(), domainEvent.eventId(), domainEvent).get();
    } catch (KafkaException exception) {
      failoverPublisher.publish(Collections.singletonList(domainEvent));
    } catch (ExecutionException exception) {
      if (exception.getCause() instanceof KafkaException) {
        failoverPublisher.publish(Collections.singletonList(domainEvent));
      } else {
        throw new RuntimeException(exception);
      }
    } catch (InterruptedException exception) {
      Thread.currentThread().interrupt();
      failoverPublisher.publish(Collections.singletonList(domainEvent));
    }
  }
}
