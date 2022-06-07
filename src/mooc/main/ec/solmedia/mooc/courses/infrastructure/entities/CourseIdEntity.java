package ec.solmedia.mooc.courses.infrastructure.entities;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;
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
