package ec.solmedia.mooc.courses.application.create;

import ec.solmedia.mooc.courses.domain.CourseDurationMother;
import ec.solmedia.mooc.courses.domain.CourseIdMother;

public class CourseCreateCommandMother {

  public static CourseCreateCommand random() {
    return new CourseCreateCommand(
        CourseIdMother.random().value(),
        CourseIdMother.random().value(),
        CourseDurationMother.random().value());
  }
}
