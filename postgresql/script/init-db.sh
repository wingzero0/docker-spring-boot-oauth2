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
        authorized_scopes varchar(1000) DEFAULT NULL,
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
        user_code_value text DEFAULT NULL,
        user_code_issued_at timestamp DEFAULT NULL,
        user_code_expires_at timestamp DEFAULT NULL,
        user_code_metadata text DEFAULT NULL,
        device_code_value text DEFAULT NULL,
        device_code_issued_at timestamp DEFAULT NULL,
        device_code_expires_at timestamp DEFAULT NULL,
        device_code_metadata text DEFAULT NULL,
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
        post_logout_redirect_uris varchar(1000) DEFAULT NULL,
        scopes varchar(1000) NOT NULL,
        client_settings varchar(2000) NOT NULL,
        token_settings varchar(2000) NOT NULL,
        PRIMARY KEY (id)
    );

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
EOSQL