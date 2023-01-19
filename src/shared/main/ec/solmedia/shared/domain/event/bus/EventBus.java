package ec.solmedia.shared.domain.event.bus;

import java.util.List;

public interface EventBus {

  void publish(final List<DomainEvent> events);

}
