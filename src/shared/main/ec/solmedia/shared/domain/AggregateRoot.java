package ec.solmedia.shared.domain;

import ec.solmedia.shared.domain.event.bus.DomainEvent;
import java.util.ArrayList;
import java.util.List;

public abstract class AggregateRoot {

  private List<DomainEvent> domainEvents = new ArrayList<>();

  public List<DomainEvent> pullDomainEvents() {
    List<DomainEvent> events = domainEvents;
    domainEvents = new ArrayList<>();

    return events;
  }

  protected void record(DomainEvent domainEvent) {
    domainEvents.add(domainEvent);
  }

}
