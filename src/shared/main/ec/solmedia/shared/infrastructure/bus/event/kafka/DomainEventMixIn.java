package ec.solmedia.shared.infrastructure.bus.event.kafka;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import ec.solmedia.shared.domain.event.bus.DomainEvent;

@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "@class")
public interface DomainEventMixIn {

  static ObjectMapper objectMapper() {
    ObjectMapper mapper = new ObjectMapper();
    mapper.addMixIn(DomainEvent.class, DomainEventMixIn.class);
    return mapper;
  }
}
