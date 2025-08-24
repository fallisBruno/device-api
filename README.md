## How to run Device API locally:

Make sure you have docker installed, you can follow the official install guide https://docs.docker.com/engine/install/

Clone the project in your local, open a terminal and run:

```cd device-api```

Inside the folder you just need to run:

```docker compose up```

The above command will run a postgres DB and the Device Spring Boot API, which is in Java 21.

### Improvements:
 - Add pagination for devices/all route.
 - Implement a patch rest resource to update only one attribute.
 - Create integration tests to test Controller > Service > Repository layers all together.