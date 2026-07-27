package ec.solmedia.shared.domain.event.bus;

import java.time.LocalDateTime;
import java.util.UUID;

public abstract class DomainEvent {

  private final String aggregateId;
  private final String eventId;
  private final String occurredOn;

  public DomainEvent(String aggregateId) {
    this.aggregateId = aggregateId;
    this.eventId = UUID.randomUUID().toString();
    this.occurredOn = LocalDateTime.now().toString();
  }

  public DomainEvent(String aggregateId, String eventId, String occurredOn) {
    this.aggregateId = aggregateId;
    this.eventId = eventId;
    this.occurredOn = occurredOn;
  }

  public abstract String eventName();

  public String aggregateId() {
    return aggregateId;
  }

  public String eventId() {
    return eventId;
  }

  public String occurredOn() {
    return occurredOn;
  }
}
