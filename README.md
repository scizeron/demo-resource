# Context
* uaa is running on http://localhost:8080, context: /uaa
* demo resource server is running on http://localhost:8082, context: /

# Requirements

* uaac target http://localhost:8080/uaa
* uaac token client get admin --secret adminsecret
* uaac group add test
* uaac group add test1
* uaac group add test2
* uaac group add test3
* uaac user add demo-user -p demosecret --emails demo.user@test.com
* uaac member add test demo-user
* uaac client add app-resource --name app-resource --authorized_grant_types "implicit" --scope "test test1 test2" --autoapprove "test test1 test2" --redirect_uri "http://example.com"
* uaac client add demo-resource --name demo-resource --secret demosecret --authorized_grant_types "client_credentials" --authorities "uaa.resource"

The "uaa.resource" is used to either invoke /check_token or /token_key (for decoding JWT tokens locally)

# /check_token

* the demo-resource client must have the "uaa.resource" authority to perform /check_token.
* the access_token is granted for the scopes "test"
* In a first time, we have used the "token-info-uri" configuration
* In a second time, the "key-uri" configuration was preferred.

# downstream

* java -Dserver.port=8083 -jar  target/demo-resource-0.0.1-SNAPSHOT.jar
* java -Xdebug -Xrunjdwp:server=y,transport=dt_socket,address=8092,suspend=n -Dserver.port=8082 -jar target\demo-resource-0.0.1-SNAPSHOT.jar

