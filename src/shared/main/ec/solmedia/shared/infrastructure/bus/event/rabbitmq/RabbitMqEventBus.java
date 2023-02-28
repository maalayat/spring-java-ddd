package ec.solmedia.shared.infrastructure.bus.event.rabbitmq;

import ec.solmedia.shared.domain.Service;
import ec.solmedia.shared.domain.event.bus.DomainEvent;
import ec.solmedia.shared.domain.event.bus.EventBus;
import java.util.List;
import org.springframework.amqp.AmqpException;
import org.springframework.context.annotation.Primary;

@Service
@Primary
public class RabbitMqEventBus implements EventBus {

  private final RabbitMqPublisher publisher;
  private final String exchangeName;

  public RabbitMqEventBus(RabbitMqPublisher publisher) {
    this.publisher = publisher;
    this.exchangeName = "domain_events";
  }

  @Override
  public void publish(List<DomainEvent> events) {
    events.forEach(this::publish);
  }

  private void publish(DomainEvent domainEvent) {
    try {
      publisher.publish(domainEvent, exchangeName);
    } catch (AmqpException exception) {
      // TODO failover
    }

  }
}
