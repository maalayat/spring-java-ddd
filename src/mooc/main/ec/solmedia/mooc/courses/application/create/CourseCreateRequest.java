package ec.solmedia.mooc.courses.application.create;

import java.util.Objects;

public final class CourseCreateRequest {

  private final String id;
  private final String name;
  private final String duration;

  public CourseCreateRequest(String id, String name, String duration) {
    this.id = id;
    this.name = name;
    this.duration = duration;
  }

  public String getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public String getDuration() {
    return duration;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CourseCreateRequest course = (CourseCreateRequest) o;
    return Objects.equals(id, course.id) && Objects.equals(name, course.name)
        && Objects.equals(duration, course.duration);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name, duration);
  }
}
