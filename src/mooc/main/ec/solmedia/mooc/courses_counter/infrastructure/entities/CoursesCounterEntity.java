package ec.solmedia.mooc.courses_counter.infrastructure.entities;

import ec.solmedia.mooc.courses.infrastructure.entities.CourseIdEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Embedded;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "courses_counter")
public class CoursesCounterEntity implements Serializable {

  @EmbeddedId
  private CoursesCounterIdEntity id;

  @Embedded
  private CoursesCounterTotalEntity total;

  @Column(name = "existing_courses", columnDefinition = "json")
  @Convert(converter = JpaConverterJson.class)
  private List<CourseIdEntity> existingCourses;

}
