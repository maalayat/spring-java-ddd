package ec.solmedia.mooc.courses.application.create;

import ec.solmedia.mooc.courses.domain.Course;
import ec.solmedia.mooc.courses.domain.CourseDuration;
import ec.solmedia.mooc.courses.domain.CourseId;
import ec.solmedia.mooc.courses.domain.CourseName;
import ec.solmedia.mooc.courses.domain.CourseRepository;
import org.springframework.stereotype.Service;

@Service
public final class CourseCreator {

  private final CourseRepository repository;

  public CourseCreator(CourseRepository repository) {
    this.repository = repository;
  }

  public void create(CourseCreateRequest request) {
    final var course = new Course(
        new CourseId(request.id()),
        new CourseName(request.name()),
        new CourseDuration(request.duration()));

    repository.save(course);
  }
}
