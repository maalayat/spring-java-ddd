package ec.solmedia.mooc.courses.infrastructure.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public final class CourseDurationEntity {

  @Column(name = "duration")
  private String duration;

  public CourseDurationEntity() {
  }

  public CourseDurationEntity(String value) {
    this.duration = value;
  }

  public String getDuration() {
    return duration;
  }

  public void setDuration(String duration) {
    this.duration = duration;
  }
}
