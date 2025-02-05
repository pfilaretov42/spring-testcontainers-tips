# spring-testcontainers-tips

Some tips on testcontainers usage with spring boot and kotlin.

# How to run

- Start Postgres in Docker using `local/docker-compose.yaml`
- Start spring boot app
- use `local/api-test.http` to test API

# How to test 

- run `gradle clean test --stacktrace`