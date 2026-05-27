# MOOC (Massive Open Online Courses)

A bounded context that manages courses offered on the learning platform. Handles course creation and tracks the total number of courses that have been created.

## Language

**Course**: A learning unit offered on the platform. Has a unique identifier, a name, and a duration.
_Avoid_: Class, module, lesson

**CourseId**: Unique identifier for a Course. Encapsulates a UUID string.

**CourseName**: The title or name of a Course.
_Avoid_: Title, label

**CourseDuration**: The time commitment or length of a Course.
_Avoid_: Length, time

**CoursesCounter**: Tracks the total number of distinct Courses that have been created on the platform. Idempotent — a Course is counted only once, even if its creation event is delivered multiple times.

**CoursesCounterId**: Unique identifier for the CoursesCounter aggregate. A singleton — there is exactly one counter for the platform.

**CoursesCounterTotal**: The current count of courses. Incremented when a new Course is created.

**CourseCreated**: A domain event emitted when a new Course is instantiated. Carries the Course's name and duration.

## Relationships

- A **Course** is identified by a **CourseId** and has a **CourseName** and **CourseDuration**.
- **CoursesCounter** contains one **CoursesCounterTotal** and a list of **CourseId** values it has already seen (for idempotency).
- **CourseCreated** events are consumed to increment the **CoursesCounter**.

## Example dialogue

> **Dev:** When is the CoursesCounter incremented — at the moment the Course aggregate is instantiated, or when it's persisted?
> **Domain expert:** When a Course is created — meaning when the aggregate is built and the creation event is recorded. The counter is eventually consistent; if the same event arrives twice, the counter does not double-count.

## Flagged ambiguities

- "course counter" was used interchangeably to mean the aggregate and the numeric value — resolved: **CoursesCounter** is the aggregate, **CoursesCounterTotal** is the value.
