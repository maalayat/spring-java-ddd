package ec.solmedia.mooc.courses.application.create;

import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import ec.solmedia.mooc.courses.domain.CourseMother;
import ec.solmedia.mooc.courses.domain.CourseRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CourseCreatorTest {

  @Test
  @DisplayName("Save a valid course")
  void saveValidCourse() {
    final var repository = mock(CourseRepository.class);
    final var creator = new CourseCreator(repository);

    final var request = CourseCreateRequestMother.random();
    final var course = CourseMother.fromRequest(request);

    creator.create(request);

    verify(repository, atLeastOnce()).save(course);
  }
}