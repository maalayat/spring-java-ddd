# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Essential Commands

- **Build**: `./gradlew build` or `make build`
- **Test**: `./gradlew test` or `make test` (requires MySQL instance running)
- **Run**: `./gradlew :run` or `make run`
- **Single Test**: `./gradlew test --tests "ClassName.methodName"`
- **View Module Paths**: `./gradlew view_paths`

## Database Setup

Tests and application require MySQL. Use Docker:
```bash
docker run --name mysql-ddd -p3306:3306 -e MYSQL_ROOT_PASSWORD=yourpassword -d mysql:8.0.31
```
Or use docker-compose: `docker-compose up mysql`

Update database configuration in `app/main/resources/application.properties`.

## Architecture Overview

This is a **Domain-Driven Design (DDD)** project with **hexagonal architecture** implementing a multi-module Spring Boot application:

- **`shared`**: Common domain building blocks (Value Objects, Aggregate Root, Domain Events, Service annotation)
- **`mooc`**: MOOC bounded context (courses and course counter aggregates)  
- **`backoffice`**: Backoffice bounded context
- **`app`**: Application entry point that wires all modules together (main class: `ec.solmedia.app.Starter`)

## Module Structure

Each bounded context follows this layered structure:
```
src/{context}/main/ec/solmedia/{context}/
├── {aggregate}/
│   ├── application/          # Use cases, command handlers, query handlers
│   ├── domain/              # Entities, value objects, repositories, domain events
│   └── infrastructure/      # JPA entities, repositories, mappers, controllers
```

## Key DDD Patterns

### Value Objects
Extend `StringValueObject` or `IntValueObject` from shared module:
```java
public final class CourseName extends StringValueObject {
  public CourseName(String value) { super(value); }
}
```

### Aggregates
Extend `AggregateRoot` and record domain events using `record()` method:
```java
public final class Course extends AggregateRoot {
  public Course(CourseId id, CourseName name, CourseDuration duration) {
    // Constructor records domain event
    this.record(new CourseCreatedDomainEvent(id.value(), name.value(), duration.value()));
  }
}
```

### Application Services
- Use `@Service` annotation from shared module (not Spring's)
- Follow constructor injection pattern
- Publish domain events via `EventBus.publish(aggregate.pullDomainEvents())`

### Infrastructure Layer
- **JPA Entities**: Use `@Entity`, `@EmbeddedId`, `@Embedded` for value objects
- **Mappers**: Use MapStruct with `@Mapper(componentModel = "spring")` for domain ↔ entity conversion
- **Repository Implementation**: Use `@Primary` annotation and composition pattern with Spring Data JPA

## Key Configuration Details

- **Component Scanning**: Uses custom `@Service` annotation filter in `Starter.java`
- **Entity Scanning**: Configured with `@EntityScan` and `@EnableJpaRepositories` using basePackageClasses
- **Database**: Single MySQL instance with auto-created schema via `spring.jpa.hibernate.ddl-auto=create-drop`
- **Virtual Threads**: Enabled with `spring.threads.virtual.enabled=true`

## Testing Patterns

- Unit tests extend module-specific test cases (e.g., `CoursesModuleUnitTestCase`)
- Infrastructure tests extend `InfrastructureTestCase` which uses `@SpringBootTest`
- Use object mothers for test data generation (e.g., `CourseMother`, `CourseCreateCommandMother`)

## Development Conventions

- All domain classes are `final` and immutable where possible
- Value objects provide both `value()` and `getValue()` methods for compatibility
- Repository interfaces live in domain, implementations in infrastructure
- Domain events include JSON serialization for persistence and follow naming pattern `*DomainEvent`
- Use constructor injection with `private final` fields
- Events are recorded in aggregates and published via EventBus