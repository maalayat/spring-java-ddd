package ec.solmedia.shared.infrastructure.bus.event.rabbitmq;

import ec.solmedia.shared.domain.Service;
import ec.solmedia.shared.domain.event.bus.DomainEvent;
import ec.solmedia.shared.infrastructure.bus.event.DomainEventJsonSerializer;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePropertiesBuilder;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

@Service
public class RabbitMqPublisher {

  private final RabbitTemplate rabbitTemplate;

  public RabbitMqPublisher(RabbitTemplate rabbitTemplate) {
    this.rabbitTemplate = rabbitTemplate;
  }

  public void publish(DomainEvent domainEvent, String exchangeName) throws AmqpException {
    final var serializedDomainEvent = DomainEventJsonSerializer.serialize(domainEvent);
    final var message = new Message(
        serializedDomainEvent.getBytes(),
        MessagePropertiesBuilder.newInstance()
            .setContentEncoding("utf-8")
            .setContentType("application/json")
            .build()
    );

    rabbitTemplate.send(exchangeName, domainEvent.eventName(), message);
  }

  public void publish(Message domainEvent, String exchangeName, String routingKey)
      throws AmqpException {
    rabbitTemplate.send(exchangeName, routingKey, domainEvent);
  }
}
