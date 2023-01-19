package ec.solmedia.app.mooc.controller.courses_counter;

import ec.solmedia.app.mooc.controller.ApplicationTestCase;
import ec.solmedia.mooc.courses.domain.CourseCreatedDomainEvent;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CoursesCounterGetControllerTest extends ApplicationTestCase {

  @Test
  @DisplayName("Given a course created event then get total counter")
  void getCounterTotalCourses2() throws Exception {
    givenISendAnEventToTheBus(
        new CourseCreatedDomainEvent("2cc07b5c-2a39-4c8a-a840-3a2b18dbe077"),
        new CourseCreatedDomainEvent("3ae00e33-720f-461c-8f36-74dfcbf9ea1b")
    );

    assertResponse("/courses-counter", 200, "{'total': 2}");
  }

  @Test
  @DisplayName("Given a course created event then get total counter")
  void getCounterTotalCourses3() throws Exception {
    givenISendAnEventToTheBus(
        new CourseCreatedDomainEvent("2cc07b5c-2a39-4c8a-a840-3a2b18dbe077"),
        new CourseCreatedDomainEvent("3ae00e33-720f-461c-8f36-74dfcbf9ea1b"),
        new CourseCreatedDomainEvent("583e97aa-2ccc-4929-9125-6b8dc2bd6c3b"),
        new CourseCreatedDomainEvent("2cc07b5c-2a39-4c8a-a840-3a2b18dbe077"),
        new CourseCreatedDomainEvent("3ae00e33-720f-461c-8f36-74dfcbf9ea1b"),
        new CourseCreatedDomainEvent("583e97aa-2ccc-4929-9125-6b8dc2bd6c3b")
    );

    assertResponse("/courses-counter", 200, "{'total': 3}");
  }
}
