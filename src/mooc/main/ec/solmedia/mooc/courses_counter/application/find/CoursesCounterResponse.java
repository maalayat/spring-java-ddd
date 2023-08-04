package ec.solmedia.mooc.courses_counter.application.find;

public class CoursesCounterResponse {

  private final Integer total;

  public CoursesCounterResponse(Integer total) {
    this.total = total;
  }

  public Integer getTotal() {
    return total;
  }
}
