package ec.solmedia.mooc.courses.application.create;

import ec.solmedia.shared.domain.command.Command;

public final class CourseCreateCommand implements Command {

  private final String id;
  private final String name;
  private final String duration;

  public CourseCreateCommand(String id, String name, String duration) {
    this.id = id;
    this.name = name;
    this.duration = duration;
  }

  public String id() {
    return id;
  }

  public String name() {
    return name;
  }

  public String duration() {
    return duration;
  }
}
