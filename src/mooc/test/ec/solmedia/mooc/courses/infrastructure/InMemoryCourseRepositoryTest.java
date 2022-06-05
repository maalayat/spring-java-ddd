package ec.solmedia.mooc.courses.infrastructure;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import ec.solmedia.mooc.courses.domain.Course;
import ec.solmedia.mooc.courses.domain.CourseDuration;
import ec.solmedia.mooc.courses.domain.CourseId;
import ec.solmedia.mooc.courses.domain.CourseName;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class InMemoryCourseRepositoryTest {

  @Test
  @DisplayName("Save a course")
  void saveCourse() {
    final var repository = new InMemoryCourseRepository();
    final var course = new Course(
        new CourseId("eb4392bd-8df8-435e-839f-03ebf92c3992"),
        new CourseName("some-name"),
        new CourseDuration("some-duration"));

    repository.save(course);
  }

  @Test
  @DisplayName("Return an existing course")
  void returnExistingCourse() {
    final var repository = new InMemoryCourseRepository();
    final var course = new Course(
        new CourseId("eb4392bd-8df8-435e-839f-03ebf92c3992"),
        new CourseName("some-name"),
        new CourseDuration("some-duration"));

    repository.save(course);

    assertEquals(Optional.of(course), repository.search(course.id()));
  }

  @Test
  @DisplayName("Not return a non existing course")
  void notReturnNonExistingCourse() {
    final var repository = new InMemoryCourseRepository();

    final var courseId = new CourseId("eb4392bd-8df8-435e-839f-03ebf92c3991");
    assertFalse(repository.search(courseId).isPresent());
  }
}