package ec.solmedia.mooc.courses_counter.domain;

import ec.solmedia.shared.domain.IntegerMother;

public class CoursesCounterTotalMother {

  public static CoursesCounterTotal create(Integer value) {
    return new CoursesCounterTotal(value);
  }

  public static CoursesCounterTotal random() {
    return create(IntegerMother.random());
  }

  public static CoursesCounterTotal one() {
    return create(1);
  }

}
