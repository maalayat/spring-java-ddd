package ec.solmedia.shared.infrastructure.bus.event.mysql;

import ec.solmedia.shared.domain.Service;
import ec.solmedia.shared.domain.event.bus.DomainEvent;
import ec.solmedia.shared.domain.event.bus.EventBus;
import java.util.List;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

@Service
public class MySqlEventBus implements EventBus {

  private final NamedParameterJdbcTemplate jdbcTemplate;

  public MySqlEventBus(NamedParameterJdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  @Override
  public void publish(List<DomainEvent> events) {
    events.forEach(this::publish);
  }

  private void publish(DomainEvent domainEvent) {
    final var params = new MapSqlParameterSource()
        .addValue("id", domainEvent.eventId())
        .addValue("aggregateId", domainEvent.aggregateId())
        .addValue("name", domainEvent.eventName())
        .addValue("body", domainEvent.toJson())
        .addValue("occurredOn", domainEvent.occurredOn());

    jdbcTemplate.update(
        "INSERT INTO domain_events (id,  aggregate_id, name,  body,  occurred_on) VALUES (:id, :aggregateId, :name, :body, :occurredOn)",
        params);
  }

}
