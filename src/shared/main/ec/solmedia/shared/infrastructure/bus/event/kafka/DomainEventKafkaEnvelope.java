package ec.solmedia.shared.infrastructure.bus.event.kafka;

public final class DomainEventKafkaEnvelope {

  public static final String ENVELOPE_DATA = "data";
  public static final String ENVELOPE_META = "meta";
  public static final String DATA_ID = "id";
  public static final String DATA_TYPE = "type";
  public static final String DATA_OCCURRED_ON = "occurred_on";
  public static final String DATA_ATTRIBUTES = "attributes";
  public static final String ATTRIBUTE_ID = "id";
  public static final String AGGREGATE_ID = "aggregateId";
  public static final String EVENT_ID = "eventId";
  public static final String OCCURRED_ON = "occurredOn";

  private DomainEventKafkaEnvelope() {
  }
}
