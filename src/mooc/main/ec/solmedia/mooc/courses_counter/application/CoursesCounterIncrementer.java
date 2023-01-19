package ec.solmedia.mooc.courses_counter.application;

import ec.solmedia.mooc.courses.domain.CourseId;
import ec.solmedia.mooc.courses_counter.domain.CoursesCounter;
import ec.solmedia.mooc.courses_counter.domain.CoursesCounterRepository;
import ec.solmedia.shared.domain.Service;
import java.util.UUID;

@Service
public class CoursesCounterIncrementer {

  private final CoursesCounterRepository repository;

  public CoursesCounterIncrementer(CoursesCounterRepository repository) {
    this.repository = repository;
  }

  public void increment(CourseId courseId) {
    final var coursesCounter = repository.search()
        .orElseGet(() -> CoursesCounter.initialize(UUID.randomUUID().toString()));

    if (coursesCounter.hasNotIncremented(courseId)) {
      coursesCounter.increment(courseId);
      repository.save(coursesCounter);
    }
  }
}
