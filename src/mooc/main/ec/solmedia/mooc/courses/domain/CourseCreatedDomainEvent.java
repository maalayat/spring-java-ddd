package ec.solmedia.mooc.courses.domain;

import ec.solmedia.shared.domain.event.bus.DomainEvent;

public final class CourseCreatedDomainEvent extends DomainEvent<CourseCreatedDomainEvent> {

  public CourseCreatedDomainEvent(String aggregateId) {
    super(aggregateId);
  }

  @Override
  protected String eventName() {
    return "course.created";
  }
}
