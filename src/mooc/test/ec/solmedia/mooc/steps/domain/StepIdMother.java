package ec.solmedia.mooc.steps.domain;

import ec.solmedia.shared.domain.UuidMother;

public final class StepIdMother {

  public static StepId create(String value) {
    return new StepId(value);
  }

  public static StepId random() {
    return create(UuidMother.random());
  }
}
