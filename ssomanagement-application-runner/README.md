# build and test notes

prepare schema, start ssoserver
```bash
# create app
mvn spring-boot:run -pl ssomanagement-application-runner -am \
    -Dspring-boot.run.arguments="--clientId=x1 --clientSecret=x2 --redirectUri=y1 --redirectUri=y2 --scope=z1 --scope=z2"

# create app role for user
mvn spring-boot:run -pl ssomanagement-application-runner -am \
    -Dspring-boot.run.arguments="--clientId=x1 --username=y1 --userRole=z1 --userRole=z2"
```