package ec.solmedia.mooc.courses_counter.domain;

import ec.solmedia.shared.domain.UuidMother;

public class CoursesCounterIdMother {

  public static CoursesCounterId random() {
    return new CoursesCounterId(UuidMother.random());
  }
}
