package ec.solmedia.mooc.courses.domain;

import ec.solmedia.shared.domain.WordMother;

public final class CourseNameMother {

  public static CourseName random() {
    return new CourseName(WordMother.random());
  }
}
