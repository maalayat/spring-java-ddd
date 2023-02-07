package ec.solmedia.mooc.courses_counter.application;

import ec.solmedia.mooc.courses.domain.CourseId;
import ec.solmedia.mooc.courses_counter.domain.CoursesCounter;
import ec.solmedia.mooc.courses_counter.domain.CoursesCounterRepository;
import ec.solmedia.shared.domain.Service;
import ec.solmedia.shared.domain.UuidGenerator;

@Service
public class CoursesCounterIncrementer {

  private final CoursesCounterRepository repository;
  private final UuidGenerator uuidGenerator;

  public CoursesCounterIncrementer(CoursesCounterRepository repository,
      UuidGenerator uuidGenerator) {
    this.repository = repository;
    this.uuidGenerator = uuidGenerator;
  }

  public void increment(CourseId courseId) {
    final var coursesCounter = repository.search()
        .orElseGet(() -> CoursesCounter.initialize(uuidGenerator.generate()));

    if (coursesCounter.hasNotIncremented(courseId)) {
      coursesCounter.increment(courseId);
      repository.save(coursesCounter);
    }
  }
}
