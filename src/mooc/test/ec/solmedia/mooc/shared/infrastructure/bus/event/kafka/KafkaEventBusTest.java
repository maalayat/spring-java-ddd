package ec.solmedia.mooc.shared.infrastructure.bus.event.kafka;

import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

import ec.solmedia.mooc.courses.domain.CourseCreatedDomainEvent;
import ec.solmedia.mooc.courses.domain.CourseCreatedDomainEventMother;
import ec.solmedia.shared.domain.event.bus.DomainEvent;
import ec.solmedia.shared.infrastructure.bus.event.kafka.KafkaEventBus;
import ec.solmedia.shared.infrastructure.bus.event.mysql.MySqlEventBus;
import java.util.Collections;
import org.apache.kafka.common.KafkaException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;

@ExtendWith(MockitoExtension.class)
public class KafkaEventBusTest {

  @Mock
  private KafkaTemplate<String, DomainEvent> kafkaTemplate;

  @Mock
  private MySqlEventBus mySqlEventBus;

  @Test
  void shouldPublishCourseCreatedDomainEventToKafkaTopic() {
    final var event = CourseCreatedDomainEventMother.random();
    final var eventBus = new KafkaEventBus(kafkaTemplate, mySqlEventBus);

    eventBus.publish(Collections.singletonList(event));

    verify(kafkaTemplate, atLeastOnce()).send("course.created", event.eventId(), event);
  }

  @Test
  void shouldFallBackToMySqlEventBusWhenKafkaFails() {
    final var event = CourseCreatedDomainEventMother.random();
    doThrow(new KafkaException("Kafka failure"))
        .when(kafkaTemplate)
        .send("course.created", event.eventId(), event);
    final var eventBus = new KafkaEventBus(kafkaTemplate, mySqlEventBus);

    eventBus.publish(Collections.singletonList(event));

    verify(mySqlEventBus, atLeastOnce()).publish(Collections.singletonList(event));
  }
}
