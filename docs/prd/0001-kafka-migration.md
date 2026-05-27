# PRD: Migrate Domain Event Bus from RabbitMQ to Kafka

## Problem Statement

The current domain event infrastructure uses RabbitMQ with a reflection-based subscriber discovery and serialization layer that is overengineered for the project's needs. The `org.reflections` library scans the classpath at startup to find subscribers annotated with `@DomainEventSubscriber`, and the `DomainEventJsonDeserializer` creates null no-arg instances of each event class to invoke `fromPrimitives()` via reflection. Every domain event subclass requires a meaningless no-arg constructor, three serialization methods (`toPrimitives()`, `toJson()`, `fromPrimitives()`), plus `equals`/`hashCode` — roughly 40 lines of boilerplate per event.

The RabbitMQ topology itself is complex: 3 topic exchanges (main, retry, dead_letter), 3 queues per subscriber, retry queues with 1s TTL, and manual redelivery counting in message headers. A Guava dependency exists solely for `CaseFormat` to convert subscriber class names to queue names.

This infrastructure makes adding new domain events more expensive than it should be, couples the domain layer to serialization concerns, and requires developers to understand the reflection pipeline to debug event flow.

## Solution

Replace RabbitMQ with Kafka using per-type topics and explicit infrastructure-level consumers. Keep the domain layer pure — no Jackson annotations, no framework dependencies. Serialization and deserialization live entirely in the infrastructure layer using Jackson mix-ins, not reflection.

The `EventBus` domain interface stays unchanged. Application services and domain aggregates are untouched. Only the infrastructure layer's messaging implementation changes.

## User Stories

1. As a developer, I want domain events to be published to Kafka topics, so that the system reliably distributes events to subscribers.
2. As a developer, I want each domain event type to have its own Kafka topic, so that consumers only receive events they care about and topic naming matches domain language.
3. As a developer, I want subscribers to use explicit `@KafkaListener` annotations on infrastructure-layer classes, so that the event handling pipeline is transparent, debuggable, and doesn't require reflection.
4. As a developer, I want the `DomainEvent` base class to be free of infrastructure annotations and serialization methods, so that the domain layer remains pure DDD.
5. As a developer, I want serialization and deserialization logic to live in the infrastructure layer, so that domain classes don't depend on Jackson, Serializable, or any framework.
6. As a developer, I want the no-arg constructors removed from domain event classes, so that event objects are always fully initialized with valid state.
7. As a developer, I want to remove the Guava dependency from the project, so that the project has fewer transitive library constraints.
8. As a developer, I want to remove the entire RabbitMQ infrastructure package and its test dependencies, so that the codebase doesn't maintain two messaging implementations with different topologies.
9. As a developer, I want events that fail Kafka delivery after retries to be persisted to MySQL, so that no events are silently lost and operators have a dead-letter store for manual inspection.
10. As a developer, I want the `EventBus` domain interface to remain unchanged, so that existing application services don't need modification.
11. As a developer, I want new domain events to require only a new topic declaration and a new infrastructure consumer class, so that adding an event is a mechanical, low-ceremony process.
12. As the system, I want Kafka to reliably deliver each event to at least one consumer, so that business invariants are eventually consistent.
13. As a developer, I want the Spring in-process event bus retained as an optional escape hatch, so that synchronous in-process event handling remains available for future use cases without adding infrastructure dependencies.
14. As a developer, I want infrastructure consumer classes to inject application services directly via constructor injection, so that event flow is type-safe and does not use reflection or string-based bean lookups.

## Implementation Decisions

### Module Architecture

The implementation is split across two Gradle modules — `shared` (infrastructure building blocks) and `mooc` (bounded context with the first consumer).

**New modules (shared):**

- **KafkaEventBus** — implements `EventBus`. Receives `List<DomainEvent>`, serializes each event to the JSON envelope via `DomainEventKafkaSerializer`, publishes to the Kafka topic matching the event's `eventName()`. On `KafkaException` after producer retries are exhausted, falls back to `MySqlEventBus` for dead-letter storage.

- **DomainEventKafkaSerializer** — converts a `DomainEvent` to a byte array in the JSON envelope format: `{"data":{"id":"...","type":"course.created","occurred_on":"...","attributes":{...}},"meta":{}}`. Uses Jackson ObjectMapper with an infrastructure-level mix-in. No reflection, no domain layer dependencies.

- **DomainEventKafkaDeserializer** — converts a byte array back to a concrete `DomainEvent` subclass. Unwraps the JSON envelope, resolves the concrete class via the `type` field using a Jackson mix-in, deserializes directly without reflection or `fromPrimitives()`.

- **KafkaEventBusConfiguration** — Spring `@Configuration` that declares: Kafka `ProducerFactory`/`KafkaTemplate`, consumer `ConsumerFactory` with the custom deserializer, `NewTopic` beans for each event type (one topic per event type, replicated and partitioned appropriately), and the Jackson `ObjectMapper` bean with the mix-in registered.

- **DomainEventMixIn** — infrastructure-level Jackson mix-in abstract class (not on `DomainEvent` itself) annotated with `@JsonTypeInfo(use = Id.NAME, property = "type")` and `@JsonSubTypes` listing known domain events. This file is the single registry of event-type-to-class mappings and must be updated when new domain events are added.

**New modules (mooc):**

- **CourseCreatedKafkaConsumer** — `@Service` + `@KafkaListener(topics = "course.created")`. Receives `CourseCreatedDomainEvent`, calls `IncrementCoursesCounterOnCourseCreated.on()`. Serves as the pattern for all future domain event consumers.

**Modified modules (shared):**

- **DomainEvent** — removes four abstract methods: `toPrimitives()`, `toJson()`, `fromPrimitives()`. Removes `implements Serializable`. Retains: `aggregateId`, `eventId`, `occurredOn`, `eventName()`.

**Modified modules (mooc):**

- **CourseCreatedDomainEvent** — removes no-arg constructor, `toPrimitives()`, `toJson()`, `fromPrimitives()`, `equals()`, `hashCode()`. Becomes a simple final class with one constructor and accessors only.
- **IncrementCoursesCounterOnCourseCreated** — removes `@DomainEventSubscriber` and `@EventListener` annotations. Retains `@Service` and the `on()` method.

**Deleted modules (shared):**

- All 7 RabbitMQ infrastructure files
- `DomainEventJsonSerializer`, `DomainEventJsonDeserializer`
- `DomainEventsInformation`, `DomainEventSubscribersInformation`, `DomainEventSubscriberInformation`
- `DomainEventSubscriber` annotation
- `NamingUtil`

**Retained unchanged:**

- `EventBus` interface — no changes to its `void publish(List<DomainEvent>)` contract
- `AggregateRoot` — no changes to `record()` or `pullDomainEvents()`
- `MySqlEventBus` — kept as dead-letter store, invoked by `KafkaEventBus` on persistent Kafka failure
- `SpringApplicationEventBus` — kept for optional in-process event handling
- All application services (`CourseCreator`, `CoursesCounterIncrementer`, etc.) — no changes
- `CommandHandlersInformation`, `QueryHandlersInformation` — still use `org.reflections`

### Build Dependencies

- **Add**: `org.springframework.kafka:spring-kafka`
- **Remove**: `org.springframework.boot:spring-boot-starter-amqp`, `com.google.guava:guava:33.0.0-jre`
- **Keep**: `org.reflections:reflections:0.10.2` (still used by CQRS infrastructure)
- **Remove test deps**: `spring-rabbit-test`, `org.testcontainers:rabbitmq`
- **Add test deps**: `org.testcontainers:kafka` (for integration tests)

### Topic Strategy

- One Kafka topic per domain event type
- Topic name equals the event name returned by `eventName()` (e.g., `"course.created"`)
- Topics are created programmatically via `NewTopic` beans in `KafkaEventBusConfiguration`
- Replication factor and partition count are configurable per topic

### Envelope Format

```
{
  "data": {
    "id": "uuid-of-event",
    "type": "course.created",
    "occurred_on": "2026-05-27T12:00:00",
    "attributes": {
      "id": "aggregate-id",
      ...event-specific fields...
    }
  },
  "meta": {}
}
```

The `type` field enables future consumers that subscribe to multiple topics or replay historical events. The envelope is constructed and consumed entirely in the infrastructure layer — domain classes never see it.

## Testing Decisions

### Testing Philosophy

Tests should verify external behavior (events are published, events are received, dead-letter fallback works) without probing internal implementation details. Avoid testing serialization format strings directly — test round-trip: serialize then deserialize produces an equal event.

### Modules to Test

| Module | Test Type | What to Verify |
|---|---|---|
| `KafkaEventBus` | Unit | Publishes events via KafkaTemplate; falls back to MySqlEventBus on KafkaException |
| `DomainEventKafkaSerializer` | Unit | Produces valid JSON envelope; all fields present |
| `DomainEventKafkaDeserializer` | Unit | Round-trip: serialize → deserialize produces equal event; unknown type throws |
| `KafkaEventBus` + consumers | Integration | End-to-end: publish via EventBus → consumer receives via @KafkaListener, using embedded Kafka (Testcontainers) |
| `CourseCreatedKafkaConsumer` | Unit | Calls `IncrementCoursesCounterOnCourseCreated.on()` with deserialized event |

### Prior Art

- `MysqlEventBusTest` in `src/mooc/test/` shows the existing integration test pattern (Spring Boot test, autowire the bus, publish and verify)
- `CoursesCounterModuleUnitTestCase` and `CoursesModuleUnitTestCase` show the unit test base class pattern with mocked collaborators
- Object mothers (`CourseCreatedDomainEventMother`) should be retained for test data generation

## Out of Scope

- Migration of the CQRS command and query buses to Kafka or removal of `org.reflections` — those remain unchanged and are outside this PRD
- Removal of `SpringApplicationEventBus` — retained for future in-process event use cases
- Adding Kafka to the Docker Compose or local development setup — the developer must provide a Kafka broker; this PRD covers code changes only
- Schema registry or Avro/Protobuf serialization — Jackson JSON is sufficient for the current event volume and complexity
- Migration of existing RabbitMQ messages from the broker — this is a clean cutover; no message replay from RabbitMQ queues is planned
- Retrofitting tests for existing code uncovered by this migration

## Further Notes

- This is a pure infrastructure swap. No domain logic, application service, aggregate, or value object changes are required beyond removing serialization boilerplate from event classes.
- The `KafkaEventBus` is `@Primary` (replacing `RabbitMqEventBus` in that role) so all existing `EventBus` injection points resolve to it without code changes.
- New domain events follow this pattern: (1) create the domain event class with fields only, (2) add a `@JsonSubTypes.Type` entry in the mix-in class, (3) add a `NewTopic` bean in `KafkaEventBusConfiguration`, (4) create an infrastructure consumer with `@KafkaListener`.
