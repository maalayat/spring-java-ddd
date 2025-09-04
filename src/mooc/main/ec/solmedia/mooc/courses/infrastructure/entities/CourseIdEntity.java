package ec.solmedia.mooc.courses.infrastructure.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public final class CourseIdEntity implements Serializable {

  @Column(name = "id")
  private String id;

  public CourseIdEntity() {
  }

  public CourseIdEntity(String value) {
    id = value;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }
}
