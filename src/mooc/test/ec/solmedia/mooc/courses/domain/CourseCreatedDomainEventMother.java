package ec.solmedia.mooc.courses.domain;

public class CourseCreatedDomainEventMother {

  public static CourseCreatedDomainEvent fromCourse(Course course) {
    return new CourseCreatedDomainEvent(
        course.id().value(),
        course.name().value(),
        course.duration().value());
  }
}
