package ec.solmedia.app;

import ec.solmedia.mooc.courses.infrastructure.entities.CourseEntity;
import ec.solmedia.mooc.courses.infrastructure.persistence.CourseRepositoryImpl;
import ec.solmedia.mooc.courses_counter.infrastructure.entities.CoursesCounterEntity;
import ec.solmedia.mooc.courses_counter.infrastructure.persistence.CourseCounterRepositoryImpl;
import ec.solmedia.shared.domain.Service;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackageClasses =
    {CourseRepositoryImpl.class, CourseCounterRepositoryImpl.class})
@EntityScan(basePackageClasses = {CourseEntity.class, CoursesCounterEntity.class})
@ComponentScan(
    includeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Service.class),
    value = {"ec.solmedia.app", "ec.solmedia.mooc", "ec.solmedia.backoffice", "ec.solmedia.shared"})
public class Starter {

  public static void main(String[] args) {
    SpringApplication.run(Starter.class, args);
  }

}
