package ec.solmedia.mooc.courses_counter.application.find;

import ec.solmedia.shared.domain.Service;
import ec.solmedia.shared.domain.query.QueryHandler;

@Service
public class FindCoursesCounterQueryHandler implements
    QueryHandler<FindCoursesCounterQuery, CoursesCounterResponse> {

  private final CoursesCounterFinder finder;

  public FindCoursesCounterQueryHandler(CoursesCounterFinder finder) {
    this.finder = finder;
  }

  @Override
  public CoursesCounterResponse handle(FindCoursesCounterQuery query) {
    return finder.find();
  }
}
