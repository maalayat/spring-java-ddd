package ec.solmedia.mooc.courses;

import ec.solmedia.mooc.courses.application.create.CourseCreator;
import ec.solmedia.mooc.courses.domain.CourseRepository;
import ec.solmedia.shared.infrastructure.UnitTestCase;
import org.mockito.InjectMocks;
import org.mockito.Mock;

public abstract class CoursesModuleUnitTestCase extends UnitTestCase {

  @Mock
  protected CourseRepository repository;

  @InjectMocks
  protected CourseCreator creator;


}
