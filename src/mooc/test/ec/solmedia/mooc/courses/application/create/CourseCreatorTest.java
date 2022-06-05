package ec.solmedia.mooc.courses.application.create;

import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import ec.solmedia.mooc.courses.domain.Course;
import ec.solmedia.mooc.courses.domain.CourseDuration;
import ec.solmedia.mooc.courses.domain.CourseId;
import ec.solmedia.mooc.courses.domain.CourseName;
import ec.solmedia.mooc.courses.domain.CourseRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CourseCreatorTest {

  @Test
  @DisplayName("Save a valid course")
  void saveValidCourse() {
    final var repository = mock(CourseRepository.class);
    final var creator = new CourseCreator(repository);

    final var request = new CourseCreateRequest(
        "eb4392bd-8df8-435e-839f-03ebf92c3992",
        "some-name",
        "some-duration");

    final var course = new Course(
        new CourseId(request.id()),
        new CourseName(request.name()),
        new CourseDuration(request.duration()));

    creator.create(request);

    verify(repository, atLeastOnce()).save(course);
  }
}