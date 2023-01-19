package ec.solmedia.mooc.courses_counter.infrastructure.mapper;

import ec.solmedia.mooc.courses.domain.CourseId;
import ec.solmedia.mooc.courses.infrastructure.entities.CourseIdEntity;
import ec.solmedia.mooc.courses_counter.domain.CoursesCounter;
import ec.solmedia.mooc.courses_counter.domain.CoursesCounterId;
import ec.solmedia.mooc.courses_counter.domain.CoursesCounterTotal;
import ec.solmedia.mooc.courses_counter.infrastructure.entities.CoursesCounterEntity;
import ec.solmedia.mooc.courses_counter.infrastructure.entities.CoursesCounterIdEntity;
import ec.solmedia.mooc.courses_counter.infrastructure.entities.CoursesCounterTotalEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CoursesCounterMapper {

  CoursesCounter toDomain(CoursesCounterEntity entity);

  @Mapping(target = "value", source = "id")
  CourseId toDomain(CourseIdEntity entity);

  @Mapping(target = "value", source = "id")
  CoursesCounterId toDomain(CoursesCounterIdEntity entity);

  @Mapping(target = "value", source = "total")
  CoursesCounterTotal toDomain(CoursesCounterTotalEntity entity);

  CoursesCounterEntity toEntity(CoursesCounter domain);

  @Mapping(target = "id", source = "value")
  CourseIdEntity toEntity(CourseId courseId);

  @Mapping(target = "id", source = "value")
  CoursesCounterIdEntity toEntity(CoursesCounterId domain);

  @Mapping(target = "total", source = "value")
  CoursesCounterTotalEntity toEntity(CoursesCounterTotal domain);


}
