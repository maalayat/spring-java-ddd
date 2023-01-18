package ec.solmedia.mooc.steps.domain.video;

import ec.solmedia.shared.domain.MotherCreator;

public final class VideoUrlMother {

  public static VideoUrl create(String value) {
    return new VideoUrl(value);
  }

  public static VideoUrl random() {
    return create(MotherCreator.random().internet().url());
  }
}
