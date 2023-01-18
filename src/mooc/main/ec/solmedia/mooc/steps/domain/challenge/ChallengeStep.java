package ec.solmedia.mooc.steps.domain.challenge;

import ec.solmedia.mooc.steps.domain.Step;
import ec.solmedia.mooc.steps.domain.StepId;
import ec.solmedia.mooc.steps.domain.StepTitle;
import java.util.Objects;

public final class ChallengeStep extends Step {

  private final ChallengeStepStatement statement;

  public ChallengeStep(StepId id, StepTitle title, ChallengeStepStatement statement) {
    super(id, title);

    this.statement = statement;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    if (!super.equals(o)) {
      return false;
    }
    ChallengeStep that = (ChallengeStep) o;
    return Objects.equals(statement, that.statement);
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), statement);
  }

}
