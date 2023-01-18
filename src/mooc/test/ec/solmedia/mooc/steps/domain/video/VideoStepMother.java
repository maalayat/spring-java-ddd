package ec.solmedia.mooc.steps.domain.video;

import ec.solmedia.mooc.steps.domain.StepId;
import ec.solmedia.mooc.steps.domain.StepIdMother;
import ec.solmedia.mooc.steps.domain.StepTitle;
import ec.solmedia.mooc.steps.domain.StepTitleMother;

public final class VideoStepMother {

  public static VideoStep create(StepId id, StepTitle title, VideoUrl videoUrl,
      VideoStepText text) {
    return new VideoStep(id, title, videoUrl, text);
  }

  public static VideoStep random() {
    return create(
        StepIdMother.random(),
        StepTitleMother.random(),
        VideoUrlMother.random(),
        VideoStepTextMother.random()
    );
  }
}
