# URL Shortener Service

Spring Boot backend service to create and resolve short URLs.

## Tech Stack
- Java 17
- Spring Boot 3
- Spring Web, Spring Data JPA, Validation, Actuator
- H2 (default)

## Architecture
- `controller`: REST endpoints
- `service`: business logic and short-code generation
- `repository`: persistence
- `dto`: API payloads
- `exception`: global exception handling

Learning comments in source files are intentionally preserved.

## API
- `POST /s` create short URL
- `GET /s/{shortUrl}` resolve original URL
- `GET /s/user/{userId}` list URLs for a user

## Short Code Strategy
- The project currently keeps a fixed-length Base62 output (8 chars) padded with `=`.
- This behavior is preserved intentionally for learning continuity.
- You can switch to non-padded Base62 later if you want cleaner URL aesthetics.

Example request:

```json
{
  "userId": 101,
  "oldUrl": "https://example.com/article/abc"
}
```

## Run Locally
```bash
./mvnw spring-boot:run
```

## Run Tests
```bash
./mvnw test
```

## Docker
Build and run:

```bash
docker build -t myapplication:local .
docker run --rm -p 8080:8080 myapplication:local
```

## Health Check
- `GET /actuator/health`
