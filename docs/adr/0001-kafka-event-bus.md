# 0001: Migrate domain event bus from RabbitMQ to Kafka

The project's domain event infrastructure used RabbitMQ with reflection-based subscriber discovery (org.reflections scanning) and reflection-based deserialization (DomainEventJsonDeserializer invoking fromPrimitives via reflection on null no-arg instances). This added unnecessary complexity: no-arg constructors on every event class, a manual retry/dead-letter topology with 3 exchanges and 3 queues per subscriber, and a Guava dependency solely for queue-name formatting.

We decided to replace RabbitMQ with Kafka, keeping the domain layer infrastructure-free:

- Explicit `@KafkaListener` consumers in the infrastructure layer — one per event type, one Kafka topic per event type
- Jackson mix-in serialization at the infrastructure level (no Jackson annotations on domain classes)
- Removal of reflection-based subscriber discovery and deserialization for domain events
- `MySqlEventBus` retained as a dead-letter store for events that fail Kafka delivery
- `SpringApplicationEventBus` retained for in-process event handling
- The `EventBus` domain interface unchanged; only the implementation and consumer patterns change
- Guava dependency removed (NamingUtil eliminated with RabbitMQ code)

## Status

Accepted

## Considered Options

- **RabbitMQ (status quo)** — rejected: overengineered for current needs, reflection-based machinery adds complexity without benefit
- **Kafka with shared topic** — rejected: one "domain_events" topic requires a type discriminator and dispatch logic, adding back the complexity we're removing
- **Kafka with per-type topics** — chosen: topic name matches event name, consumers declare their type in the method signature, no dispatch logic needed

## Consequences

- Event type to topic mapping is 1:1 — topic name equals event name (e.g., `"course.created"`)
- New domain events require: (1) a new topic declaration, (2) a new infrastructure consumer with `@KafkaListener`
- `org.reflections` dependency retained — still used by CommandHandlersInformation and QueryHandlersInformation (CQRS)
- Spring AMQP dependency and all RabbitMQ test containers removed
- Contributors need a Kafka broker instead of RabbitMQ for local development
