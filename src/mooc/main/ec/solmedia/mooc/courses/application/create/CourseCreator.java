package ec.solmedia.mooc.courses.application.create;

import ec.solmedia.mooc.courses.domain.Course;
import ec.solmedia.mooc.courses.domain.CourseDuration;
import ec.solmedia.mooc.courses.domain.CourseId;
import ec.solmedia.mooc.courses.domain.CourseName;
import ec.solmedia.mooc.courses.domain.CourseRepository;
import ec.solmedia.shared.domain.Service;
import ec.solmedia.shared.domain.event.bus.EventBus;

@Service
public final class CourseCreator {

  private final CourseRepository repository;
  private final EventBus eventBus;

  public CourseCreator(CourseRepository repository, EventBus eventBus) {
    this.repository = repository;
    this.eventBus = eventBus;
  }

  public void create(CourseId courseId, CourseName courseName, CourseDuration courseDuration) {
    final var course = new Course(courseId, courseName, courseDuration);

    repository.save(course);
    eventBus.publish(course.pullDomainEvents());
  }
}
