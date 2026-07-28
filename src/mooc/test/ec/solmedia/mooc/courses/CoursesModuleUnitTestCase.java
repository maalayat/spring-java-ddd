package ec.solmedia.mooc.courses;

import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;

import ec.solmedia.mooc.courses.application.create.CourseCreateCommandHandler;
import ec.solmedia.mooc.courses.application.create.CourseCreator;
import ec.solmedia.mooc.courses.domain.Course;
import ec.solmedia.mooc.courses.domain.CourseCreatedDomainEvent;
import ec.solmedia.mooc.courses.domain.CourseRepository;
import ec.solmedia.shared.domain.event.bus.DomainEvent;
import ec.solmedia.shared.infrastructure.UnitTestCase;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;

public abstract class CoursesModuleUnitTestCase extends UnitTestCase {

  @Mock
  protected CourseRepository repository;

  @InjectMocks
  protected CourseCreateCommandHandler handler;

  @BeforeEach
  protected void setUp() {
    handler = new CourseCreateCommandHandler(new CourseCreator(repository, eventBus));
  }

  protected void shouldHaveSaved(Course course) {
    verify(repository, atLeastOnce()).save(course);
  }

  protected void shouldHavePublished(CourseCreatedDomainEvent expectedEvent) {
    verify(eventBus, atLeastOnce()).publish(argThat((List<DomainEvent> events) ->
        events.stream().anyMatch(event ->
            event instanceof CourseCreatedDomainEvent publishedEvent
                && expectedEvent.aggregateId().equals(publishedEvent.aggregateId())
                && expectedEvent.name().equals(publishedEvent.name())
                && expectedEvent.duration().equals(publishedEvent.duration())
        )
    ));
  }
}
