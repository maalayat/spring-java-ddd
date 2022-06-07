package ec.solmedia.mooc.courses.infrastructure.persistence;

import ec.solmedia.mooc.courses.infrastructure.entities.CourseEntity;
import ec.solmedia.mooc.courses.infrastructure.entities.CourseIdEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepositoryImpl extends JpaRepository<CourseEntity, CourseIdEntity> {

}
