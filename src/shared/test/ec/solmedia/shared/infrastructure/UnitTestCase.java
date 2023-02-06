package ec.solmedia.shared.infrastructure;

import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;

import ec.solmedia.shared.domain.event.bus.DomainEvent;
import ec.solmedia.shared.domain.event.bus.EventBus;
import java.util.Collections;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public abstract class UnitTestCase {

  @Mock
  protected EventBus eventBus;

  protected void shouldHavePublished(DomainEvent<?> domainEvents) {
    verify(eventBus, atLeastOnce()).publish(Collections.singletonList(domainEvents));
  }


}
