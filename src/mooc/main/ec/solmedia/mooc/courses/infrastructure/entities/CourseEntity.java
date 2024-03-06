package ec.solmedia.mooc.courses.infrastructure.entities;

import jakarta.persistence.Embedded;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.util.Objects;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "courses")
public final class CourseEntity {

  @EmbeddedId
  private CourseIdEntity id;

  @Embedded
  private CourseNameEntity name;

  @Embedded
  private CourseDurationEntity duration;

  public CourseEntity() {
  }

  public CourseEntity(CourseIdEntity id, CourseNameEntity name, CourseDurationEntity duration) {
    this.id = id;
    this.name = name;
    this.duration = duration;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CourseEntity course = (CourseEntity) o;
    return id.equals(course.id) && name.equals(course.name) && duration.equals(course.duration);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name, duration);
  }
}
