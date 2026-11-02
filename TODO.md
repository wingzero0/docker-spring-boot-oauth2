# TODO list

- rename mariadb/1.2.7-schema.sql to mariadb/1.5.4-schema.sql，刪去 mariadb/init-db.sql，只需要 upstream 的版本就夠。
- 追 test case 可能的問題。role-server test / in-memory 中的沒有create user，它應該是放了在ssoserver 中的 in-memory中，這類有沒有問題（道理上起動有先後，但總感覺有些風險）
- README.md 改為 json parse 由 python改為 jq 這個command line tools
- 改本專案的 pom.xml version 去0.13.0
- implement ssomanagement for CRUD ssoenity