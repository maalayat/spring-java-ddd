package ec.solmedia.mooc.courses_counter.infrastructure.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Embeddable
@NoArgsConstructor
public class CoursesCounterTotalEntity implements Serializable {

  @Column(name = "total")
  private Integer total;

}
