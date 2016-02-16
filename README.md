# Context
* uaa is running on http://localhost:8080, context: /uaa
* demo resource server is running on http://localhost:8082, context: /

# Requirements

* uaac target http://localhost:8080/uaa
* uaac token client get admin --secret adminsecret
* uaac group add test
* uaac user add demo-user -p demosecret --emails demo.user@test.com
* uaac member add test demo-user
* uaac client add app-resource --name app-resource --authorized_grant_types "implicit" --scope "test test1 test2" --autoapprove "test test1 test2" --redirect_uri "http://example.com"
* uaac client add demo-resource --name demo-resource --secret demosecret --authorized_grant_types "client_credentials" --authorities "uaa.resource"

# /check_token

* the demo-resource client must have the "uaa.resource" authority to perform /check_token.
* the access_token is granted for the scopes "test"