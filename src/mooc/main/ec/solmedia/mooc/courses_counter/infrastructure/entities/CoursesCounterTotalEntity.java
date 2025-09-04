package ec.solmedia.mooc.courses_counter.infrastructure.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class CoursesCounterTotalEntity implements Serializable {

  @Column(name = "total")
  private Integer total;

  public CoursesCounterTotalEntity() {
  }

  public Integer getTotal() {
    return total;
  }

  public void setTotal(Integer total) {
    this.total = total;
  }
}
