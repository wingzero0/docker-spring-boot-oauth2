# TODO list

- 修改 pom version為 ci frienly 的型式，可以試套用 skill 中maven-ci-friendly-versioning.
- 追 test case 可能的問題。role-server test / in-memory 中的沒有create user，它應該是放了在ssoserver 中的 in-memory中，這類有沒有問題（道理上起動有先後，但總感覺有些風險）
- 把 ssomanagement 的預設db setting 改為 mariadb。並考慮是不是有mariadb 的相關時區問題。
- 在 ssomanagement 中 implement ssoenity 的 backend / frontend CRUD , 