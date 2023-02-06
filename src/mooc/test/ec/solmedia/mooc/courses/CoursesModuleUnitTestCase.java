package ec.solmedia.mooc.courses;

import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;

import ec.solmedia.mooc.courses.application.create.CourseCreator;
import ec.solmedia.mooc.courses.domain.Course;
import ec.solmedia.mooc.courses.domain.CourseRepository;
import ec.solmedia.shared.infrastructure.UnitTestCase;
import org.mockito.InjectMocks;
import org.mockito.Mock;

public abstract class CoursesModuleUnitTestCase extends UnitTestCase {

  @Mock
  protected CourseRepository repository;

  @InjectMocks
  protected CourseCreator creator;

  protected void shouldHaveSaved(Course course) {
    verify(repository, atLeastOnce()).save(course);
  }

}
