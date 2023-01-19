package ec.solmedia.mooc.courses_counter.infrastructure.entities;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;
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
