# Context
* uaa is running on http://localhost:8080, context: /uaa
* demo resource server is running on http://localhost:8082, context: /

# Requirements

* uaac target http://localhost:8080/uaa
* uaac token client get admin --secret adminsecret
* uaac group add test
* uaac user add demo-user -p demosecret --emails demo.user@test.com
* uaac member add test demo-user
* uaac group add documents.123.read
* uaac member add documents.123.read demo-user
* uaac client add app-resource --name app-resource --authorized_grant_types "implicit" --scope "test documents.*.read" --autoapprove "test documents.*.read" --redirect_uri "http://example.com"
* uaac client add demo-resource --name demo-resource --secret demosecret --authorized_grant_types "client_credentials" --authorities "uaa.resource"

The "uaa.resource" is used to either invoke /check_token or /token_key (for decoding JWT tokens locally)

# /check_token

* the demo-resource client must have the "uaa.resource" authority to perform /check_token.
* the access_token is granted for the scopes "test"
* In a first time, we have used the "token-info-uri" configuration
* In a second time, the "key-uri" configuration was preferred.

# downstream

* $JAVA_HOME/bin/java -Dserver.port=8083 -jar target/demo-resource-0.0.1-SNAPSHOT.jar
* $JAVA_HOME/bin/java -Xdebug -Xrunjdwp:server=y,transport=dt_socket,address=8092,suspend=n -Dserver.port=8082 -jar target/demo-resource-0.0.1-SNAPSHOT.jar

