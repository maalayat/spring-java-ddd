package ec.solmedia.mooc.courses.infrastructure.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Getter
@Setter
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

}
