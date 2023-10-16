package ec.solmedia.mooc.courses.domain;

import ec.solmedia.shared.domain.JsonUtil;
import ec.solmedia.shared.domain.event.bus.DomainEvent;
import java.io.Serial;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Objects;

public final class CourseCreatedDomainEvent extends DomainEvent implements Serializable {

  private final String name;
  private final String duration;

  public CourseCreatedDomainEvent() {
    super(null);

    this.name = null;
    this.duration = null;
  }

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

  @Override
  public HashMap<String, Serializable> toPrimitives() {
    return new HashMap<>() {
      @Serial
      private static final long serialVersionUID = 4502342479399376802L;

      {
        put("name", name);
        put("duration", duration);
      }
    };
  }

  @Override
  public String toJson() {
    final var map = new HashMap<String, Serializable>() {
      {
        put("name", name);
        put("duration", duration);
      }
    };

    return JsonUtil.encode(map);
  }

  @Override
  public DomainEvent fromPrimitives(String aggregateId, HashMap<String, Serializable> body,
      String eventId, String occurredOn) {
    return new CourseCreatedDomainEvent(
        aggregateId,
        eventId,
        occurredOn,
        (String) body.get("name"),
        (String) body.get("duration")
    );
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
