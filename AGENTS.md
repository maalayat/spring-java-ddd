# AGENTS.md

This guide helps agentic coding agents work effectively in this Spring Java DDD project.

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

## Code Style Guidelines

### Imports
- Package declaration first
- Static imports at the top (before other imports)
- Project imports grouped first, then standard java imports
- No blank lines within import groups

### Formatting
- 2-space indentation
- Opening braces on same line
- Blank line between method definitions
- Private final fields at class level
- Constructor injection pattern only
- `final` keyword for immutable domain classes
- Empty line after opening brace for complex initialization blocks

### Naming Conventions
- **Classes**: PascalCase
- **Methods**: camelCase
- **Value Objects**: Type + descriptor (CourseName, CourseDuration, CourseId)
- **Aggregates**: Entity name (Course, CoursesCounter)
- **Domain Events**: `{Entity}{Action}DomainEvent` (CourseCreatedDomainEvent)
- **Commands**: `{Action}{Entity}Command` (CourseCreateCommand)
- **Queries**: `{Action}{Entity}Query` (FindCoursesCounterQuery)
- **Handlers**: `{Command/Query}Handler` (CourseCreateCommandHandler)
- **Use Cases**: `{Entity}{Action}or` (CourseCreator, CoursesCounterIncrementer)
- **Repositories**: Interface (CourseRepository), Implementation (MySqlCourseRepository)
- **Mappers**: `{Type}Mapper` (CourseMapper)
- **Entities**: `{Type}Entity` (CourseEntity, CourseIdEntity)
- **Test Mothers**: `{Type}Mother` (CourseMother, CourseCreateCommandMother)

### Types
- All domain classes are `final` and immutable where possible
- Value Objects extend `StringValueObject` or `IntValueObject` from shared module
- Aggregates extend `AggregateRoot` and record domain events via `record()` method
- Domain Events extend `DomainEvent` and implement `Serializable`
- Application Services use `@Service` from shared module (NOT Spring's)
- Repository implementations use `@Primary` annotation and composition pattern

### DDD Patterns

**Value Objects:**
```java
public final class CourseName extends StringValueObject {
  public CourseName(String value) { super(value); }
}
```

**Aggregates:**
```java
public final class Course extends AggregateRoot {
  private final CourseId id;
  private final CourseName name;
  private final CourseDuration duration;

  public Course(CourseId id, CourseName name, CourseDuration duration) {
    this.id = id;
    this.name = name;
    this.duration = duration;
    this.record(new CourseCreatedDomainEvent(id.value(), name.value(), duration.value()));
  }

  // Provide both value() and getValue() methods for compatibility
}
```

**Application Services:**
- Use `@Service` annotation from shared module
- Constructor injection with `private final` fields
- Publish domain events via `eventBus.publish(aggregate.pullDomainEvents())`

**Infrastructure Layer:**
- JPA Entities: Use `@Entity`, `@EmbeddedId`, `@Embedded` for value objects
- Mappers: MapStruct with `@Mapper(componentModel = "spring")`
- Repository Implementation: Use `@Primary` and composition with Spring Data JPA

### Testing Patterns
- Unit tests extend module-specific base classes (e.g., `CoursesModuleUnitTestCase`)
- Infrastructure tests extend `InfrastructureTestCase` which uses `@SpringBootTest`
- Use object mothers for test data (e.g., `CourseMother`, `CourseCreateCommandMother`)
- Use Datafaker library for random test data generation
- Test methods use `@DisplayName` with Given-When-Then format
- Verify domain events were published using `shouldHavePublished()`

### Error Handling
- Domain-specific exceptions extend from appropriate base classes
- Use Optional for repository search methods that may not find results

### Module Structure
```
src/{context}/main/ec/solmedia/{context}/
├── {aggregate}/
│   ├── application/          # Use cases, command handlers, query handlers
│   ├── domain/              # Entities, value objects, repositories, domain events
│   └── infrastructure/      # JPA entities, repositories, mappers, controllers
```

### Key Configuration
- **Component Scanning**: Custom `@Service` annotation filter in `Starter.java`
- **Entity Scanning**: `@EntityScan` and `@EnableJpaRepositories` with basePackageClasses
- **Database**: Auto-created schema via `spring.jpa.hibernate.ddl-auto=create-drop`
- **Virtual Threads**: Enabled with `spring.threads.virtual.enabled=true`
- **Java Toolchain**: Java 24

## Agent skills

### Issue tracker

GitHub Issues. See `docs/agents/issue-tracker.md`.

### Triage labels

Uses the five canonical labels: `needs-triage`, `needs-info`, `ready-for-agent`, `ready-for-human`, `wontfix`. See `docs/agents/triage-labels.md`.

### Domain docs

Single-context — one `CONTEXT.md` at repo root. See `docs/agents/domain.md`.
