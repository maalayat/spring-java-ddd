package ec.solmedia.mooc.courses.application.create;

import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;

import ec.solmedia.mooc.courses.domain.CourseMother;
import ec.solmedia.mooc.courses.domain.CourseRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CourseCreatorTest {

  @Mock
  private CourseRepository repository;

  @InjectMocks
  private CourseCreator creator;

  @Test
  @DisplayName("Save a valid course")
  void saveValidCourse() {
    final var request = CourseCreateRequestMother.random();
    final var course = CourseMother.fromRequest(request);

    creator.create(request);

    verify(repository, atLeastOnce()).save(course);
  }
}