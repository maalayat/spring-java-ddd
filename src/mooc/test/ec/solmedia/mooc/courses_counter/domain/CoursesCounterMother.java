package ec.solmedia.mooc.courses_counter.domain;

import ec.solmedia.mooc.courses.domain.CourseIdMother;
import java.util.List;

public class CoursesCounterMother {

  public static CoursesCounter random() {
    return new CoursesCounter(
        CoursesCounterIdMother.random(),
        new CoursesCounterTotal(3),
        List.of(CourseIdMother.random(), CourseIdMother.random(), CourseIdMother.random())
    );
  }
}
