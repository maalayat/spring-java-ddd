package ec.solmedia.mooc.courses_counter.application;

import ec.solmedia.mooc.courses.domain.CourseCreatedDomainEventMother;
import ec.solmedia.mooc.courses.domain.CourseIdMother;
import ec.solmedia.mooc.courses_counter.CoursesCounterModuleUnitTestCase;
import ec.solmedia.mooc.courses_counter.domain.CoursesCounterMother;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class IncrementCoursesCounterOnCourseCreatedTest extends CoursesCounterModuleUnitTestCase {

  private IncrementCoursesCounterOnCourseCreated subscriber;

  @BeforeEach
  void setUp() {
    subscriber = new IncrementCoursesCounterOnCourseCreated(
        new CoursesCounterIncrementer(repository, uuidGenerator)
    );
  }

  @Test
  void shouldInitializeNewCounter() {
    final var event = CourseCreatedDomainEventMother.random();

    final var courseId = CourseIdMother.create(event.aggregateId());
    final var newCounter = CoursesCounterMother.withCourseId(courseId);

    shouldMockSearch();
    shouldMockUuid(newCounter.id().value());

    subscriber.on(event);

    shouldHaveSaved(newCounter);
  }

  @Test
  void shouldIncrementAnValidCourse() {
    final var event = CourseCreatedDomainEventMother.random();

    final var courseId = CourseIdMother.create(event.aggregateId());
    final var coursesCounter = CoursesCounterMother.random();
    final var incrementedCounter = CoursesCounterMother.incrementing(coursesCounter, courseId);

    shouldMockSearch(coursesCounter);

    subscriber.on(event);

    shouldHaveSaved(incrementedCounter);
  }

  @Test
  void shouldNotIncrementAnAlreadyIncrementedCourse() {
    final var event = CourseCreatedDomainEventMother.random();

    final var courseId = CourseIdMother.create(event.aggregateId());
    final var existingCounter = CoursesCounterMother.withCourseId(courseId);

    shouldMockSearch(existingCounter);

    subscriber.on(event);

    shouldNotHaveSaved(existingCounter);
  }

}
