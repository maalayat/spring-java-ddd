package ec.solmedia.mooc.courses.infrastructure;

import ec.solmedia.mooc.courses.domain.Course;
import ec.solmedia.mooc.courses.domain.CourseRepository;
import ec.solmedia.shared.domain.Service;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class InMemoryCourseRepository implements CourseRepository {

  private final Map<String, Course> courses = new HashMap<>();

  @Override
  public void save(Course course) {
    courses.put(course.getId(), course);
  }

  @Override
  public Optional<Course> search(String courseId) {
    return Optional.ofNullable(courses.get(courseId));
  }
}
