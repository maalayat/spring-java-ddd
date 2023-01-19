package ec.solmedia.mooc.courses_counter.infrastructure.persistence;

import ec.solmedia.mooc.courses_counter.domain.CoursesCounter;
import ec.solmedia.mooc.courses_counter.domain.CoursesCounterRepository;
import ec.solmedia.mooc.courses_counter.infrastructure.mapper.CoursesCounterMapper;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class MySqlCourseCounterRepository implements CoursesCounterRepository {

  private final CourseCounterRepositoryImpl repository;
  private final CoursesCounterMapper mapper;

  @Override
  public void save(CoursesCounter coursesCounter) {
    final var coursesCounterEntity = mapper.toEntity(coursesCounter);
    repository.save(coursesCounterEntity);
  }

  @Override
  public Optional<CoursesCounter> search() {
    final var coursesCounter = repository.findAll().stream().findFirst();

    return coursesCounter.map(mapper::toDomain);
  }
}
