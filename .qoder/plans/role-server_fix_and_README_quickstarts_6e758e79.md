# 修正 role-server 並提供 in-memory / 真實 DB 範例

## Summary
- `role-server` 目前 `mvn test` 會失敗：測試用 `@ActiveProfiles("test")`，但模組沒有 `application-test.properties`，所以落到 `application.properties` 的 MariaDB 設定（localhost:3306 未啟動即連線失敗）。
- 另外 `HomeController` 用 `@Value("${spring.profiles.active}")` 取值，profile 由環境變數/命令列設定時不穩定。
- README 內容過時（舊 `oauth/authorize` 範例、要到處改 properties），需改成「in-memory 一鍵跑」與「Docker MariaDB 真實 DB」兩套可照抄的流程。

## Part 1: role-server 修正

### 1. 新增 `role-server/src/main/resources/application-test.properties`
讓測試自帶 H2，重用既有種子檔（`h2` 已是 test scope 依賴）：
```properties
spring.datasource.url=jdbc:h2:mem:roleserver-test;MODE=MariaDB;DB_CLOSE_DELAY=-1
spring.datasource.username=sa
spring.datasource.password=
spring.datasource.driver-class-name=org.h2.Driver

spring.jpa.hibernate.ddl-auto=update
spring.jpa.generate-ddl=true
spring.jpa.defer-datasource-initialization=true
spring.sql.init.mode=always
spring.sql.init.data-locations=classpath:db/inmemory/data.sql
```

### 2. 修正 `role-server/src/main/resources/db/inmemory/data.sql`
`app_user_role.id` 是 `String`，目前寫 `1`（整數）在 H2 可能型別轉換失敗，改為字串並保留單筆 john 資料：
```sql
INSERT INTO app_user_role (id, username, user_role, app_client_id, last_modified_date, last_modified_by) VALUES
    ('1', 'john', 'ADMIN', 'messaging-client2', '2022-07-04 09:46:25.112214', 'system');
```

### 3. 修正 `role-server/.../HomeController.java`
改用 `Environment` 取得實際啟用的 profile，避免 placeholder 解析失敗並讓 `/api/version` 回傳真實 profile：
```java
public HomeController(Environment environment) { this.environment = environment; }
...
"profile", String.join(",", environment.getActiveProfiles())
```

### 4. 驗證
- `mvn -B -pl role-server -am test`：無 DB 也全綠。
- `mvn spring-boot:run -pl role-server -am -Pinmemory` 後 `curl localhost:8082/res/api/version` 回 `profile=inmemory`。

## Part 2: 真實 DB（MariaDB）所需檔案

### 5. 新增 `mariadb/init-db.sql`
只建 3 張 SAS JDBC 表（`oauth2_registered_client`、`oauth2_authorization`、`oauth2_authorization_consent`），用 `CREATE TABLE IF NOT EXISTS`，內容取自 [mariadb/1.2.7-schema.sql](mariadb/1.2.7-schema.sql)（該檔保持不動，作為 SAS 官方參考）。
`app_user` / `app_user_role` / `app_user_seq` 不在此建立：由 `ssoserver`/`role-server` dev profile 的 `ddl-auto=update` 於啟動時產生。

### 6. 新增 `mariadb/seed-app-data.sql`
在應用程式啟動後（表已存在）執行，idempotent 的示範資料：
```sql
DELETE FROM app_user_role WHERE username = 'john';
DELETE FROM app_user WHERE username = 'john';

INSERT INTO app_user (id, display_name, email, is_active, last_modified_by, last_modified_date, password, username)
VALUES (NEXTVAL(app_user_seq), 'John Don', 'john@test', true, 'system', NOW(),
        '$2a$12$cLQ0N0QwCSUk2TZvb1b8neYZJmYxZCwosyJ3C2fka5soEK6TR1mR.', 'john');

INSERT INTO app_user_role (id, username, user_role, app_client_id, last_modified_date, last_modified_by)
VALUES ('1', 'john', 'ADMIN', 'messaging-client2', NOW(), 'system');
```
密碼雜湊已驗證為 `456`（bcrypt match）。

## Part 3: README 改寫（[README.md](README.md)）

保留可用的 curl 參考段落，重整為：

1. **專案簡介 + 模組/連接埠表**
   ssoserver `8081/auth`（in-memory `8087/auth`）、role-server `8082/res`、ssoclient `8080`、ssomanagement `8080`、ssomanagement-application-runner。註明 Spring Boot 3.5.7 / SAS 1.5.7 / Java 17。

2. **Quick start A：in-memory（不需要資料庫）**
   ```bash
   # terminal 1
   mvn spring-boot:run -pl ssoserver -am -Pinmemory     # http://localhost:8087/auth
   # terminal 2
   mvn spring-boot:run -pl role-server -am -Pinmemory   # http://localhost:8082/res
   # optional terminal 3（瀏覽器登入）
   mvn spring-boot:run -pl ssoclient -am -Pinmemory     # http://127.0.0.1:8080/loginPage
   ```
   驗證指令（皆已在本機實測可通）：
   - `curl http://localhost:8082/res/api/version`
   - 取 token：`curl -u messaging-client2:secret -d 'grant_type=client_credentials&scope=app_role' http://localhost:8087/auth/oauth2/token`
   - 帶 token 打 `GET /res/api/appRoles`、`GET /res/api/appRole/john`（應回 `["ADMIN"]`）
   - introspection 範例
   - 瀏覽器：`http://127.0.0.1:8080/loginPage`，帳號 `john` / `456`
   註明 in-memory profile 使用 H2 + `application.init.oauth2registeredclient=true` 自動建立 `messaging-client2`。

3. **Quick start B：MariaDB（真實 DB，Docker）**
   ```bash
   docker run -d --name sso-mariadb -p 3306:3306 \
     -e MARIADB_ROOT_PASSWORD=root \
     -e MARIADB_DATABASE=authorization_server \
     -e MARIADB_USER=authorization_server \
     -e MARIADB_PASSWORD=authorization_server \
     mariadb:11
   # 等 DB ready 後套用 oauth2 表
   docker exec -i sso-mariadb mariadb -uroot -proot authorization_server < mariadb/init-db.sql
   # 啟動（沿用 application.properties 預設連線，dev profile 會建 app_user* 表並建立 messaging-client2）
   mvn spring-boot:run -pl ssoserver -am
   mvn spring-boot:run -pl role-server -am
   # 灌示範資料
   docker exec -i sso-mariadb mariadb -uroot -proot authorization_server < mariadb/seed-app-data.sql
   ```
   之後貼上與 Quick start A 相同但 port 8081 的 curl 驗證；附 `docker rm -f sso-mariadb` 清理。
   簡短註明 PostgreSQL 替代方案（`.devcontainer` 的 `postgresqldb`、`postgresql/script/init-db.sh`、需改 driver/url）。

4. **其他段落**
   - `ssoserver/npmLib` 前端建置步驟保留。
   - `package and run war file` 保留，更新 war 檔名（`*-${active.profile}.war`）與 profile 用法。
   - 刪除過時的 `oauth/authorize` 手動 cookie 流程，改成一句說明 consent 頁面在 `/oauth2/consent`、以 ssoclient 走 authorization_code。
   - 保留 revoke / refresh / introspect 範例並修正錯字。
   - 新增 `## Tests`：`mvn test`（role-server 已用 H2 test profile，不需 DB）。

5. 順手把 [TODO.md](TODO.md) 的 `- update read me` 移除。

## Test Plan
1. `mvn -B test`（全部模組）在**無 DB** 狀態下通過。
2. In-memory 實測：以 `-Pinmemory` 起 ssoserver(8087) + role-server(8082)，逐條執行 README 的 curl，確認 token 取得、`/api/appRoles` 回 `["ADMIN"]`、`/api/version` 顯示 `inmemory`，之後關閉。
3. 真實 DB 實測：依 README 起 `mariadb:11` container（本機 Docker 可用），套用 `init-db.sql`，起兩個模組，套用 `seed-app-data.sql`，在 8081/8082 重跑同一組 curl 驗證，最後 `docker rm -f` 清理。
4. `mvn -B -DskipTests package` 確認打包未受影響。

## Assumptions
- 真實 DB 範例以 MariaDB 為主（符合 `application.properties` 預設值），PostgreSQL 僅一句帶過。
- `app_user` / `app_user_role` 交給 JPA `ddl-auto=update` 建立，不新增重複 DDL；因此種子資料需在應用啟動後執行。
- [mariadb/1.2.7-schema.sql](mariadb/1.2.7-schema.sql) 不改動（其結尾的 `app_user` INSERT 依賴已存在的表，故 Docker 初始化改用新的 `init-db.sql`）。
