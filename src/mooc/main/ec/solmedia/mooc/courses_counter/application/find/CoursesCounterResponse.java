package ec.solmedia.mooc.courses_counter.application.find;

import ec.solmedia.shared.domain.query.Response;

public class CoursesCounterResponse implements Response {

  private final Integer total;

  public CoursesCounterResponse(Integer total) {
    this.total = total;
  }

  public Integer getTotal() {
    return total;
  }
}
