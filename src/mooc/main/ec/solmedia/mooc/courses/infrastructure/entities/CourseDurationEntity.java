package ec.solmedia.mooc.courses.infrastructure.entities;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Embeddable
public final class CourseDurationEntity {

  @Column(name = "duration")
  private String duration;

  public CourseDurationEntity() {
  }

  public CourseDurationEntity(String value) {
    this.duration = value;
  }

}
