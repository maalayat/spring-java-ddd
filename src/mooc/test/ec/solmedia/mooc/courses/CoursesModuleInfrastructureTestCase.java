package ec.solmedia.mooc.courses;

import ec.solmedia.mooc.courses.domain.CourseRepository;
import ec.solmedia.shared.infrastructure.InfrastructureTestCase;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class CoursesModuleInfrastructureTestCase extends InfrastructureTestCase {

  @Autowired
  protected CourseRepository repository;

}
