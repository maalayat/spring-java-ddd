package ec.solmedia.mooc.courses_counter.infrastructure;

import ec.solmedia.mooc.courses_counter.domain.CoursesCounterRepository;
import ec.solmedia.shared.infrastructure.InfrastructureTestCase;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class CoursesCounterModuleInfrastructureTestCase extends InfrastructureTestCase {

  @Autowired
  protected CoursesCounterRepository repository;

}
