package ec.solmedia.mooc.courses.infrastructure.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Embeddable
public final class CourseNameEntity {

  @Column(name = "name")
  private String name;

  public CourseNameEntity() {
  }

  public CourseNameEntity(String value) {
    this.name = value;
  }

}
