# build and test notes
Build a spring boot oauth server with postgresql db.

There is a bug in spring-security-oauth2-authorization-server v0.2.3 (or it's dependency) to connect mysql. not sure that is it fixed in later version.

## docker ide 
linux container

    $> docker-composer up -d # create env
    $> docker-composer start # rerun env
    $> docker-composer stop # stop env
    $> docker-composer down # delete env

visit localhost:9001 for IDE interface, login password "admin"

## running app in docker
running ssoserver at localhost:8081/auth
```bash
cp ssoserver/src/main/filters-example.properties ssoserver/src/main/filters-dev.properties
mvn clean compile spring-boot:run -pl ssoserver -am
```

running ssoclient at ***127.0.0.1:8080*** . because of redirect-uri in db is marked as 127.0.0.1, it cannot change to localhost.
```bash
cp ssoclient/src/main/filters-example.properties ssoclient/src/main/filters-dev.properties
mvn clean compile spring-boot:run -pl ssoclient -am
```

running ssoresourceserver at localhost:8082/res
```bash
cp ssoresourceserver/src/main/filters-example.properties ssoresourceserver/src/main/filters-dev.properties
mvn clean compile spring-boot:run -pl ssoresourceserver -am
```

## testing command

### test client_credentials authentication
```bash
# for spring-security-oauth2-authorization-server, if its client_authentication_methods is "client_secret_basic"
# it means that you need to base64 encode "client_id:client_secret" and put in header "Authorization: Basic base64(client_id:client_secret)"
# scope is optional
curl -v -X POST \
	http://localhost:8081/auth/oauth2/token \
	-F scope="message.read message.write" \
	-F grant_type=client_credentials \
	-H "Authorization: Basic bWVzc2FnaW5nLWNsaWVudDI6c2VjcmV0"

# for spring-security-oauth2-authorization-server, if its client_authentication_methods is "client_secret_post"
# then it act as the old one
curl -v -X POST \
	http://localhost:8081/auth/oauth2/token \
	-F scope="message.read message.write" \
	-F grant_type=client_credentials \
	-F client_id=messaging-client2 \
	-F client_secret=secret
```

### authentication
generate access token by client_credentials or authorization_code. password authenication is not support.
```bash
# TODO write example about authorization_code
```

use access token to visit resource server
```bash
curl -v http://localhost:8082/res/user/read -H "Authorization: Bearer xxxx"
```

revoke access token and refresh_token
```bash
curl -v -X POST http://localhost:8081/auth/oauth2/revoke \
 -H "Authorization: Basic bWVzc2FnaW5nLWNsaWVudDI6c2VjcmV0" \
 -F token_type=access_token \
 -F token=xxx

curl -v -X POST http://localhost:8081/auth/oauth2/revoke \
 -H "Authorization: Basic bWVzc2FnaW5nLWNsaWVudDI6c2VjcmV0" \
 -F token_type=refresh_token \
 -F token=xxx
```

refresh
```bash
curl -v -X POST \
	http://localhost:8081/auth/oauth2/token \
	-H "Authorization: Basic bWVzc2FnaW5nLWNsaWVudDI6c2VjcmV0" \
	-F grant_type=refresh_token \
	-F refresh_token=xxx
```



## introspect endpoint
For resource server to verify token. That is, forward the token to auth server to check if it valid

```bash
# the client_id and client_secret need not to be the original client.
# that is, the token was generated by messaging-client, it still can verified by messaging-client2
curl -v -X POST \
	http://localhost:8081/auth/oauth2/introspect \
	-H "Authorization: Basic bWVzc2FnaW5nLWNsaWVudDI6c2VjcmV0" \
	-F token=xxx

# response will look like below.
# {"active":true,"sub":"messaging-client","aud":["messaging-client"],"nbf":1651041699,"scope":"message.read message.write","iss":"http://localhost:9000","exp":1651041999,"iat":1651041699,"client_id":"messaging-client","token_type":"Bearer"}
```

### test code grant
to be revise with new framework
```
curl -v -X GET "http://localhost:8081/auth/oauth/authorize?client_id=spring-security-oauth2-read-write-client&response_type=code&state=5ca75bd30&redirect_uri=http%3A%2F%2Flocalhost%3A8080%2Flogin%2Foauth2%2Fcode%2Fmy-client-2"

curl -v -X GET "http://localhost:8081/auth/login" --cookie "SESSION=ZWUyZWUzY2QtNTdhZi00ODE2LWFjNzItZWY1N2E1ZjJkZGI4"

curl -v -X POST "http://localhost:8081/auth/login" --cookie "SESSION=ZWUyZWUzY2QtNTdhZi00ODE2LWFjNzItZWY1N2E1ZjJkZGI4" -F _csrf=b50d4f23-a77e-4ddb-970b-42e64509e136 -F username=john -F password=456

curl -v -X GET "http://localhost:8081/auth/oauth/authorize?client_id=spring-security-oauth2-read-write-client&response_type=code&state=5ca75bd30&redirect_uri=http%3A%2F%2Flocalhost%3A8080%2Flogin%2Foauth2%2Fcode%2Fmy-client-2" --cookie "SESSION=OGM2NTdmYjgtMjA4Mi00MmIzLTk5MzEtNWQ2ZTU2MTM3NzMz"

curl -v -X POST "http://localhost:8081/auth/oauth/authorize" --cookie "SESSION=OGM2NTdmYjgtMjA4Mi00MmIzLTk5MzEtNWQ2ZTU2MTM3NzMz" -F _csrf=16fb5657-9ed0-4833-a523-7e1e64aaa364 -F user_oauth_approval=true -F scope.read=true -F scope.write=true -F scope.full_user_list=false -F scope.user_management=false

http://localhost:8080/login/oauth2/code/my-client-2?code=PEio1w&state=5ca75bd30

curl -X POST \
	http://localhost:8081/auth/oauth/token \
	-F grant_type=authorization_code \
	-F redirect_uri="http://localhost:8080/login/oauth2/code/my-client-2" \
	-F code=PEio1w \
	-F client_id=spring-security-oauth2-read-write-client \
	-F client_secret=spring-security-oauth2-read-write-client-password1234

{"access_token":"6e58306a-c371-4aa8-9dac-80083c7aab7f","token_type":"bearer","refresh_token":"d14c9aca-d7ab-49e6-bd05-5705aa6927d6","expires_in":10799,"scope":"read write"}
```
