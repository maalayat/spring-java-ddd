package ec.solmedia.shared.infrastructure.bus.event;

import ec.solmedia.shared.domain.Service;
import ec.solmedia.shared.domain.event.bus.DomainEventSubscriber;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Set;
import org.reflections.Reflections;

@Service
public final class DomainEventSubscribersInformation {

  HashMap<Class<?>, DomainEventSubscriberInformation> information;

  public DomainEventSubscribersInformation(
      HashMap<Class<?>, DomainEventSubscriberInformation> information) {
    this.information = information;
  }

  public DomainEventSubscribersInformation() {
    this(scanDomainEventSubscribers());
  }

  private static HashMap<Class<?>, DomainEventSubscriberInformation> scanDomainEventSubscribers() {
    final var reflections = new Reflections("ec.solmedia");
    Set<Class<?>> subscribers = reflections.getTypesAnnotatedWith(DomainEventSubscriber.class);

    HashMap<Class<?>, DomainEventSubscriberInformation> subscribersInformation = new HashMap<>();

    for (Class<?> subscriberClass : subscribers) {
      DomainEventSubscriber annotation = subscriberClass.getAnnotation(DomainEventSubscriber.class);

      subscribersInformation.put(
          subscriberClass,
          new DomainEventSubscriberInformation(subscriberClass, Arrays.asList(annotation.value()))
      );
    }

    return subscribersInformation;
  }

  public Collection<DomainEventSubscriberInformation> all() {
    return information.values();
  }

  public String[] rabbitMqFormattedNames() {
    return information.values()
        .stream()
        .map(DomainEventSubscriberInformation::formatRabbitMqQueueName)
        .distinct()
        .toArray(String[]::new);
  }
}
