package ec.solmedia.shared.infrastructure.bus.event.rabbitmq;

import ec.solmedia.shared.infrastructure.bus.event.DomainEventSubscribersInformation;
import ec.solmedia.shared.infrastructure.bus.event.DomainEventsInformation;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Declarable;
import org.springframework.amqp.core.Declarables;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqEventBusConfiguration {

  private final DomainEventSubscribersInformation domainEventSubscribersInformation;
  private final DomainEventsInformation domainEventsInformation;
  private final String exchangeName;

  public RabbitMqEventBusConfiguration(
      DomainEventSubscribersInformation domainEventSubscribersInformation,
      DomainEventsInformation domainEventsInformation) {
    this.domainEventSubscribersInformation = domainEventSubscribersInformation;
    this.domainEventsInformation = domainEventsInformation;
    this.exchangeName = "domain_events";
  }

  @Bean
  public Declarables declaration() {
    final var retryExchangeName = RabbitMqExchangeNameFormatter.retry(exchangeName);
    final var deadLetterExchangeName = RabbitMqExchangeNameFormatter.deadLetter(exchangeName);

    final var domainEventsExchange = new TopicExchange(exchangeName, true, false);
    final var retryDomainEventsExchange = new TopicExchange(retryExchangeName, true, false);
    final var deadLetterDomainEventsExchange = new TopicExchange(deadLetterExchangeName, true,
        false);
    final var declarables = new ArrayList<Declarable>();
    declarables.add(domainEventsExchange);
    declarables.add(retryDomainEventsExchange);
    declarables.add(deadLetterDomainEventsExchange);

    final var queuesAndBindings = declareQueuesAndBindings(
        domainEventsExchange,
        retryDomainEventsExchange,
        deadLetterDomainEventsExchange
    ).stream().flatMap(Collection::stream).toList();

    declarables.addAll(queuesAndBindings);

    return new Declarables(declarables);
  }

  private Collection<Collection<Declarable>> declareQueuesAndBindings(
      TopicExchange domainEventsExchange,
      TopicExchange retryDomainEventsExchange,
      TopicExchange deadLetterDomainEventsExchange
  ) {
    return domainEventSubscribersInformation.all().stream().map(information -> {
      final var queueName = RabbitMqQueueNameFormatter.format(information);
      final var retryQueueName = RabbitMqQueueNameFormatter.formatRetry(information);
      final var deadLetterQueueName = RabbitMqQueueNameFormatter.formatDeadLetter(information);

      final var queue = QueueBuilder.durable(queueName).build();
      final var retryQueue = QueueBuilder.durable(retryQueueName).withArguments(
          retryQueueArguments(domainEventsExchange, queueName)
      ).build();
      final var deadLetterQueue = QueueBuilder.durable(deadLetterQueueName).build();

      final var fromExchangeSameQueueBinding = BindingBuilder
          .bind(queue)
          .to(domainEventsExchange)
          .with(queueName);

      final var fromRetryExchangeSameQueueBinding = BindingBuilder
          .bind(retryQueue)
          .to(retryDomainEventsExchange)
          .with(queueName);

      final var fromDeadLetterExchangeSameQueueBinding = BindingBuilder
          .bind(deadLetterQueue)
          .to(deadLetterDomainEventsExchange)
          .with(queueName);

      final var fromExchangeDomainEventsBindings = information.subscribedEvents().stream().map(
          domainEventClass -> {
            String eventName = domainEventsInformation.forClass(domainEventClass);
            return BindingBuilder
                .bind(queue)
                .to(domainEventsExchange)
                .with(eventName);
          }).toList();

      List<Declarable> queuesAndBindings = new ArrayList<>();
      queuesAndBindings.add(queue);
      queuesAndBindings.add(fromExchangeSameQueueBinding);
      queuesAndBindings.addAll(fromExchangeDomainEventsBindings);

      queuesAndBindings.add(retryQueue);
      queuesAndBindings.add(fromRetryExchangeSameQueueBinding);

      queuesAndBindings.add(deadLetterQueue);
      queuesAndBindings.add(fromDeadLetterExchangeSameQueueBinding);

      return queuesAndBindings;
    }).collect(Collectors.toList());
  }

  private HashMap<String, Object> retryQueueArguments(TopicExchange exchange, String routingKey) {
    return new HashMap<>() {{
      put("x-dead-letter-exchange", exchange.getName());
      put("x-dead-letter-routing-key", routingKey);
      put("x-message-ttl", 1000);
    }};
  }
}
