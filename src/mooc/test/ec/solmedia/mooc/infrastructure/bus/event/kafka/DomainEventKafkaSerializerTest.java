package ec.solmedia.mooc.infrastructure.bus.event.kafka;

import static org.junit.jupiter.api.Assertions.assertEquals;

import ec.solmedia.mooc.courses.CoursesModuleUnitTestCase;
import ec.solmedia.mooc.courses.domain.CourseCreatedDomainEvent;
import ec.solmedia.mooc.courses.domain.CourseCreatedDomainEventMother;
import ec.solmedia.shared.infrastructure.bus.event.kafka.DomainEventEnvelopeSerializer;
import ec.solmedia.shared.infrastructure.bus.event.kafka.DomainEventKafkaSerializer;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class DomainEventKafkaSerializerTest extends CoursesModuleUnitTestCase {

  @Test
  @DisplayName("Given a course created domain event when serialize to kafka then it produces the expected envelope")
  void shouldSerializeCourseCreatedDomainEventToEnvelope() throws Exception {
    final var event = CourseCreatedDomainEventMother.random();
    final var envelopeSerializer = new DomainEventEnvelopeSerializer(DomainEventMixIn.objectMapper());
    final var serializer = new DomainEventKafkaSerializer(envelopeSerializer);

    final var bytes = serializer.serialize("course.created", event);
    final var json = new String(bytes);
    final var envelope = DomainEventMixIn.objectMapper().readValue(json, Map.class);
    final var data = (Map<String, Object>) envelope.get("data");
    final var attributes = (Map<String, Object>) data.get("attributes");

    assertEquals("course.created", data.get("type"));
    assertEquals(event.eventId(), data.get("id"));
    assertEquals(event.aggregateId(), attributes.get("id"));
    assertEquals(event.name(), attributes.get("name"));
    assertEquals(event.duration(), attributes.get("duration"));
  }
}
