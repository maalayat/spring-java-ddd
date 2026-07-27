package ec.solmedia.mooc.infrastructure.bus.event.kafka;

import ec.solmedia.mooc.courses.domain.CourseCreatedDomainEvent;
import ec.solmedia.shared.domain.event.bus.DomainEvent;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.ObjectMapper;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({
    @JsonSubTypes.Type(value = CourseCreatedDomainEvent.class, name = "course.created")
})
public abstract class DomainEventMixIn {

  @JsonProperty("aggregateId")
  public abstract String aggregateId();

  @JsonProperty("eventId")
  public abstract String eventId();

  @JsonProperty("occurredOn")
  public abstract String occurredOn();

  private static final ObjectMapper OBJECT_MAPPER = createObjectMapper();

  public static ObjectMapper createObjectMapper() {
    ObjectMapper mapper = new ObjectMapper();
    mapper.addMixIn(DomainEvent.class, DomainEventMixIn.class);
    mapper.addMixIn(CourseCreatedDomainEvent.class, CourseCreatedDomainEventMixIn.class);
    return mapper;
  }

  public static ObjectMapper objectMapper() {
    return OBJECT_MAPPER;
  }
}
