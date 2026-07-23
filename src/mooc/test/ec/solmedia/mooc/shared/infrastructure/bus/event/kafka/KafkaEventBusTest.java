package ec.solmedia.mooc.shared.infrastructure.bus.event.kafka;

import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

import ec.solmedia.mooc.courses.domain.CourseCreatedDomainEvent;
import ec.solmedia.mooc.courses.domain.CourseCreatedDomainEventMother;
import ec.solmedia.shared.domain.event.bus.DomainEvent;
import ec.solmedia.shared.domain.event.bus.EventBus;
import ec.solmedia.shared.infrastructure.UnitTestCase;
import ec.solmedia.shared.infrastructure.bus.event.kafka.KafkaEventBus;
import ec.solmedia.shared.infrastructure.bus.event.mysql.MySqlEventBus;
import java.util.Collections;
import org.apache.kafka.common.KafkaException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.kafka.core.KafkaTemplate;

public class KafkaEventBusTest extends UnitTestCase {

  @Mock
  private KafkaTemplate<String, DomainEvent> kafkaTemplate;

  @Mock
  private MySqlEventBus mySqlEventBus;

  @Test
  @DisplayName("Given a course created domain event when publish through kafka event bus then it sends it to the course.created topic")
  void shouldPublishCourseCreatedDomainEventToKafkaTopic() {
    final var event = CourseCreatedDomainEventMother.random();
    final EventBus eventBus = new KafkaEventBus(kafkaTemplate, mySqlEventBus);

    eventBus.publish(Collections.singletonList(event));

    verify(kafkaTemplate, atLeastOnce()).send("course.created", event.eventId(), event);
  }

  @Test
  @DisplayName("Given a kafka failure when publish a course created domain event then it falls back to the mysql event bus")
  void shouldFallBackToMySqlEventBusWhenKafkaFails() {
    final var event = CourseCreatedDomainEventMother.random();
    doThrow(new KafkaException("Kafka failure"))
        .when(kafkaTemplate)
        .send("course.created", event.eventId(), event);
    final EventBus eventBus = new KafkaEventBus(kafkaTemplate, mySqlEventBus);

    eventBus.publish(Collections.singletonList(event));

    verify(mySqlEventBus, atLeastOnce()).publish(Collections.singletonList(event));
  }
}
