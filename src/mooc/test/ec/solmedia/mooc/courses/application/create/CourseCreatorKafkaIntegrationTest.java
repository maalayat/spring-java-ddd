package ec.solmedia.mooc.courses.application.create;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import ec.solmedia.mooc.courses.domain.CourseCreatedDomainEvent;
import ec.solmedia.mooc.courses.domain.CourseDurationMother;
import ec.solmedia.mooc.courses.domain.CourseIdMother;
import ec.solmedia.mooc.courses.domain.CourseNameMother;
import ec.solmedia.mooc.courses_counter.domain.CoursesCounterRepository;
import ec.solmedia.shared.infrastructure.InfrastructureTestCase;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Supplier;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.test.annotation.DirtiesContext;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class CourseCreatorKafkaIntegrationTest extends InfrastructureTestCase {

  private static final String COURSE_CREATED_TOPIC = "course.created";
  private static final int MAX_WAIT_ITERATIONS = 50;
  private static final long WAIT_MILLIS = 200;

  @Autowired
  private CourseCreator courseCreator;

  @Autowired
  private CoursesCounterRepository coursesCounterRepository;

  private final AtomicReference<CourseCreatedDomainEvent> receivedEvent = new AtomicReference<>();

  @KafkaListener(topics = COURSE_CREATED_TOPIC, groupId = "test.course.created")
  public void consume(CourseCreatedDomainEvent event) {
    receivedEvent.set(event);
  }

  @Test
  @DisplayName("Given a course creator when create a course then it publishes a course.created event to kafka")
  void shouldPublishCourseCreatedEventToKafka() throws InterruptedException {
    final var courseId = CourseIdMother.random();
    final var courseName = CourseNameMother.random();
    final var courseDuration = CourseDurationMother.random();

    courseCreator.create(courseId, courseName, courseDuration);

    waitUntil(() -> receivedEvent.get() != null);

    final var event = receivedEvent.get();
    assertTrue(event != null, "Expected a message to be published to the course.created topic");
    assertEquals(courseId.value(), event.aggregateId());
    assertEquals(courseName.value(), event.name());
    assertEquals(courseDuration.value(), event.duration());

    waitUntil(() -> coursesCounterRepository.search().isPresent());

    final var counter = coursesCounterRepository.search();
    assertTrue(counter.isPresent(), "Expected the courses counter to be incremented");
    assertEquals(1, counter.get().total().value());
  }

  private void waitUntil(Supplier<Boolean> condition) throws InterruptedException {
    for (int i = 0; i < MAX_WAIT_ITERATIONS && !condition.get(); i++) {
      Thread.sleep(WAIT_MILLIS);
    }
  }
}
