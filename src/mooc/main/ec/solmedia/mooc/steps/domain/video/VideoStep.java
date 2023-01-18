package ec.solmedia.mooc.steps.domain.video;

import ec.solmedia.mooc.steps.domain.Step;
import ec.solmedia.mooc.steps.domain.StepId;
import ec.solmedia.mooc.steps.domain.StepTitle;

public final class VideoStep extends Step {

  private final VideoUrl videoUrl;
  private final VideoStepText text;

  public VideoStep(StepId id, StepTitle title, VideoUrl videoUrl, VideoStepText text) {
    super(id, title);

    this.videoUrl = videoUrl;
    this.text = text;
  }

}
