package ec.solmedia.mooc.courses_counter.infrastructure.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class CoursesCounterIdEntity implements Serializable {

  @Column(name = "id")
  private String id;

  public CoursesCounterIdEntity() {
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }
}
