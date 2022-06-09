package ec.solmedia.shared.infrastructure;

import ec.solmedia.app.Starter;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest
@ContextConfiguration(classes = Starter.class)
public abstract class InfrastructureTestCase {

}
