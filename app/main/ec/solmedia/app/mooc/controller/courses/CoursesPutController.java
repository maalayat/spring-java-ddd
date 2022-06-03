package ec.solmedia.app.mooc.controller.courses;

import ec.solmedia.mooc.courses.application.create.CourseCreator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CoursesPutController {

  private final CourseCreator creator;

  public CoursesPutController(CourseCreator creator) {
    this.creator = creator;
  }

  @PutMapping("/courses/{id}")
  public ResponseEntity<Object> create(@PathVariable String id, @RequestBody Request request) {
    creator.create(id, request.getName(), request.getDuration());
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }

}

final class Request {
  private final String name;
  private final String duration;

  public Request(String name, String duration) {
    this.name = name;
    this.duration = duration;
  }

  public String getName() {
    return name;
  }

  public String getDuration() {
    return duration;
  }
}
