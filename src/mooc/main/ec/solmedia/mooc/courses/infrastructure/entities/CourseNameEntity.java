package ec.solmedia.mooc.courses.infrastructure.entities;

import javax.persistence.Column;
import javax.persistence.Embeddable;
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
