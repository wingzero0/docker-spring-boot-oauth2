# docker-spring-boot-oauth2

SSO demo built with Spring Boot 3.5.7 and Spring Authorization Server 1.5.7 (OAuth2 / OpenID Connect).
Requires **Java 17+**, **Maven 3.9+**, and **jq** (for the token commands below).

| module | description | url |
| --- | --- | --- |
| `ssoserver` | authorization server (login, consent, token, introspect, revoke, OIDC) | http://localhost:8081/auth (in-memory: http://localhost:8087/auth) |
| `role-server` | resource server, validates the token by introspection and returns user roles | http://localhost:8082/res |
| `ssoclient` | server side login demo (authorization_code + OIDC) | http://127.0.0.1:8080 |
| `ssomanagement` | management UI / API (Vue frontend, springdoc) | http://localhost:8080 |
| `ssomanagement-application-runner` | data maintenance CLI (not a web app) | - |
| `ssoentity` | shared JPA entities and repositories | - |

Two ways to run it: **in-memory (no database)** for a quick look, or **MariaDB** for a real database.

## Quick start A: in-memory, no database

Everything runs on H2 in memory, seeded on startup. Nothing to install besides Java and Maven.

```bash
# terminal 1 - authorization server, H2, port 8087
mvn spring-boot:run -pl ssoserver -am -Pinmemory

# terminal 2 - resource server, H2, port 8082
mvn spring-boot:run -pl role-server -am -Pinmemory
```

Check that both are up (the resource server endpoint is public):

```bash
curl -s http://localhost:8082/res/api/version
# {"version":"0.13.0-SNAPSHOT-localBuild","profile":"inmemory"}
```

Get an access token with the `client_credentials` grant (the in-memory profile registers the
client `messaging-client2` / `secret` automatically):

```bash
TOKEN=$(curl -s -u messaging-client2:secret \
  -d 'grant_type=client_credentials&scope=app_role' \
  http://localhost:8087/auth/oauth2/token | jq -r '.access_token')

# roles of every user of this client
curl -s -H "Authorization: Bearer $TOKEN" http://localhost:8082/res/api/appRoles
# ["ADMIN"]

# roles of one user of this client
curl -s -H "Authorization: Bearer $TOKEN" http://localhost:8082/res/api/appRole/john
# ["ADMIN"]
```

A request without a token returns `401`; a token without the `app_role` scope returns `403`.

Introspect the token (this is what the resource server does internally):

```bash
curl -s -u messaging-client2:secret \
  -d "token=$TOKEN&token_type_hint=access_token" \
  http://localhost:8087/auth/oauth2/introspect
# {"active":true,"sub":"messaging-client2",...}
```

Optional browser login (authorization_code + consent page + OIDC):

```bash
# terminal 3 - client application, must be reached through 127.0.0.1 (registered redirect URI)
mvn spring-boot:run -pl ssoclient -am -Pinmemory
```

Open http://127.0.0.1:8080/loginPage and log in with **john / 456**.

What the in-memory profiles do:

- `ssoserver`: H2 (`jdbc:h2:mem:ssoserver`), creates the Spring Authorization Server tables plus the
  `app_user` table from [ssoserver/src/main/resources/db/inmemory/schema.sql](ssoserver/src/main/resources/db/inmemory/schema.sql),
  and bootstraps the `messaging-client2` client (`application.init.oauth2registeredclient=true`).
- `role-server`: H2 (`jdbc:h2:mem:roleserver`), seeds `app_user_role` from
  [role-server/src/main/resources/db/inmemory/data.sql](role-server/src/main/resources/db/inmemory/data.sql)
  and introspects against port `8087`.
- `ssoclient`: points its issuer to `http://localhost:8087/auth`.

All data disappears when the processes stop.

## Quick start B: MariaDB, a real database

### 1. Start MariaDB

The default `application.properties` of `ssoserver` and `role-server` already point to
`jdbc:mariadb://localhost:3306/authorization_server` with user/password `authorization_server`,
so the container below matches the configuration. Their URLs also carry
`preserveInstants=true&connectionTimeZone=UTC&forceConnectionTimeZoneToSession=true`, so no manual edit is
needed - see [Time zone contract](#time-zone-contract) for why.

```bash
docker run -d --name sso-mariadb -p 3306:3306 \
  -e MARIADB_ROOT_PASSWORD=root \
  -e MARIADB_DATABASE=authorization_server \
  -e MARIADB_USER=authorization_server \
  -e MARIADB_PASSWORD=authorization_server \
  mariadb:11

# wait until the database accepts connections
until docker exec sso-mariadb mariadb-admin ping -uroot -proot --silent; do sleep 2; done
```

### 2. Create the OAuth2 tables

```bash
docker exec -i sso-mariadb mariadb -uroot -proot authorization_server < mariadb/1.5.7-schema.sql
```

[mariadb/1.5.7-schema.sql](mariadb/1.5.7-schema.sql) is the upstream Spring Authorization Server 1.5.7
schema (the three `oauth2-*-schema.sql` files of the
`spring-security-oauth2-authorization-server` jar, concatenated as is). It only creates the three Spring
Authorization Server tables, because they are not mapped by JPA. The
`app_user` / `app_user_role` / `app_user_seq` tables are created by Hibernate when the applications start
(dev profile, `spring.jpa.hibernate.ddl-auto=update`).

### 3. Start the applications

```bash
# terminal 1 - authorization server, port 8081, context path /auth
mvn spring-boot:run -pl ssoserver -am

# terminal 2 - resource server, port 8082, context path /res
mvn spring-boot:run -pl role-server -am
```

The `dev` profile (active by default) of `ssoserver` also registers the `messaging-client2` client and
creates the `app_user` tables on startup.

### 4. Seed demo user and role

Run this once, after the tables exist:

```bash
docker exec -i sso-mariadb mariadb -uroot -proot authorization_server < mariadb/seed-app-data.sql
```

### 5. Verify

Same commands as Quick start A, but the authorization server is on `8081`:

```bash
TOKEN=$(curl -s -u messaging-client2:secret \
  -d 'grant_type=client_credentials&scope=app_role' \
  http://localhost:8081/auth/oauth2/token | jq -r '.access_token')

curl -s -H "Authorization: Bearer $TOKEN" http://localhost:8082/res/api/appRoles
curl -s -H "Authorization: Bearer $TOKEN" http://localhost:8082/res/api/appRole/john
```

Browser login with `ssoclient` (see Quick start A) works here too: log in with **john / 456**.

### 6. Clean up

```bash
docker rm -f sso-mariadb
```

To use another database server, change `spring.datasource.*` in the module `application.properties`
or override it on the command line, for example
`--spring.datasource.url=jdbc:mariadb://db-host:3306/authorization_server?preserveInstants=true&connectionTimeZone=UTC&forceConnectionTimeZoneToSession=true`.
Keep the three query options when overriding, otherwise the values written into the `timestamp` columns depend
on the JVM time zone (see [Time zone contract](#time-zone-contract)).
For PostgreSQL, use [postgresql/script/init-db.sh](postgresql/script/init-db.sh) (see the
`.devcontainer` setup) and change the driver/url to `org.postgresql.Driver` /
`jdbc:postgresql://postgresqldb:5432/authorization_server`.

## Build the war files

```bash
# frontend assets for the login page
cd ssoserver/npmLib && npm ci && npm run postbuild && cd ../../

mvn clean package                # dev profile is the default -> *-dev.war
mvn clean package -Pinmemory     # -> *-inmemory.war

java -jar "ssoserver/target/ssoserver##0.13.0-SNAPSHOT-dev.war"
java -jar "role-server/target/role-server##0.13.0-SNAPSHOT-dev.war"
```

The file name embeds the Maven profile (`${active.profile}`), which is also the Spring profile written
into `spring.profiles.active`. Override ports or the database on the command line, for example
`--spring.profiles.active=prod --server.port=8082 --spring.datasource.url=...`.

## Endpoint reference

The client `messaging-client2` uses `client_secret_basic`, so `client_id:client_secret` is sent as
`Authorization: Basic base64(messaging-client2:secret)` (`-u` does that for you).

```bash
# client_credentials
curl -u messaging-client2:secret -d 'grant_type=client_credentials&scope=app_role' \
  http://localhost:8081/auth/oauth2/token

# refresh
curl -u messaging-client2:secret -d 'grant_type=refresh_token&refresh_token=xxx' \
  http://localhost:8081/auth/oauth2/token

# introspect - the client used here does not have to be the one that created the token
curl -u messaging-client2:secret -d 'token=xxx' http://localhost:8081/auth/oauth2/introspect

# revoke
curl -u messaging-client2:secret -d 'token=xxx&token_type_hint=access_token' \
  http://localhost:8081/auth/oauth2/revoke
curl -u messaging-client2:secret -d 'token=xxx&token_type_hint=refresh_token' \
  http://localhost:8081/auth/oauth2/revoke

# discovery document / jwks
curl http://localhost:8081/auth/.well-known/openid-configuration
curl http://localhost:8081/auth/oauth2/jwks

# resource server APIs (scope is checked, no scope -> 403)
curl -H "Authorization: Bearer xxx" http://localhost:8082/res/api/appRole/john
curl -H "Authorization: Bearer xxx" http://localhost:8082/res/api/testScopeRead
```

The consent page lives at `/oauth2/consent` (`authorizationEndpoint.consentPage(...)` in
[AuthorizationServerConfig.java](ssoserver/src/main/java/io/github/wingzero0/ssoserver/AuthorizationServerConfig.java));
the `ssoclient` module exercises the full authorization_code flow.

## Run the tests

The tests are self contained - `role-server` uses an H2 `test` profile, so no database is needed:

```bash
mvn test                  # all modules
mvn test -pl role-server  # single module
```

## Notes

- The schema of the OAuth2 tables is unchanged between Spring Authorization Server 1.2.7 and 1.5.7;
  [mariadb/1.5.7-schema.sql](mariadb/1.5.7-schema.sql) is the 1.5.7 upstream script, kept verbatim.
  Remember that it uses `blob` for the token columns - PostgreSQL needs `text` instead, and for MySQL /
  MariaDB the `preserveInstants=true&connectionTimeZone=UTC&forceConnectionTimeZoneToSession=true` JDBC
  options are required to store time instants accurately (see the header of the script); they are already
  part of every MariaDB `spring.datasource.url` in this repository, see [Time zone contract](#time-zone-contract).
- `role-server` and `ssoserver` cannot be deployed twice into the same Tomcat instance; undeploy the old
  version before deploying a new one.

## Time zone contract

The applications store **instants**, never wall-clock values, and the JDBC layer pins both sides of the
conversion to UTC. The JVM time zone is therefore irrelevant for what ends up in the database: a JVM running
at UTC-8, UTC+8 or UTC writes the same rows.

| Written by | Java type | Column | Conversion |
| --- | --- | --- | --- |
| Spring Authorization Server (`JdbcOAuth2AuthorizationService`, `JdbcRegisteredClientRepository`) | `java.sql.Timestamp.from(Instant)` / `getTimestamp(...).toInstant()` | `timestamp` (upstream DDL) | driver **and** server |
| Hibernate / JPA (`AppUser.lastModifiedDate`, `AppUserRole.lastModifiedDate`) | `java.util.Date` | `datetime(6)` (MySQL / MariaDB dialect DDL) | driver only |
| Spring Session (`SPRING_SESSION`) | epoch millis | `BIGINT` | none |

What the three URL options do:

| Option | Effect |
| --- | --- |
| `preserveInstants=true` | The driver converts `java.sql.Timestamp` / `java.util.Date` between the instant and a wall clock in `connectionTimeZone`, instead of passing the wall clock through untouched |
| `connectionTimeZone=UTC` | Pins that wall clock to UTC instead of the JVM default (`LOCAL`), so several JVMs writing to the same database always agree |
| `forceConnectionTimeZoneToSession=true` | Sets the MariaDB session `time_zone` to `+00:00`, so the server-side conversion of the `timestamp` columns cancels out and `NOW()` / `DEFAULT CURRENT_TIMESTAMP` are UTC |

Without them the round trip still "works" inside a single JVM, because the same shift is applied when
writing and when reading - which is exactly why the problem is easy to miss. It shows up as soon as a second
JVM with a different default time zone, a SQL `NOW()` such as
[mariadb/seed-app-data.sql](mariadb/seed-app-data.sql), or a person reading the raw column is involved: each
of them is off by the difference between the JVM time zone and the server session time zone. A `timestamp`
column is converted twice, so it is only self-consistent when `connectionTimeZone` equals the session
`time_zone`; `datetime(6)` is converted once by the driver, which is why the JPA columns do not depend on the
session at all.

Notes:

- `@Temporal` is not a fix. On a `java.util.Date` field it only selects the JDBC type (`DATE`, `TIME` or
  `TIMESTAMP`); the DDL type comes from the dialect, and the Hibernate MySQL / MariaDB dialect maps the
  `TIMESTAMP` JDBC type to `datetime($p)`. `@Temporal(TemporalType.TIMESTAMP)` therefore changes nothing for
  this project, and `@Temporal(TemporalType.DATE)` would silently drop the time of day.
- `datetime(6)` is deliberately kept for the JPA columns: one conversion instead of two, and no 2038 limit.
  Switching those columns to `timestamp` would add constraints, not accuracy.
- Do not add `spring.jpa.properties.hibernate.jdbc.time_zone` (`hibernate.jdbc.time_zone`). It stacks a second
  conversion on top of the driver options instead of replacing them.
- The H2 `inmemory` / `test` profiles cannot catch this class of bug (H2 `TIMESTAMP` performs no session-zone
  conversion), so any change here has to be verified against MariaDB.

Verify a running setup:

```bash
docker exec -i sso-mariadb mariadb -uroot -proot authorization_server \
  -e 'SELECT @@system_time_zone, @@session.time_zone, NOW(), UTC_TIMESTAMP();'
# @@session.time_zone must be +00:00 and NOW() must equal UTC_TIMESTAMP()
```

Then create a user through the API and compare the returned `lastModifiedDate` with
`SELECT id, last_modified_date FROM app_user ORDER BY id DESC LIMIT 1;` - both must denote the same instant.
Starting the application with a different JVM time zone (for example `-Duser.timezone=America/Los_Angeles`)
must not change the stored value.
