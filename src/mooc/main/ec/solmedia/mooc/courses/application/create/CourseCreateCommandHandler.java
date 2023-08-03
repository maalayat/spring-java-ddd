package ec.solmedia.mooc.courses.application.create;

import ec.solmedia.mooc.courses.domain.CourseDuration;
import ec.solmedia.mooc.courses.domain.CourseId;
import ec.solmedia.mooc.courses.domain.CourseName;
import ec.solmedia.shared.domain.Service;
import ec.solmedia.shared.domain.command.CommandHandler;

@Service
public class CourseCreateCommandHandler implements CommandHandler<CourseCreateCommand> {

  private final CourseCreator creator;

  public CourseCreateCommandHandler(CourseCreator creator) {
    this.creator = creator;
  }

  @Override
  public void handle(CourseCreateCommand command) {
    final var courseId = new CourseId(command.id());
    final var courseName = new CourseName(command.name());
    final var courseDuration = new CourseDuration(command.duration());
    this.creator.create(courseId, courseName, courseDuration);

  }
}
