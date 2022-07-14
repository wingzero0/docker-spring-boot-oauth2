This project cannot run multiple version in the same tomcat instance.

**Please unploy the old version before re-deploy the new version.**

# get token from oauth server
generate access token, which contain read write scope, by password
```bash
curl -X POST \
	http://localhost:8081/auth/oauth/token \
	-F grant_type=password \
	-F username=john \
	-F password=456 \
	-F client_id=spring-security-oauth2-read-write-client \
	-F client_secret=spring-security-oauth2-read-write-client-password1234
```

generate access token, which contain read scope only, by password
```bash
curl -X POST \
	http://localhost:8081/auth/oauth/token \
	-F grant_type=password \
	-F username=john \
	-F password=456 \
	-F client_id=spring-security-oauth2-read-client \
	-F client_secret=spring-security-oauth2-read-client-password1234
```

# test resource server with previous token
test resource server api, please replace token in header
```bash
curl -X GET \
	http://localhost:8082/res/user/read \
	-H "Authorization: Bearer 71H_HRaL45xe8hyIZOZhcthZhHU"
```

may get 403 if access token not contain write scope
```bash
curl -X GET \
	http://localhost:8082/res/user/read \
	-H "Authorization: Bearer 71H_HRaL45xe8hyIZOZhcthZhHU"
    
curl -X GET \
	http://localhost:8082/res/user/write \
	-H "Authorization: Bearer E0e0Eo6rXcNFH99TOefZL5G-PTg"
```