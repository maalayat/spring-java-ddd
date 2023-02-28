package ec.solmedia.shared.infrastructure.bus.event;

import ec.solmedia.shared.domain.JsonUtil;
import ec.solmedia.shared.domain.event.bus.DomainEvent;
import java.io.Serializable;
import java.util.HashMap;

public class DomainEventJsonSerializer {

  public static String serialize(DomainEvent domainEvent) {
    HashMap<String, Serializable> attributes = domainEvent.toPrimitives();
    attributes.put("id", domainEvent.aggregateId());

    return JsonUtil.encode(new HashMap<>() {{
      put("data", new HashMap<String, Serializable>() {{
        put("id", domainEvent.eventId());
        put("type", domainEvent.eventName());
        put("occurred_on", domainEvent.occurredOn());
        put("attributes", attributes);
      }});
      put("meta", new HashMap<String, Serializable>());
    }});
  }

}
