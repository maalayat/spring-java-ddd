package ec.solmedia.mooc.courses.domain;

import ec.solmedia.shared.domain.WordMother;

public final class CourseDurationMother {

  public static CourseDuration random() {
    return new CourseDuration(WordMother.random());
  }
}
