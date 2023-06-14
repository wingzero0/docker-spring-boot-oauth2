# build and test notes

prepare schema, start ssoserver
```bash
mvn spring-boot:run -pl ssomanagement-application-runner -am -Dspring-boot.run.arguments="--clientId=x1 --clientSecret=x2 --redirectUri=y1 --redirectUri=y2 --scope=z1 --scope=z2"
```