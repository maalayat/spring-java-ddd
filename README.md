# Spring Boot DDD Project ☕

This project is a practical implementation of Domain-Driven Design (DDD) principles using Spring
Boot. It serves as a companion to a DDD course, demonstrating how to build a modular, scalable, and
maintainable application.

## ✨ Features

- **Modular Architecture**: The project is divided into `backoffice`, `mooc`, and `shared` modules,
  each with a specific responsibility.
- **Domain-Driven Design**: The codebase is structured around the principles of DDD, with a clear
  separation of layers (domain, application, infrastructure).
- **Spring Boot**: The application is built on top of the Spring Boot framework, providing a robust
  and efficient development experience.
- **Docker Compose**: The project uses Docker Compose to manage the required services (MySQL and
  RabbitMQ), making it easy to set up and run.

## 🚀 Stack

- **Java 24**
- **Spring Boot**
- **Gradle**
- **MySQL**
- **RabbitMQ**
- **Docker**

## ✅ Prerequisites

- [Java 24](https://www.oracle.com/java/technologies/downloads/)
- [Gradle](https://gradle.org/install/)
- [Docker](https://docs.docker.com/get-docker/)

## ▶️ Getting Started

1. **Clone the repository**:

   ```bash
   git clone https://github.com/maalayat/spring-java-ddd.git
   cd spring-java-ddd
   ```

2. **Start the services**:

   ```bash
   docker compose up -d
   ```

3. **Build the project**:

   ```bash
   make build
   ```

## 📁 Project Structure

The project is organized into the following modules:

- `app`: The main application module, responsible for starting the Spring Boot application.
- `backoffice`: Contains the backoffice-related domain logic and infrastructure.
- `mooc`: Contains the Massive Open Online Course (MOOC) related domain logic and infrastructure.
- `shared`: Contains the shared kernel logic, including the domain and infrastructure layers.

## 🏃‍♀️ Run the application

To run the application, you can use the following command:

```bash
make run
```

## ✅ Testing

To run the tests, you can use the following command:

```bash
make test
```

## 🤝 Contributing

Contributions, issues, and feature requests are welcome! Feel free to check
the [issues page](https://github.com/alejo-es/spring-java-ddd/issues).

## ⭐️ Show your support

Give a ⭐️ if this project helped you!

## 📝 License

This project is [MIT](./LICENSE) licensed.
