#!/bin/bash
set -e

psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" --dbname "$POSTGRES_DB" <<-EOSQL
	CREATE DATABASE authorization_server;
    \connect authorization_server;
    CREATE TABLE oauth2_authorization (
        id varchar(100) NOT NULL,
        registered_client_id varchar(100) NOT NULL,
        principal_name varchar(200) NOT NULL,
        authorization_grant_type varchar(100) NOT NULL,
        attributes text DEFAULT NULL,
        state varchar(500) DEFAULT NULL,
        authorization_code_value text DEFAULT NULL,
        authorization_code_issued_at timestamp DEFAULT NULL,
        authorization_code_expires_at timestamp DEFAULT NULL,
        authorization_code_metadata text DEFAULT NULL,
        access_token_value text DEFAULT NULL,
        access_token_issued_at timestamp DEFAULT NULL,
        access_token_expires_at timestamp DEFAULT NULL,
        access_token_metadata text DEFAULT NULL,
        access_token_type varchar(100) DEFAULT NULL,
        access_token_scopes varchar(1000) DEFAULT NULL,
        oidc_id_token_value text DEFAULT NULL,
        oidc_id_token_issued_at timestamp DEFAULT NULL,
        oidc_id_token_expires_at timestamp DEFAULT NULL,
        oidc_id_token_metadata text DEFAULT NULL,
        refresh_token_value text DEFAULT NULL,
        refresh_token_issued_at timestamp DEFAULT NULL,
        refresh_token_expires_at timestamp DEFAULT NULL,
        refresh_token_metadata text DEFAULT NULL,
        PRIMARY KEY (id)
    );

    CREATE TABLE oauth2_authorization_consent (
        registered_client_id varchar(100) NOT NULL,
        principal_name varchar(200) NOT NULL,
        authorities varchar(1000) NOT NULL,
        PRIMARY KEY (registered_client_id, principal_name)
    );

    CREATE TABLE oauth2_registered_client (
        id varchar(100) NOT NULL,
        client_id varchar(100) NOT NULL,
        client_id_issued_at timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL,
        client_secret varchar(200) DEFAULT NULL,
        client_secret_expires_at timestamp DEFAULT NULL,
        client_name varchar(200) NOT NULL,
        client_authentication_methods varchar(1000) NOT NULL,
        authorization_grant_types varchar(1000) NOT NULL,
        redirect_uris varchar(1000) DEFAULT NULL,
        scopes varchar(1000) NOT NULL,
        client_settings varchar(2000) NOT NULL,
        token_settings varchar(2000) NOT NULL,
        PRIMARY KEY (id)
    );

    INSERT INTO "oauth2_registered_client" ("id", "client_id", "client_id_issued_at", "client_secret", "client_secret_expires_at", "client_name", "client_authentication_methods", "authorization_grant_types", "redirect_uris", "scopes", "client_settings", "token_settings") VALUES
    ('7eb74be4-af92-4af1-92ca-d0c25a280dca',	'messaging-client',	'2022-07-03 18:15:58.385897',	'{noop}secret',	NULL,	'7eb74be4-af92-4af1-92ca-d0c25a280dca',	'client_secret_basic',	'refresh_token,client_credentials,authorization_code',	'http://127.0.0.1:8080/authorized,http://127.0.0.1:8080/login/oauth2/code/messaging-client-oidc',	'openid,message.read,message.write',	'{"@class":"java.util.Collections\$UnmodifiableMap","settings.client.require-proof-key":false,"settings.client.require-authorization-consent":true}',	'{"@class":"java.util.Collections\$UnmodifiableMap","settings.token.reuse-refresh-tokens":true,"settings.token.id-token-signature-algorithm":["org.springframework.security.oauth2.jose.jws.SignatureAlgorithm","RS256"],"settings.token.access-token-time-to-live":["java.time.Duration",300.000000000],"settings.token.access-token-format":{"@class":"org.springframework.security.oauth2.core.OAuth2TokenFormat","value":"self-contained"},"settings.token.refresh-token-time-to-live":["java.time.Duration",3600.000000000]}');
    INSERT INTO "oauth2_registered_client" ("id", "client_id", "client_id_issued_at", "client_secret", "client_secret_expires_at", "client_name", "client_authentication_methods", "authorization_grant_types", "redirect_uris", "scopes", "client_settings", "token_settings") VALUES
    ('2222222222222222',	'messaging-client2',	'2022-07-03 18:15:58.385897',	'\$2a\$12\$6patnf/iijlCCXtCOkniCO.jKWubx79gR.DIpfxW0P0Ew37dDJIl6',	NULL,	'2222222222222222',	'client_secret_basic',	'refresh_token,client_credentials,authorization_code',	'http://127.0.0.1:8080/authorized,http://127.0.0.1:8080/login/oauth2/code/messaging-client-oidc',	'openid,message.read,message.write',	'{"@class":"java.util.Collections\$UnmodifiableMap","settings.client.require-proof-key":false,"settings.client.require-authorization-consent":true}',	'{"@class":"java.util.Collections\$UnmodifiableMap","settings.token.reuse-refresh-tokens":true,"settings.token.id-token-signature-algorithm":["org.springframework.security.oauth2.jose.jws.SignatureAlgorithm","RS256"],"settings.token.access-token-time-to-live":["java.time.Duration",300.000000000],"settings.token.access-token-format":{"@class":"org.springframework.security.oauth2.core.OAuth2TokenFormat","value":"self-contained"},"settings.token.refresh-token-time-to-live":["java.time.Duration",3600.000000000]}');

    DROP TABLE IF EXISTS "app_user";
    DROP SEQUENCE IF EXISTS app_user_id_seq;
    CREATE SEQUENCE app_user_id_seq INCREMENT 1 MINVALUE 1 MAXVALUE 2147483647 CACHE 1;

    CREATE TABLE "public"."app_user" (
        "id" integer DEFAULT nextval('app_user_id_seq') NOT NULL,
        "display_name" character varying(255),
        "email" character varying(255),
        "is_active" character varying(255),
        "last_modified_by" character varying(255),
        "last_modified_date" timestamp,
        "password" character varying(255),
        "username" character varying(255),
        CONSTRAINT "app_user_pkey" PRIMARY KEY ("id")
    ) WITH (oids = false);

    INSERT INTO "app_user" ("display_name", "email", "is_active", "last_modified_by", "last_modified_date", "password", "username") VALUES
    ('John Don',	'john@test',	'Y',	'system',	'2022-07-04 09:46:25.112214',	'\$2a\$12\$cLQ0N0QwCSUk2TZvb1b8neYZJmYxZCwosyJ3C2fka5soEK6TR1mR.',	'john');

    CREATE TABLE SPRING_SESSION (
        PRIMARY_ID CHAR(36) NOT NULL,
        SESSION_ID CHAR(36) NOT NULL,
        CREATION_TIME BIGINT NOT NULL,
        LAST_ACCESS_TIME BIGINT NOT NULL,
        MAX_INACTIVE_INTERVAL INT NOT NULL,
        EXPIRY_TIME BIGINT NOT NULL,
        PRINCIPAL_NAME VARCHAR(100),
        CONSTRAINT SPRING_SESSION_PK PRIMARY KEY (PRIMARY_ID)
    );

    CREATE UNIQUE INDEX SPRING_SESSION_IX1 ON SPRING_SESSION (SESSION_ID);
    CREATE INDEX SPRING_SESSION_IX2 ON SPRING_SESSION (EXPIRY_TIME);
    CREATE INDEX SPRING_SESSION_IX3 ON SPRING_SESSION (PRINCIPAL_NAME);

    CREATE TABLE SPRING_SESSION_ATTRIBUTES (
        SESSION_PRIMARY_ID CHAR(36) NOT NULL,
        ATTRIBUTE_NAME VARCHAR(200) NOT NULL,
        ATTRIBUTE_BYTES BYTEA NOT NULL,
        CONSTRAINT SPRING_SESSION_ATTRIBUTES_PK PRIMARY KEY (SESSION_PRIMARY_ID, ATTRIBUTE_NAME),
        CONSTRAINT SPRING_SESSION_ATTRIBUTES_FK FOREIGN KEY (SESSION_PRIMARY_ID) REFERENCES SPRING_SESSION(PRIMARY_ID) ON DELETE CASCADE
    );
EOSQL