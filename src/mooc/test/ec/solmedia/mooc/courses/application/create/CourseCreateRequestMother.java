package ec.solmedia.mooc.courses.application.create;

import ec.solmedia.mooc.courses.domain.CourseDurationMother;
import ec.solmedia.mooc.courses.domain.CourseIdMother;

public class CourseCreateRequestMother {

  public static CourseCreateRequest random() {
    return new CourseCreateRequest(
        CourseIdMother.random().value(),
        CourseIdMother.random().value(),
        CourseDurationMother.random().value());
  }
}
