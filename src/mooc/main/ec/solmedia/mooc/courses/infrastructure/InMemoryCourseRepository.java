package ec.solmedia.mooc.courses.infrastructure;

import ec.solmedia.mooc.courses.domain.Course;
import ec.solmedia.mooc.courses.domain.CourseRepository;
import ec.solmedia.shared.domain.Service;

@Service
public class InMemoryCourseRepository implements CourseRepository {

  @Override
  public void save(Course course) {

  }
}
