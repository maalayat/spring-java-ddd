package ec.solmedia.mooc.courses.infrastructure.mapper;

import ec.solmedia.mooc.courses.domain.Course;
import ec.solmedia.mooc.courses.domain.CourseDuration;
import ec.solmedia.mooc.courses.domain.CourseId;
import ec.solmedia.mooc.courses.domain.CourseName;
import ec.solmedia.mooc.courses.infrastructure.entities.CourseDurationEntity;
import ec.solmedia.mooc.courses.infrastructure.entities.CourseEntity;
import ec.solmedia.mooc.courses.infrastructure.entities.CourseIdEntity;
import ec.solmedia.mooc.courses.infrastructure.entities.CourseNameEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CourseMapper {

  Course toDomain(CourseEntity entity);

  @Mapping(target = "value", source = "id")
  CourseId toDomain(CourseIdEntity entity);

  @Mapping(target = "value", source = "name")
  CourseName toDomain(CourseNameEntity entity);

  @Mapping(target = "value", source = "duration")
  CourseDuration toDomain(CourseDurationEntity entity);

  CourseEntity toEntity(Course course);

  @Mapping(target = "id", source = "value")
  CourseIdEntity toEntity(CourseId domain);

  @Mapping(target = "name", source = "value")
  CourseNameEntity toEntity(CourseName domain);

  @Mapping(target = "duration", source = "value")
  CourseDurationEntity toEntity(CourseDuration domain);

}
