This project cannot run multiple version in the same tomcat instance.

**Please unploy the old version before re-deploy the new version.**

# get token from oauth server
generate access token, which contain read write scope, by client_credentials "Authorization: Basic base64(messaging-client2:secret)", 
```bash
curl -v -X POST \
	http://localhost:8081/auth/oauth2/token \
	-F scope="message.read" \
	-F grant_type=client_credentials \
	-H "Authorization: Basic bWVzc2FnaW5nLWNsaWVudDI6c2VjcmV0"
```

# test resource server with previous token
test resource server api, please replace token in header
```bash
curl -v -X GET \
	http://localhost:8082/res/user/read \
	-H "Authorization: Bearer eyJraWQiOiJjMGZjN2E2Yy05M2E1LTQ3OTAtYTc4ZC0yMTYyYzRmYWM4ZGIiLCJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJtZXNzYWdpbmctY2xpZW50MiIsImF1ZCI6Im1lc3NhZ2luZy1jbGllbnQyIiwibmJmIjoxNjU5MzI2NDUxLCJzY29wZSI6WyJtZXNzYWdlLnJlYWQiXSwiaXNzIjoiaHR0cDpcL1wvbG9jYWxob3N0OjgwODFcL2F1dGgiLCJleHAiOjE2NTkzMjY3NTEsImlhdCI6MTY1OTMyNjQ1MX0.SUs8J4hoZkwBwK00pM7czWWExcprRscMw6OeP1sCTP8FnI0mjtVrAP1WOl41cffqoncbmh44-undCtSiA4skcOojPmDwfZFUK8yMwo9qJDty8kEO1OF-nu-D9QmYm2c4G77zrq1P9xVm15176qpFV8swlcPd7ZWdUq0khWrgacrjzBEQ6gfa6Iespx12O6taVekwo4wuk0OFvIJM4NcHR3WkliSgNu4ZXy13belVAyjQn1qWnz7dtVqHtOGgZaDddXEBSHcS7hZ2Hjva3lhV6M0UhyMVlfvqNicT-0__kzFILeN4oGCn2nBkFdOJkIqp2CtcAXQrrrSCakyA621mbA"
```

may get 403 if access token not contain write scope
```bash
curl -v -X POST \
	http://localhost:8081/auth/oauth2/token \
	-F scope="app_role" \
	-F grant_type=client_credentials \
	-H "Authorization: Basic bWVzc2FnaW5nLWNsaWVudDI6c2VjcmV0"
    
curl -v -X GET \
	http://localhost:8082/res/user/read \
	-H "Authorization: Bearer eyJraWQiOiJjMGZjN2E2Yy05M2E1LTQ3OTAtYTc4ZC0yMTYyYzRmYWM4ZGIiLCJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJtZXNzYWdpbmctY2xpZW50MiIsImF1ZCI6Im1lc3NhZ2luZy1jbGllbnQyIiwibmJmIjoxNjU5MzI2NDk4LCJzY29wZSI6WyJhcHBfcm9sZSJdLCJpc3MiOiJodHRwOlwvXC9sb2NhbGhvc3Q6ODA4MVwvYXV0aCIsImV4cCI6MTY1OTMyNjc5OCwiaWF0IjoxNjU5MzI2NDk4fQ.E7RN-rH9Iwk5TDelrsRJ_ewovexhJq91g6hYs2K-0pm-Xm7fqK-ZP25koY_VMp3NkyulEj-AAYGBHk89bEpdfXyNSFWxtJfa6pE16Db8z0D1mCj8NiVQCYkZsKv87FX2D_WYJaAZLCHTqDpR4I-PN9_X2hdiUUv0sOG_QJWaZP5tpixsrAlj-y23MVr-sX79NidFKiWF9KWoZka1jXZoSt9YZ6mEytqBGR8RZzSAeE2hXhrchY6T7Yozw5GTrMrq3p6ilB6WA0P8a_voaoDaZtOu7aYIfiLralLcDzGaL_n7zTYyj5bzE8XYJWbHwRC4yoy_xKqXCGaJ4OdgiPt48A"
```