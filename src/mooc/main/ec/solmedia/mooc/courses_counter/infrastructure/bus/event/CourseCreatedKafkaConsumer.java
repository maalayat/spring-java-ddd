package ec.solmedia.mooc.courses_counter.infrastructure.bus.event;

import ec.solmedia.mooc.courses.domain.CourseCreatedDomainEvent;
import ec.solmedia.mooc.courses_counter.application.IncrementCoursesCounterOnCourseCreated;
import ec.solmedia.shared.domain.Service;
import org.springframework.kafka.annotation.KafkaListener;

@Service
public class CourseCreatedKafkaConsumer {

  public static final String TOPIC = "course.created";

  private final IncrementCoursesCounterOnCourseCreated subscriber;

  public CourseCreatedKafkaConsumer(IncrementCoursesCounterOnCourseCreated subscriber) {
    this.subscriber = subscriber;
  }

  @KafkaListener(topics = TOPIC, groupId = "solmedia.courses_counter")
  public void consume(CourseCreatedDomainEvent event) {
    subscriber.on(event);
  }
}
