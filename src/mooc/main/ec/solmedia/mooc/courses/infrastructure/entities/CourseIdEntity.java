package ec.solmedia.mooc.courses.infrastructure.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Embeddable
public final class CourseIdEntity implements Serializable {

  @Column(name = "id")
  private String id;

  public CourseIdEntity() {
  }

  public CourseIdEntity(String value) {
    id = value;
  }

}
