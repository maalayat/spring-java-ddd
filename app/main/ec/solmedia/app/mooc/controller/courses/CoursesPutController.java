package ec.solmedia.app.mooc.controller.courses;

import ec.solmedia.mooc.courses.application.create.CourseCreateCommand;
import ec.solmedia.shared.domain.command.CommandBus;
import ec.solmedia.shared.domain.command.CommandNotRegistered;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CoursesPutController {

  private final CommandBus commandBus;

  public CoursesPutController(CommandBus commandBus) {
    this.commandBus = commandBus;
  }

  @PutMapping("/courses/{id}")
  public ResponseEntity<Object> create(@PathVariable String id, @RequestBody Request request)
      throws CommandNotRegistered {
    commandBus.dispatch(new CourseCreateCommand(id, request.name(), request.duration()));

    return ResponseEntity.status(HttpStatus.CREATED).build();
  }

}

record Request(String name, String duration) {

}
