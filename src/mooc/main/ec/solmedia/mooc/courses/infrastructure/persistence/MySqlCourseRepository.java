package ec.solmedia.mooc.courses.infrastructure.persistence;

import ec.solmedia.mooc.courses.domain.Course;
import ec.solmedia.mooc.courses.domain.CourseId;
import ec.solmedia.mooc.courses.domain.CourseRepository;
import ec.solmedia.mooc.courses.infrastructure.mapper.CourseMapper;
import java.util.Optional;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

@Primary
@Repository
public class MySqlCourseRepository implements CourseRepository {

  private final CourseRepositoryImpl repository;
  private final CourseMapper mapper;

  public MySqlCourseRepository(CourseRepositoryImpl repository, CourseMapper mapper) {
    this.repository = repository;
    this.mapper = mapper;
  }

  @Override
  public void save(Course course) {
    final var entity = mapper.toEntity(course);
    repository.save(entity);
  }

  @Override
  public Optional<Course> search(CourseId courseId) {
    return repository.findById(mapper.toEntity(courseId))
        .map(mapper::toDomain);
  }

}
