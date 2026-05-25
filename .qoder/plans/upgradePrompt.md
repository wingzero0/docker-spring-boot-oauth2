​pom.xml​ ​pom.xml​ 

/​plan​ 
force on pom.xml and ssoserver/pom.xml, i want to update core library "spring-security-oauth2-authorization-server" to 1.5.7 (current latest version).

1. update  spring-boot version to meet, spring-security-oauth2-authorization-server the minimun requirement.
2. check if any changes on default schema. esiting 1.2.7 schema you can take a look on "mariadb/1.2.7-schema.sql".
3. check if any required changes if i only need some basic oauth2 server function like login page, consent page, logout page, introspection endpoint.