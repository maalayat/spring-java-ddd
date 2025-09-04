package ec.solmedia.mooc.courses.infrastructure.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public final class CourseNameEntity {

  @Column(name = "name")
  private String name;

  public CourseNameEntity() {
  }

  public CourseNameEntity(String value) {
    this.name = value;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
