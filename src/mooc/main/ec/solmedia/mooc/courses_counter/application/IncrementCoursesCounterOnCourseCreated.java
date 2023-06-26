package ec.solmedia.mooc.courses_counter.application;

import ec.solmedia.mooc.courses.domain.CourseCreatedDomainEvent;
import ec.solmedia.mooc.courses.domain.CourseId;
import ec.solmedia.shared.domain.Service;
import ec.solmedia.shared.domain.event.bus.DomainEventSubscriber;
import org.springframework.context.event.EventListener;

@Service
@DomainEventSubscriber({CourseCreatedDomainEvent.class})
public class IncrementCoursesCounterOnCourseCreated {

  private final CoursesCounterIncrementer incrementer;

  public IncrementCoursesCounterOnCourseCreated(CoursesCounterIncrementer incrementer) {
    this.incrementer = incrementer;
  }

  @EventListener
  public void on(CourseCreatedDomainEvent event) {
    final var courseId = new CourseId(event.aggregateId());

    incrementer.increment(courseId);
  }
}
