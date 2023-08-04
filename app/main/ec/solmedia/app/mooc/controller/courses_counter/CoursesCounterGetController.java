package ec.solmedia.app.mooc.controller.courses_counter;

import ec.solmedia.mooc.courses_counter.application.find.CoursesCounterFinder;
import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public final class CoursesCounterGetController {

  private final CoursesCounterFinder finder;

  public CoursesCounterGetController(CoursesCounterFinder finder) {
    this.finder = finder;
  }

  @GetMapping("/courses-counter")
  public Map<String, Integer> index() {
    final var response = finder.find();

    return Map.of("total", response.getTotal());
  }
}
