# Upgrade Authorization Server 1.5.7

## Scope
Files in focus per request:
- [pom.xml](file:///home/deck/workspace/docker-spring-boot-oauth2/pom.xml)
- [ssoserver/pom.xml](file:///home/deck/workspace/docker-spring-boot-oauth2/ssoserver/pom.xml)

## Task 1: Spring Boot version to satisfy SAS 1.5.7

Spring Authorization Server 1.5.7 requires Spring Framework 6.2.x and Spring Security 6.5.x. The matching Spring Boot line is **3.5.x** (Spring Boot 3.5.0 introduced SAS 1.5.0; the latest 3.5.x patch bundles SAS 1.5.7).

Recommended changes in [pom.xml](file:///home/deck/workspace/docker-spring-boot-oauth2/pom.xml):

```xml
<parent>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-parent</artifactId>
    <version>3.5.7</version>            <!-- was 3.2.12 -->
</parent>
```

```xml
<dependency>
    <groupId>org.springframework.security</groupId>
    <artifactId>spring-security-oauth2-authorization-server</artifactId>
    <version>1.5.7</version>            <!-- was 1.2.7; can be removed since Boot 3.5.7 already manages 1.5.7 -->
</dependency>
```

Note: With Boot 3.5.7, the explicit `dependencyManagement` entry for `spring-security-oauth2-authorization-server` becomes redundant and can be deleted. Keep it only if you want to pin the version independently of the Boot BOM.

Java target stays at 17 (SAS 1.5.x still supports Java 17).

## Task 2: Schema diff vs `mariadb/1.2.7-schema.sql`

Compared SAS 1.5.7 schemas in `org/springframework/security/oauth2/server/authorization/` against [mariadb/1.2.7-schema.sql](file:///home/deck/workspace/docker-spring-boot-oauth2/mariadb/1.2.7-schema.sql):

- `oauth2_registered_client`: identical columns and types.
- `oauth2_authorization`: identical columns and types (all 31 columns including `user_code_*` and `device_code_*`).
- `oauth2_authorization_consent`: identical.

**Conclusion: no required schema migration.**

Optional (advisory only, from upstream comments added in 1.5.x):
- For MariaDB/MySQL, append `preserveInstants=true&connectionTimeZone=UTC&forceConnectionTimeZoneToSession=true` to the JDBC URL for accurate timestamp storage.
- For PostgreSQL, prefer `timestamptz` over `timestamp`.

## Task 3: Code changes for basic functions (login, consent, logout, introspection)

Reviewed [AuthorizationServerConfig.java](file:///home/deck/workspace/docker-spring-boot-oauth2/ssoserver/src/main/java/io/github/wingzero0/ssoserver/AuthorizationServerConfig.java) and [SecurityConfig.java](file:///home/deck/workspace/docker-spring-boot-oauth2/ssoserver/src/main/java/io/github/wingzero0/ssoserver/SecurityConfig.java).

### Required changes
**None.** All APIs used for the four endpoints you need still work in 1.5.7:
- Login page: handled by `formLogin().loginPage("/login")` in `SecurityConfig` — unchanged.
- Consent page: `authorizationEndpoint(ae -> ae.consentPage("/oauth2/consent"))` — unchanged.
- Logout: provided by Spring Security's default logout + OIDC logout endpoint via `oidc(Customizer.withDefaults())` — unchanged.
- Introspection: enabled by default via `OAuth2AuthorizationServerConfiguration.applyDefaultSecurity(http)` (and still default in the new builder) — unchanged.
- `JdbcRegisteredClientRepository`, `JdbcOAuth2AuthorizationService`, `JdbcOAuth2AuthorizationConsentService`, `OAuth2AuthorizationServerConfiguration.jwtDecoder(...)`, `AuthorizationServerSettings.builder()` — all still present and signature-compatible.

### Optional (deprecation cleanup, not required for 1.5.7 to run)
`OAuth2AuthorizationServerConfiguration.applyDefaultSecurity(HttpSecurity)` was deprecated in 1.4 and is scheduled for removal in 2.0. It still works in 1.5.7. If you want to future-proof, replace lines 65–69 of `AuthorizationServerConfig.java` with the builder form:

```java
OAuth2AuthorizationServerConfigurer authorizationServerConfigurer =
        OAuth2AuthorizationServerConfigurer.authorizationServer();
http
    .securityMatcher(authorizationServerConfigurer.getEndpointsMatcher())
    .with(authorizationServerConfigurer, authorizationServer ->
        authorizationServer
            .authorizationEndpoint(ae -> ae.consentPage(CUSTOM_CONSENT_PAGE_URI))
            .oidc(Customizer.withDefaults())
    );
```

The remaining `exceptionHandling(...)` and `oauth2ResourceServer(...)` blocks stay as-is.

## Cross-module impact (informational)
Bumping the root parent to Boot 3.5.x will also affect siblings (`ssoentity`, `ssoclient`, `role-server`, `ssomanagement`, `ssomanagement-application-runner`). Two known transitive notes worth verifying after the bump:
- `net.sourceforge.htmlunit:htmlunit` (test scope in `ssoserver/pom.xml`) was relocated to `org.htmlunit:htmlunit` in newer Spring Boot BOMs; the old coordinate may no longer resolve. If a build error appears here, switch the test dependency.
- Spring Session JDBC, MariaDB JDBC client and other Boot-managed deps will pick up new versions transparently.

These are outside the strict ask for `pom.xml` + `ssoserver/pom.xml` but flagged so the upgrade is not surprised at compile/test time.

## Summary of edits
1. [pom.xml](file:///home/deck/workspace/docker-spring-boot-oauth2/pom.xml): bump parent to `3.5.7`; bump (or remove) `spring-security-oauth2-authorization-server` managed version to `1.5.7`.
2. [ssoserver/pom.xml](file:///home/deck/workspace/docker-spring-boot-oauth2/ssoserver/pom.xml): no required edits. (Optionally fix `htmlunit` coordinates if Boot 3.5 dropped the legacy ones.)
3. No schema migration required.
4. No required source-code changes; one optional refactor in [AuthorizationServerConfig.java](file:///home/deck/workspace/docker-spring-boot-oauth2/ssoserver/src/main/java/io/github/wingzero0/ssoserver/AuthorizationServerConfig.java) to drop the deprecated `applyDefaultSecurity` call.
