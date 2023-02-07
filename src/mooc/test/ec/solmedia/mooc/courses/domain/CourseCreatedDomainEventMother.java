package ec.solmedia.mooc.courses.domain;

public class CourseCreatedDomainEventMother {

  public static CourseCreatedDomainEvent fromCourse(Course course) {
    return new CourseCreatedDomainEvent(
        course.id().value(),
        course.name().value(),
        course.duration().value());
  }

  public static CourseCreatedDomainEvent random() {
    return new CourseCreatedDomainEvent(
        CourseIdMother.random().value(),
        CourseNameMother.random().value(),
        CourseDurationMother.random().value()
    );
  }
}
