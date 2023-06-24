package ec.solmedia.mooc.shared.infrastructure.bus.event.mysql;

import ec.solmedia.mooc.courses.domain.CourseCreatedDomainEventMother;
import ec.solmedia.shared.infrastructure.InfrastructureTestCase;
import ec.solmedia.shared.infrastructure.bus.event.mysql.MySqlEventBus;
import java.util.Collections;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class MysqlEventBusTest extends InfrastructureTestCase {

  @Autowired
  private MySqlEventBus mysqlEventBus;

  @Test
  void shouldPublishEvent() {
    final var event = CourseCreatedDomainEventMother.random();

    mysqlEventBus.publish(Collections.singletonList(event));
  }
}
