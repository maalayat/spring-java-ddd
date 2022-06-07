package ec.solmedia.mooc.courses.infrastructure.persistence;

import ec.solmedia.mooc.courses.domain.Course;
import ec.solmedia.mooc.courses.domain.CourseId;
import ec.solmedia.mooc.courses.domain.CourseRepository;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public class InMemoryCourseRepository implements CourseRepository {

  private final Map<String, Course> courses = new HashMap<>();

  @Override
  public void save(Course course) {
    courses.put(course.id().value(), course);
  }

  @Override
  public Optional<Course> search(CourseId courseId) {
    return Optional.ofNullable(courses.get(courseId.value()));
  }
}
