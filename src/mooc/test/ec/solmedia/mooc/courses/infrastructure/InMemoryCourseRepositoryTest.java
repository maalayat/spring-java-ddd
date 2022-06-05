package ec.solmedia.mooc.courses.infrastructure;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import ec.solmedia.mooc.courses.domain.Course;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class InMemoryCourseRepositoryTest {

  @Test
  @DisplayName("Save a course")
  void saveCourse() {
    final var repository = new InMemoryCourseRepository();

    repository.save(new Course("some-id", "some-name", "some-duration"));
  }

  @Test
  @DisplayName("return an existing course")
  void returnExistingCourse() {
    final var repository = new InMemoryCourseRepository();
    final var course = new Course("some-id", "some-name", "some-duration");

    repository.save(course);

    assertEquals(Optional.of(course), repository.search(course.getId()));
  }

  @Test
  @DisplayName("Not return a non existing course")
  void notReturnNonExistingCourse() {
    final var repository = new InMemoryCourseRepository();

    assertFalse(repository.search("non-existing-id").isPresent());
  }
}