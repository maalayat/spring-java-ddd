package ec.solmedia.mooc.courses.domain;

import ec.solmedia.shared.domain.event.bus.DomainEvent;
import java.util.Objects;

public final class CourseCreatedDomainEvent extends DomainEvent<CourseCreatedDomainEvent> {

  private final String name;
  private final String duration;

  public CourseCreatedDomainEvent(String aggregateId, String name, String duration) {
    super(aggregateId);
    this.name = name;
    this.duration = duration;
  }

  @Override
  protected String eventName() {
    return "course.created";
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CourseCreatedDomainEvent that = (CourseCreatedDomainEvent) o;
    return Objects.equals(name, that.name) && Objects.equals(duration,
        that.duration);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, duration);
  }
}
