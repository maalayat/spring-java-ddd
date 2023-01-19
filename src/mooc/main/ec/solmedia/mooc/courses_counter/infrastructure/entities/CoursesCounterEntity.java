package ec.solmedia.mooc.courses_counter.infrastructure.entities;

import ec.solmedia.mooc.courses.infrastructure.entities.CourseIdEntity;
import io.hypersistence.utils.hibernate.type.json.JsonType;
import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "courses_counter")
@TypeDef(name = "json", typeClass = JsonType.class)
public class CoursesCounterEntity implements Serializable {

  @EmbeddedId
  private CoursesCounterIdEntity id;

  @Embedded
  private CoursesCounterTotalEntity total;

  @Type(type = "json")
  @Column(name = "existing_courses", columnDefinition = "json")
  private List<CourseIdEntity> existingCourses;

}
