-- Demo data for the real (MariaDB) profile.
--
-- Run this AFTER ssoserver and role-server have started once, because the
-- app_user / app_user_role tables (and app_user_seq) are created by Hibernate.
--
-- Usage:
--   docker exec -i sso-mariadb mariadb -uroot -proot authorization_server < mariadb/seed-app-data.sql
--
-- Login user: john / 456

DELETE FROM app_user_role WHERE username = 'john';
DELETE FROM app_user WHERE username = 'john';

INSERT INTO app_user (id, display_name, email, is_active, last_modified_by, last_modified_date, password, username) VALUES
    (NEXTVAL(app_user_seq), 'John Don', 'john@test', true, 'system', NOW(), '$2a$12$cLQ0N0QwCSUk2TZvb1b8neYZJmYxZCwosyJ3C2fka5soEK6TR1mR.', 'john');

INSERT INTO app_user_role (id, username, user_role, app_client_id, last_modified_date, last_modified_by) VALUES
    ('1', 'john', 'ADMIN', 'messaging-client2', NOW(), 'system');
