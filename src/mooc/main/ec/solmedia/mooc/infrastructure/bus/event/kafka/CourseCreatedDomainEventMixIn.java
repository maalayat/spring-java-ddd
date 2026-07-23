package ec.solmedia.mooc.infrastructure.bus.event.kafka;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public abstract class CourseCreatedDomainEventMixIn {

  @JsonCreator
  public CourseCreatedDomainEventMixIn(
      @JsonProperty("aggregateId") String aggregateId,
      @JsonProperty("eventId") String eventId,
      @JsonProperty("occurredOn") String occurredOn,
      @JsonProperty("name") String name,
      @JsonProperty("duration") String duration
  ) {
  }

  @JsonProperty("name")
  public abstract String name();

  @JsonProperty("duration")
  public abstract String duration();
}
