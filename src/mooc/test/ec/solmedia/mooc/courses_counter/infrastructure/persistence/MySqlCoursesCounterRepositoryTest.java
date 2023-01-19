package ec.solmedia.mooc.courses_counter.infrastructure.persistence;

import static org.junit.jupiter.api.Assertions.assertEquals;

import ec.solmedia.mooc.courses_counter.domain.CoursesCounterMother;
import ec.solmedia.mooc.courses_counter.infrastructure.CoursesCounterModuleInfrastructureTestCase;
import java.util.Optional;
import org.junit.jupiter.api.Test;

public class MySqlCoursesCounterRepositoryTest extends CoursesCounterModuleInfrastructureTestCase {

  @Test
  void saveCoursesCounter() {
    final var coursesCounter = CoursesCounterMother.random();

    repository.save(coursesCounter);

    assertEquals(Optional.of(coursesCounter), repository.search());
  }

}
