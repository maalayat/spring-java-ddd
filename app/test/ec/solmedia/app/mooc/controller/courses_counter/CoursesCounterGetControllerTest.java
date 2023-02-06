package ec.solmedia.app.mooc.controller.courses_counter;

import ec.solmedia.app.mooc.controller.ApplicationTestCase;
import ec.solmedia.mooc.courses.domain.CourseCreatedDomainEvent;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.annotation.DirtiesContext;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class CoursesCounterGetControllerTest extends ApplicationTestCase {

  @Test
  @DisplayName("Given a request when we find course counter then get total counter")
  void shouldGetCounterWithOutDuplicatedEvents() throws Exception {
    givenISendAnEventToTheBus(
        new CourseCreatedDomainEvent(
            "2cc07b5c-2a39-4c8a-a840-3a2b18dbe077",
            "Domain-Driven Design Distilled",
            "21 days"),
        new CourseCreatedDomainEvent(
            "3ae00e33-720f-461c-8f36-74dfcbf9ea1b",
            "Clean code",
            "4 days")
    );

    assertResponse("/courses-counter", 200, "{'total': 2}");
  }

  @Test
  @DisplayName("Given a course created event then get total counter")
  void shouldGetCounterHavingDuplicatedEvents() throws Exception {
    givenISendAnEventToTheBus(
        new CourseCreatedDomainEvent(
            "2cc07b5c-2a39-4c8a-a840-3a2b18dbe077",
            "Domain-Driven Design Distilled",
            "21 days"),
        new CourseCreatedDomainEvent(
            "3ae00e33-720f-461c-8f36-74dfcbf9ea1b",
            "Clean code",
            "4 days"),
        new CourseCreatedDomainEvent(
            "583e97aa-2ccc-4929-9125-6b8dc2bd6c3b",
            "Domain-Driven Design in PHP",
            "7 days"),
        new CourseCreatedDomainEvent(
            "2cc07b5c-2a39-4c8a-a840-3a2b18dbe077",
            "Domain-Driven Design Distilled",
            "21 days"),
        new CourseCreatedDomainEvent(
            "3ae00e33-720f-461c-8f36-74dfcbf9ea1b",
            "Clean code",
            "4 days"),
        new CourseCreatedDomainEvent(
            "583e97aa-2ccc-4929-9125-6b8dc2bd6c3b",
            "Domain-Driven Design in PHP",
            "7 days")
    );

    assertResponse("/courses-counter", 200, "{'total': 3}");
  }
}
