package ec.solmedia.app;

import ec.solmedia.mooc.courses.infrastructure.entities.CourseEntity;
import ec.solmedia.mooc.courses.infrastructure.persistence.CourseRepositoryImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackageClasses = CourseRepositoryImpl.class)
@EntityScan(basePackageClasses = CourseEntity.class)
@ComponentScan({"ec.solmedia.app", "ec.solmedia.mooc", "ec.solmedia.backoffice"})
public class Starter {

  public static void main(String[] args) {
    SpringApplication.run(Starter.class, args);
  }

}
