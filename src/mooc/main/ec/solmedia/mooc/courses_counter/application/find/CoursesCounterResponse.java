package ec.solmedia.mooc.courses_counter.application.find;

import ec.solmedia.shared.domain.query.Response;

public record CoursesCounterResponse(Integer total) implements Response {

}
