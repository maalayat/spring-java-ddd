package ec.solmedia.mooc.courses.application.create;

import ec.solmedia.shared.domain.command.Command;

public record CourseCreateCommand(String id, String name, String duration) implements Command {

}
