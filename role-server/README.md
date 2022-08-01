This project cannot run multiple version in the same tomcat instance.

**Please unploy the old version before re-deploy the new version.**

# get token from oauth server
generate access token, which contain read write scope, by client_credentials "Authorization: Basic base64(messaging-client2:secret)", 
```bash
curl -v -X POST \
	http://localhost:8081/auth/oauth2/token \
	-F scope="app_role" \
	-F grant_type=client_credentials \
	-H "Authorization: Basic bWVzc2FnaW5nLWNsaWVudDI6c2VjcmV0"
```

# test resource server with previous token
test resource server api, please replace token in header
```bash
curl -v -X GET \
	http://localhost:8082/res/api/appRole \
	-H "Authorization: Bearer eyJraWQiOiJjMGZjN2E2Yy05M2E1LTQ3OTAtYTc4ZC0yMTYyYzRmYWM4ZGIiLCJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJtZXNzYWdpbmctY2xpZW50MiIsImF1ZCI6Im1lc3NhZ2luZy1jbGllbnQyIiwibmJmIjoxNjU5MzI1MDQwLCJzY29wZSI6WyJhcHBfcm9sZSJdLCJpc3MiOiJodHRwOlwvXC9sb2NhbGhvc3Q6ODA4MVwvYXV0aCIsImV4cCI6MTY1OTMyNTM0MCwiaWF0IjoxNjU5MzI1MDQwfQ.eeHOAVyp29MafvNJIdRcjgZu2CBNyaCESVzdjXtsWgZRyvicZCtgh6SBfqTEPW6HjH_WUxdAPKcoGxZaYFQq5HgmY1K3NAVNPLwD4SpmiWo2jnF4qy1_tVWuB1cMGkzD9qmBFckrMnN7XPEtsD7LNRUNjPjflMnvVZl70NyasBiZTB5MRA9DuTz5vtixBaZ-sp6TMYi_tAoZNxb-zVjkEJfO0jEggTFfeY0X9tJ6lmBhEMZxGVz4Vt4kW4L29A5Ii4UczNxt5XzdQTlHkY9M_gxWm2ZJco0gXknrfuBWa9Zr7qpTavGtKqLohyK6r6VGAddqgJbAuOH6aKQIfiIItg"
```

may get 403 if access token not contain write scope