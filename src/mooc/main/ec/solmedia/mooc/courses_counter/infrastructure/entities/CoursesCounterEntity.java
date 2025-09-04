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

@Entity
@Table(name = "courses_counter")
public class CoursesCounterEntity implements Serializable {

  @EmbeddedId
  private CoursesCounterIdEntity id;

  @Embedded
  private CoursesCounterTotalEntity total;

  @Column(name = "existing_courses", columnDefinition = "json")
  @Convert(converter = JpaConverterJson.class)
  private List<CourseIdEntity> existingCourses;

  public CoursesCounterEntity() {
  }

  public CoursesCounterIdEntity getId() {
    return id;
  }

  public void setId(CoursesCounterIdEntity id) {
    this.id = id;
  }

  public CoursesCounterTotalEntity getTotal() {
    return total;
  }

  public void setTotal(CoursesCounterTotalEntity total) {
    this.total = total;
  }

  public List<CourseIdEntity> getExistingCourses() {
    return existingCourses;
  }

  public void setExistingCourses(List<CourseIdEntity> existingCourses) {
    this.existingCourses = existingCourses;
  }
}
