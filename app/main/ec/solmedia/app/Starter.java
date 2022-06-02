package ec.solmedia.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"ec.solmedia.app", "ec.solmedia.mooc", "ec.solmedia.backoffice"})
public class Starter {

  public static void main(String[] args) {
    SpringApplication.run(Starter.class, args);
  }

}
