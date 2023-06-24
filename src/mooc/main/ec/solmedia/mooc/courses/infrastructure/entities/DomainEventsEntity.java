package ec.solmedia.mooc.courses.infrastructure.entities;

import com.vladmihalcea.hibernate.type.json.JsonType;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

@Getter
@Setter
@Entity
@Table(name = "domain_events")
@TypeDef(
    name = "json",
    typeClass = JsonType.class
)
public class DomainEventsEntity {

  @Id
  private String id;

  @Column(name = "aggregate_id")
  private String aggregateId;

  private String name;

  @Type(type = "json")
  @Column(columnDefinition = "json")
  private String body;

  @Column(name = "occurred_on")
  private String occurredOn;

}
