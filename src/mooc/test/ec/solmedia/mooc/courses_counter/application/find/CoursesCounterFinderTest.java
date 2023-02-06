package ec.solmedia.mooc.courses_counter.application.find;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import ec.solmedia.mooc.courses_counter.CoursesCounterModuleUnitTestCase;
import ec.solmedia.mooc.courses_counter.domain.CoursesCounterMother;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

public class CoursesCounterFinderTest extends CoursesCounterModuleUnitTestCase {

  @InjectMocks
  private CoursesCounterFinder finder;

  @Test
  void shouldFindAnExistingCoursesCounter() {
    final var counter = CoursesCounterMother.random();

    shouldMockSearch(counter);

    assertEquals(3, finder.find().get("total"));
  }

  @Test
  void shouldThrowAnExceptionWhenCoursesCounterDoesNotExists() {
    shouldMockSearch();

    assertThrows(NoSuchElementException.class, () -> finder.find());
  }
}
