package ec.solmedia.mooc.courses_counter.domain;

import java.util.Optional;

public interface CoursesCounterRepository {

  void save(CoursesCounter coursesCounter);

  Optional<CoursesCounter> search();

}
