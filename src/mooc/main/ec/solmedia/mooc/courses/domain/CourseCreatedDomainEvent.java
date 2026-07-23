package ec.solmedia.mooc.courses.domain;

import ec.solmedia.shared.domain.event.bus.DomainEvent;

public final class CourseCreatedDomainEvent extends DomainEvent {

  private final String name;
  private final String duration;

  public CourseCreatedDomainEvent(String aggregateId, String name, String duration) {
    super(aggregateId);
    this.name = name;
    this.duration = duration;
  }

  public CourseCreatedDomainEvent(
      String aggregateId,
      String eventId,
      String occurredOn,
      String name,
      String duration
  ) {
    super(aggregateId, eventId, occurredOn);
    this.name = name;
    this.duration = duration;
  }

  @Override
  public String eventName() {
    return "course.created";
  }

  public String name() {
    return name;
  }

  public String duration() {
    return duration;
  }
}
