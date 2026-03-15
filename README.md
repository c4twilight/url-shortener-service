# Spring Boot Production Showcase

A production-style Spring Boot project that demonstrates three core areas:
- REST API design and testing (COVID stats module)
- DSA implementation in a service API (tree preorder/subtree query module)
- OOP design patterns (Observer pattern social platform module)

It also keeps your existing URL shortener module so the project showcases multiple practical backend patterns in one repo.

## Tech Stack
- Java 17
- Spring Boot 3.4
- Spring Web, Spring Data JPA, Validation, Actuator
- H2 (default local) and PostgreSQL profile support
- JUnit 5 + MockMvc
- Docker multi-stage build
- GitHub Actions CI

## Modules

### 1) URL Shortener
- `POST /s`
- `GET /s/{shortUrl}`
- `GET /s/user/{userId}`

### 2) COVID Stats (from your Module 1 problem)
- `GET /covid`
- `POST /covid`
- `GET /covid/{id}`
- `GET /covid/top5?by=active|death|recovered`
- `GET /covid/total?by=active|death|recovered`

Rules implemented:
- Invalid `by` -> `400 Bad Request`
- Missing covid id -> `404 Not Found`

### 3) Tree Query Solver (from your Module 2 problem)
- `POST /algorithms/tree-queries/solve`

Sample request:
```json
{
  "par": [-1, 1, 1, 2, 2, 3],
  "query": [[1, 1], [2, 3], [3, 2], [5, 2]]
}
```

Sample response:
```json
{
  "answers": [1, 5, 6, -1]
}
```

### 4) Social Observer Platform (from your Module 3 problem)
- `POST /social/users`
- `POST /social/follow`
- `POST /social/unfollow`
- `POST /social/posts`

The post flow returns event lines in the exact style of the HackerRank output format.

## Production-Readiness Features
- Structured global error responses (`timestamp`, `status`, `error`, `message`, `path`)
- Input validation on request payloads
- Deterministic integration tests for all three modules
- Actuator health/info endpoints
- OpenAPI UI via springdoc: `/swagger-ui/index.html`
- Dockerized build/runtime image
- CI pipeline (`.github/workflows/ci.yml`) with test + coverage artifact upload

## Run Locally
```bash
./mvnw spring-boot:run
```

## Run Tests
```bash
./mvnw clean test
```

## Run with Production Profile
```bash
SPRING_PROFILES_ACTIVE=prod ./mvnw spring-boot:run
```

Set datasource env vars for production profile:
- `SPRING_DATASOURCE_URL`
- `SPRING_DATASOURCE_USERNAME`
- `SPRING_DATASOURCE_PASSWORD`

## Docker
```bash
docker build -t springboot-showcase:local .
docker run --rm -p 8080:8080 springboot-showcase:local
```

## Health
- `GET /actuator/health`
- `GET /actuator/info`
