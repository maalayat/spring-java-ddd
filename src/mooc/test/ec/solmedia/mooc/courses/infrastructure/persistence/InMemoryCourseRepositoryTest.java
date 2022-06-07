package ec.solmedia.mooc.courses.infrastructure.persistence;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import ec.solmedia.mooc.courses.domain.CourseIdMother;
import ec.solmedia.mooc.courses.domain.CourseMother;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class InMemoryCourseRepositoryTest {

  @Test
  @DisplayName("Save a course")
  void saveCourse() {
    final var repository = new InMemoryCourseRepository();
    final var course = CourseMother.random();

    repository.save(course);
  }

  @Test
  @DisplayName("Return an existing course")
  void returnExistingCourse() {
    final var repository = new InMemoryCourseRepository();
    final var course = CourseMother.random();

    repository.save(course);

    assertEquals(Optional.of(course), repository.search(course.id()));
  }

  @Test
  @DisplayName("Not return a non existing course")
  void notReturnNonExistingCourse() {
    final var repository = new InMemoryCourseRepository();

    assertFalse(repository.search(CourseIdMother.random()).isPresent());
  }
}