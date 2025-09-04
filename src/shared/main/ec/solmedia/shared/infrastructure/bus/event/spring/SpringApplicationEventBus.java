package ec.solmedia.shared.infrastructure.bus.event.spring;

import ec.solmedia.shared.domain.event.bus.DomainEvent;
import ec.solmedia.shared.domain.event.bus.EventBus;
import java.util.List;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
public class SpringApplicationEventBus implements EventBus {

  private final ApplicationEventPublisher publisher;

  public SpringApplicationEventBus(ApplicationEventPublisher publisher) {
    this.publisher = publisher;
  }

  @Override
  public void publish(List<DomainEvent> events) {
    events.forEach(publisher::publishEvent);
  }

}
