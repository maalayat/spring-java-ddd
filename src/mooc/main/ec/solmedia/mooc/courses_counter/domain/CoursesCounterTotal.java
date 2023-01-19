package ec.solmedia.mooc.courses_counter.domain;

import ec.solmedia.shared.domain.IntValueObject;

public final class CoursesCounterTotal extends IntValueObject {

  public CoursesCounterTotal(Integer value) {
    super(value);
  }

  public static CoursesCounterTotal initialize() {
    return new CoursesCounterTotal(1);
  }

  public CoursesCounterTotal increment() {
    return new CoursesCounterTotal(value() + 1);
  }

}
