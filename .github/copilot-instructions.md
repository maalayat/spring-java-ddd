# Copilot Instructions for Spring Java DDD

## Architecture Overview

This is a **Domain-Driven Design (DDD)** project implementing a multi-module Spring Boot application with bounded contexts as separate Gradle modules:

- **`shared`**: Common domain building blocks (Value Objects, Aggregate Root, Domain Events, Service annotation)
- **`mooc`**: MOOC bounded context (courses and course counter aggregates)
- **`backoffice`**: Backoffice bounded context
- **`app`**: Application entry point that wires all modules together

The project follows **hexagonal architecture** with clear separation between domain, application, and infrastructure layers.

## Key DDD Building Blocks

### Domain Layer Patterns
- **Value Objects**: Extend `StringValueObject` or `IntValueObject` from shared module (e.g., `CourseName`, `CourseDuration`)
- **Aggregates**: Extend `AggregateRoot` and record domain events using `record()` method
- **Domain Events**: Extend `DomainEvent`, implement serialization methods, and follow naming pattern `*DomainEvent`
- **Repositories**: Define interfaces in domain, implement in infrastructure layer

### Example Value Object:
```java
public final class CourseName extends StringValueObject {
  public CourseName(String value) { super(value); }
}
```

### Example Aggregate:
```java
public final class Course extends AggregateRoot {
  // Constructor records domain event
  public Course(CourseId id, CourseName name, CourseDuration duration) {
    this.record(new CourseCreatedDomainEvent(id.value(), name.value(), duration.value()));
  }
}
```

## Module Structure

Each bounded context follows this structure:
```
src/{context}/main/ec/solmedia/{context}/
├── {aggregate}/
│   ├── application/          # Use cases, command handlers, query handlers
│   ├── domain/              # Entities, value objects, repositories, domain events
│   └── infrastructure/      # JPA entities, repositories, mappers, controllers
```

## Critical Development Patterns

### 1. Application Services
- Use `@Service` annotation from shared module (not Spring's)
- Inject domain repositories and EventBus
- Handle commands/queries and publish domain events

### 2. Infrastructure Layer
- **JPA Entities**: Use `@Entity`, `@EmbeddedId`, `@Embedded` for value objects
- **Mappers**: Use MapStruct with `@Mapper(componentModel = "spring")` for domain ↔ entity conversion
- **Repository Implementation**: Use `@Primary` and composition pattern with Spring Data JPA

### 3. Domain Events
- Events are recorded in aggregates and published via `EventBus.publish(aggregate.pullDomainEvents())`
- Events follow naming: `{Entity}{Action}DomainEvent` (e.g., `CourseCreatedDomainEvent`)

### 4. Testing Patterns
- Unit tests extend module-specific test cases (e.g., `CoursesModuleUnitTestCase`)
- Infrastructure tests extend `InfrastructureTestCase` which uses `@SpringBootTest`
- Use object mothers for test data generation (e.g., `CourseMother`, `CourseCreateCommandMother`)

## Build & Development Workflow

### Essential Commands
- **Build**: `./gradlew build` or `make build`
- **Test**: `./gradlew test` or `make test` 
- **Run**: `./gradlew :run` or `make run`

### Database Setup
1. Start MySQL: `docker-compose up mysql` or manual Docker command from README
2. Configure connection in `app/main/resources/application.properties`
3. Database schema is auto-created via `spring.jpa.hibernate.ddl-auto=create-drop`

### Key Configuration
- **Main class**: `ec.solmedia.app.Starter` (scans all modules with `@ComponentScan`)
- **Entity scanning**: Uses `@EntityScan` and `@EnableJpaRepositories` with basePackageClasses
- **Service discovery**: Uses custom `@Service` annotation filter in component scanning

## Integration Points

- **Event Bus**: Centralized domain event publishing and handling
- **MapStruct**: Automatic mapping generation between domain and infrastructure
- **Cross-Module Dependencies**: Shared module provides common abstractions
- **Database**: Single MySQL instance with multiple schemas (mooc, backoffice)

## Important Conventions

- All domain classes are `final` and immutable where possible
- Value objects provide both `value()` and `getValue()` methods for compatibility
- Repository interfaces live in domain, implementations in infrastructure
- Domain events include JSON serialization for persistence
- Use `@Primary` on production repository implementations when multiple exist (e.g., MySQL vs InMemory)
