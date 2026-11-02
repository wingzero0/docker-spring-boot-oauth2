# TODO list

- 解釋Mariadb的參數 preserveInstants=true&connectionTimeZone=UTC&forceConnectionTimeZoneToSession=true ，如果我java server使用的是UTC-8，會有不會有問題？請對比真實的java type和sql column，以確認時間差異問題。以我所知，如果一直都使用timestamp不應該有問題，為何這邊要特別做指定參數。是這個專安有使用特別的java type嗎？
- 追 test case 可能的問題。role-server test / in-memory 中的沒有create user，它應該是放了在ssoserver 中的 in-memory中，這類有沒有問題（道理上起動有先後，但總感覺有些風險）
- implement ssomanagement for CRUD ssoenity