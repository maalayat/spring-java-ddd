package ec.solmedia.app.mooc.controller.courses_counter;

import ec.solmedia.mooc.courses_counter.application.find.CoursesCounterResponse;
import ec.solmedia.mooc.courses_counter.application.find.FindCoursesCounterQuery;
import ec.solmedia.shared.domain.query.QueryBus;
import ec.solmedia.shared.domain.query.QueryNotRegistered;
import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public final class CoursesCounterGetController {

  private final QueryBus queryBus;

  public CoursesCounterGetController(QueryBus queryBus) {
    this.queryBus = queryBus;
  }

  @GetMapping("/courses-counter")
  public Map<String, Integer> index() throws QueryNotRegistered {
    final var response = queryBus.<CoursesCounterResponse>ask(new FindCoursesCounterQuery());

    return Map.of("total", response.getTotal());
  }
}
