# WinWin Test Task

Two Spring Boot services + Postgres, wired up with Docker Compose.

auth-api handles register/login (JWT) and a protected /process endpoint. data-api does the actual text transform and only talks to auth-api (checks an internal token header, nothing else). auth-api calls data-api internally and logs each request to Postgres.

## Run it

    docker compose up --build

Needs a `.env` in the root:

    POSTGRES_DB=authdb
    POSTGRES_USER=postgres
    POSTGRES_PASSWORD=postgres
    JWT_SECRET=some-long-random-string
    INTERNAL_TOKEN=some-other-secret

auth-api → localhost:8080
data-api → localhost:8081

Postgres schema is created automatically (ddl-auto=update), nothing to run manually.

## Endpoints

Register:

    curl -X POST http://localhost:8080/api/auth/register -H "Content-Type: application/json" -d '{"email":"a@a.com","password":"password123"}'

201, or 409 if email's taken, or 400 if validation fails.

Login:

    curl -X POST http://localhost:8080/api/auth/login -H "Content-Type: application/json" -d '{"email":"a@a.com","password":"password123"}'

returns { "token": "..." }

Process (needs the token from login):

    curl -X POST http://localhost:8080/api/process -H "Content-Type: application/json" -H "Authorization: Bearer <token>" -d '{"text":"hello world"}'

returns { "result": "HELLO WORLD" }, also writes a row to processing_log (user_id, input, output, timestamp).

## Stack

Java 21, Spring Boot 4.1, Spring Security, JWT (jjwt), Spring Data JPA, PostgreSQL, BCrypt, Docker.

## A couple of things worth mentioning

- data-api is intentionally dumb — it doesn't know what a user is, just checks X-Internal-Token and transforms text. Keeps it decoupled from auth-api.
- Login/register errors are generic on purpose (same message whether the email doesn't exist or the password's wrong) so you can't enumerate emails.
- No tests yet — next thing on my list, didn't want to rush it just to tick a box

Each service also runs fine on its own without Docker (mvn spring-boot:run), application.properties has local defaults for that.
