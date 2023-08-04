package ec.solmedia.mooc.courses_counter.application.find;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import ec.solmedia.mooc.courses_counter.CoursesCounterModuleUnitTestCase;
import ec.solmedia.mooc.courses_counter.domain.CoursesCounterMother;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

public class FindCoursesCounterQueryHandlerTest extends CoursesCounterModuleUnitTestCase {

  @InjectMocks
  private FindCoursesCounterQueryHandler handler;

  @BeforeEach
  void setUp() {
    handler = new FindCoursesCounterQueryHandler(new CoursesCounterFinder(repository));
  }

  @Test
  void shouldFindAnExistingCoursesCounter() {
    final var counter = CoursesCounterMother.random();
    final var query = new FindCoursesCounterQuery();

    shouldMockSearch(counter);

    assertEquals(3, handler.handle(query).getTotal());
  }

  @Test
  void shouldThrowAnExceptionWhenCoursesCounterDoesNotExists() {
    final var query = new FindCoursesCounterQuery();
    shouldMockSearch();

    assertThrows(NoSuchElementException.class, () -> handler.handle(query));
  }
}
