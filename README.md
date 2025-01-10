# Lobby service

Lobby service is responsible for managing lobby creation, listing and deletion.
In addition it also keeps some simple stats about the lobbies using the CQRS pattern from the game service.
This is done by hosting a gRPC server that listens for events from the game service. 

Other notes:
- ORM of choice is Hibernate
- `rso-comms` is a git sub module that contains the proto files for the gRPC communication between the services
- The gradle build script copies the relevant proto files from the `rso-comms` module to the `src/main/proto` directory
- You need to host a local postgresql instance using docker or connect to a remote one
- Builds are done using Google jib plugin
- It is recommended to use Teleport or kubernetes port forwarding to connect to the other services and/ or databases

## Development

This server is a Spring Boot application. You need to have the following installed:

- Java 21
- Gradle >7
- Intellij IDEA
- Docker with docker compose

Create an `appplication-local.yaml` file with the following content:

```yaml
svc:
  game-app:
    admin-url: 
    grpc-svc-template: 
```

In the `applicaion.yaml` lookup other common properties, such as the server port, Swagger configuration, etc.