-- Test data for role-server in-memory profile.
-- DDL is handled by Hibernate (spring.jpa.hibernate.ddl-auto=update);
-- this file only seeds rows. Edit freely.

INSERT INTO app_user_role (id, username, user_role, app_client_id, last_modified_date, last_modified_by) VALUES
    (1, 'john', 'ADMIN', 'messaging-client2', '2022-07-04 09:46:25.112214', 'system');
