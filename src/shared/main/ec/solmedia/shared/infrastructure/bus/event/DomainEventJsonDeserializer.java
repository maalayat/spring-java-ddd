package ec.solmedia.shared.infrastructure.bus.event;

import ec.solmedia.shared.domain.JsonUtil;
import ec.solmedia.shared.domain.Service;
import ec.solmedia.shared.domain.event.bus.DomainEvent;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

@Service
public final class DomainEventJsonDeserializer {

  private final DomainEventsInformation information;

  public DomainEventJsonDeserializer(DomainEventsInformation information) {
    this.information = information;
  }

  public DomainEvent deserialize(String body)
      throws InvocationTargetException, IllegalAccessException, NoSuchMethodException, InstantiationException {
    final var eventData = JsonUtil.decode(body);
    final var data = (HashMap<String, Serializable>) eventData.get("data");
    final var attributes = (HashMap<String, Serializable>) data.get("attributes");
    final var domainEventClass = information.forName((String) data.get("type"));

    final var nullInstance = domainEventClass.getConstructor().newInstance();

    final var fromPrimitivesMethod = domainEventClass.getMethod(
        "fromPrimitives",
        String.class,
        HashMap.class,
        String.class,
        String.class
    );

    final var domainEvent = fromPrimitivesMethod.invoke(
        nullInstance,
        (String) attributes.get("id"),
        attributes,
        (String) data.get("id"),
        (String) data.get("occurred_on")
    );

    return (DomainEvent) domainEvent;
  }
}
