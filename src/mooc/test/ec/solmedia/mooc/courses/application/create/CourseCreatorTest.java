package ec.solmedia.mooc.courses.application.create;

import ec.solmedia.mooc.courses.CoursesModuleUnitTestCase;
import ec.solmedia.mooc.courses.domain.CourseCreatedDomainEventMother;
import ec.solmedia.mooc.courses.domain.CourseMother;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CourseCreatorTest extends CoursesModuleUnitTestCase {

  @Test
  @DisplayName("Given a course creator request when create a valid course then an event occurs")
  void shouldCreateAValidCourse() {
    final var request = CourseCreateRequestMother.random();

    final var course = CourseMother.fromRequest(request);
    final var domainEvent = CourseCreatedDomainEventMother.fromCourse(course);

    creator.create(request);

    shouldHaveSaved(course);
    shouldHavePublished(domainEvent);
  }
}
