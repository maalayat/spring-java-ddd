package ec.solmedia.mooc.courses_counter.infrastructure.bus.event;

import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;

import ec.solmedia.mooc.courses.domain.CourseCreatedDomainEvent;
import ec.solmedia.mooc.courses.domain.CourseCreatedDomainEventMother;
import ec.solmedia.mooc.courses_counter.application.IncrementCoursesCounterOnCourseCreated;
import ec.solmedia.shared.infrastructure.UnitTestCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

public class CourseCreatedKafkaConsumerTest extends UnitTestCase {

  @Mock
  private IncrementCoursesCounterOnCourseCreated subscriber;

  private CourseCreatedKafkaConsumer consumer;

  @BeforeEach
  void setUp() {
    consumer = new CourseCreatedKafkaConsumer(subscriber);
  }

  @Test
  @DisplayName("Given a course created domain event when consume through kafka consumer then delegates to subscriber")
  void shouldDelegateToSubscriber() {
    final var event = CourseCreatedDomainEventMother.random();

    consumer.consume(event);

    verify(subscriber, atLeastOnce()).on(event);
  }
}
