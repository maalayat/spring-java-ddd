package ec.solmedia.mooc.courses.application.create;

import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import ec.solmedia.mooc.courses.domain.Course;
import ec.solmedia.mooc.courses.domain.CourseCreateRequest;
import ec.solmedia.mooc.courses.domain.CourseRepository;
import org.junit.jupiter.api.Test;

class CourseCreatorTest {

  @Test
  void saveValidCourse() {
    final var repository = mock(CourseRepository.class);
    final var creator = new CourseCreator(repository);
    final var course = new Course("some-id", "some-name", "some-duration");

    creator.create(new CourseCreateRequest(course.getId(), course.getName(), course.getDuration()));

    verify(repository, atLeastOnce()).save(course);

  }
}