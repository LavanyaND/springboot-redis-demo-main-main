# Spring Project with Redis
- To run Redis Container
```bash
docker run -d -p 6379:6379 redis
```

- To run the project use 
```bash
mvn spring-boot:run
```
## If Running both in the Docker container
- application.properties (Spring Boot Redis config)
- Make sure your application.properties points to the Redis container:
```bash
spring.redis.host=${SPRING_REDIS_HOST:localhost}
spring.redis.port=${SPRING_REDIS_PORT:6379}
spring.redis.timeout=6000
```
## 4. How to Run
- Build your Spring Boot jar:
```bash
mvn clean package
```
- Start the services:
```bash
docker-compose up --build
```
- Visit your app: http://localhost:8084
- Redis is accessible inside Docker network at host redis:6379.
---
*Done by Sak_shetty*