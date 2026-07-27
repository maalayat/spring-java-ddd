package ec.solmedia.mooc.courses_counter.application;

import ec.solmedia.mooc.courses.domain.CourseCreatedDomainEvent;
import ec.solmedia.mooc.courses.domain.CourseId;
import ec.solmedia.shared.domain.Service;

@Service
public class IncrementCoursesCounterOnCourseCreated {

  private final CoursesCounterIncrementer incrementer;

  public IncrementCoursesCounterOnCourseCreated(CoursesCounterIncrementer incrementer) {
    this.incrementer = incrementer;
  }

  public void on(CourseCreatedDomainEvent event) {
    final var courseId = new CourseId(event.aggregateId());

    incrementer.increment(courseId);
  }
}
