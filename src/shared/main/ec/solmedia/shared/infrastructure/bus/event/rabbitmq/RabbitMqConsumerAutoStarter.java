package ec.solmedia.shared.infrastructure.bus.event.rabbitmq;

import ec.solmedia.shared.domain.Service;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;

@Service
public class RabbitMqConsumerAutoStarter implements ApplicationListener {

  private final RabbitMqDomainEventsConsumer rabbitMqDomainEventsConsumer;

  public RabbitMqConsumerAutoStarter(RabbitMqDomainEventsConsumer rabbitMqDomainEventsConsumer) {
    this.rabbitMqDomainEventsConsumer = rabbitMqDomainEventsConsumer;
  }

  @Override
  public void onApplicationEvent(ApplicationEvent event) {
    if (event instanceof ApplicationReadyEvent) {
      rabbitMqDomainEventsConsumer.consume();
    }
  }
}
