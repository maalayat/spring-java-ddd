package ec.solmedia.mooc.courses.infrastructure.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@Table(name = "domain_events")
public class DomainEventsEntity {

  @Id
  private String id;

  @Column(name = "aggregate_id")
  private String aggregateId;

  private String name;

  @JdbcTypeCode(SqlTypes.LONGVARCHAR)
  @Column(columnDefinition = "json")
  private String body;

  @Column(name = "occurred_on")
  private String occurredOn;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getAggregateId() {
    return aggregateId;
  }

  public void setAggregateId(String aggregateId) {
    this.aggregateId = aggregateId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getBody() {
    return body;
  }

  public void setBody(String body) {
    this.body = body;
  }

  public String getOccurredOn() {
    return occurredOn;
  }

  public void setOccurredOn(String occurredOn) {
    this.occurredOn = occurredOn;
  }
}
