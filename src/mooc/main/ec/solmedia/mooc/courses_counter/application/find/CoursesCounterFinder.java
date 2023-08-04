package ec.solmedia.mooc.courses_counter.application.find;

import ec.solmedia.mooc.courses_counter.domain.CoursesCounterRepository;
import ec.solmedia.shared.domain.Service;
import java.util.NoSuchElementException;

@Service
public class CoursesCounterFinder {

  private final CoursesCounterRepository repository;

  public CoursesCounterFinder(CoursesCounterRepository repository) {
    this.repository = repository;
  }

  public CoursesCounterResponse find() {
    final var coursesCounter = repository.search().orElseThrow(NoSuchElementException::new);

    return new CoursesCounterResponse(coursesCounter.getTotal().value());
  }
}
