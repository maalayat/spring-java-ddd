package ec.solmedia.app.mooc.controller.courses;

import ec.solmedia.app.mooc.controller.RequestTestCase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public final class CoursesPutControllerTest extends RequestTestCase {

  @Test
  @DisplayName("Create a valid non exists course")
  void createCourse() throws Exception {
    assertRequestWithBody(
        "PUT",
        "/courses/1aab45ba-3c7a-4344-8936-78466eca77fa",
        "{\"name\": \"The best course\", \"duration\": \"5 hours\"}",
        201);

  }
}
