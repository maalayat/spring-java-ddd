package ec.solmedia.mooc.courses_counter.infrastructure.persistence;

import ec.solmedia.mooc.courses_counter.infrastructure.entities.CoursesCounterEntity;
import ec.solmedia.mooc.courses_counter.infrastructure.entities.CoursesCounterIdEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseCounterRepositoryImpl extends JpaRepository<CoursesCounterEntity, CoursesCounterIdEntity> {

}
