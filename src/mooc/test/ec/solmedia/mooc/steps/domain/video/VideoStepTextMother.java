package ec.solmedia.mooc.steps.domain.video;

import ec.solmedia.shared.domain.WordMother;

public final class VideoStepTextMother {

  public static VideoStepText create(String value) {
    return new VideoStepText(value);
  }

  public static VideoStepText random() {
    return create(WordMother.random());
  }
}
