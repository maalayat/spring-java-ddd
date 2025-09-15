package ec.solmedia.shared.infrastructure;

import ec.solmedia.app.Starter;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;

@Import(TestcontainersConfiguration.class)
@SpringBootTest
@ContextConfiguration(classes = Starter.class)
public abstract class InfrastructureTestCase {

}
