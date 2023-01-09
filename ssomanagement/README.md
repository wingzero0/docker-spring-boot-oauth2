# build and test notes

start ssoserver, role-server.

start ssomanagement
```bash
cp ssomanagement/src/main/filters-example.properties ssomanagement/src/main/filters-dev.properties
mvn spring-boot:run -pl ssomanagement -am
```

visit ```http://127.0.0.1:8080/swagger-ui/index.html```