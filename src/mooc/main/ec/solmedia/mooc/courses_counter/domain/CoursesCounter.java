package ec.solmedia.mooc.courses_counter.domain;

import ec.solmedia.mooc.courses.domain.CourseId;
import java.util.List;
import java.util.Objects;

public final class CoursesCounter {

  private final CoursesCounterId id;
  private CoursesCounterTotal total;

  private final List<CourseId> existingCourses;

  public CoursesCounter(CoursesCounterId id, CoursesCounterTotal total,
      List<CourseId> existingCourses) {
    this.id = id;
    this.total = total;
    this.existingCourses = existingCourses;
  }

  public static CoursesCounter initialize(String id) {
    return new CoursesCounter(
        new CoursesCounterId(id),
        CoursesCounterTotal.initialize(),
        List.of()
    );
  }

  public boolean hasNotIncremented(CourseId courseId) {
    return !existingCourses.contains(courseId);
  }

  public void increment(CourseId courseId) {
    total = total.increment();
    existingCourses.add(courseId);
  }

  public CoursesCounterId id() {
    return id;
  }

  public CoursesCounterTotal total() {
    return total;
  }

  public List<CourseId> existingCourses() {
    return existingCourses;
  }

  public CoursesCounterId getId() {
    return id;
  }

  public CoursesCounterTotal getTotal() {
    return total;
  }

  public List<CourseId> getExistingCourses() {
    return existingCourses;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CoursesCounter that = (CoursesCounter) o;
    return Objects.equals(id, that.id) && Objects.equals(total, that.total)
        && Objects.equals(existingCourses, that.existingCourses);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, total, existingCourses);
  }
}
