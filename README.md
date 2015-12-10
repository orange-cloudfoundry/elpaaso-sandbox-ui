# elpaaso-sandbox-ui [![Build Status](https://travis-ci.org/Orange-OpenSource/elpaaso-sandbox-service.svg?branch=master)](https://travis-ci.org/Orange-OpenSource/elpaaso-sandbox-ui)
[![Apache Version 2 Licence](http://img.shields.io/:license-Apache%20v2-blue.svg)](LICENSE)
[![Bintray](https://www.bintray.com/docs/images/bintray_badge_color.png)](https://bintray.com/elpaaso/maven/elpaaso-sandbox-ui/view?source=watch)
[![JCenter](https://img.shields.io/badge/JCenter-available-blue.svg)](https://bintray.com/bintray/jcenter?filterByPkgName=elpaaso-sandbox-ui)
[![Join the chat at https://gitter.im/Orange-OpenSource/elpaaso](https://img.shields.io/badge/gitter-join%20chat%20→-brightgreen.svg)](https://gitter.im/Orange-OpenSource/elpaaso?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge&utm_content=badge)

elpaaso sandbox ui


Angular and spring Oauth2 security based GUI to create a [sandbox](https://github.com/Orange-OpenSource/elpaaso-sandbox-service.git).

Credits to [spring security angular sample](https://github.com/dsyer/spring-security-angular.git).



## Tech specs of the sandbox service

### Overview

![Sandbox UI](http://g.gravizo.com/g?
@startuml
User -> SandboxUI: GET sandboxes/me;
User <-- SandboxUI: 302 location=uaa/login;
User -> uaa: ....;
User <-- uaa: 302: GET: SandboxUI/sandboxes/me?code=rezrze;
User -> SandboxUI : GET /sandboxes/me?code=rezrze;
SandboxUI -> uaa : GET /oauth/token?code=rezrze;
SandboxUI <-- uaa : user_token;
SandboxUI -> SandboxService: ...;
SandboxService -> CC_api: ...;
SandboxUI <-- SandboxService:"org_name","space_name","cc_api_url";
User <-- SandboxUI: "org_name","space_name","cc_api_url";
@enduml
)


# Build
To be able to build this project, you have to update your maven settings. You can use the one provided [here]()

## Running Tests

### Unit Tests
   * `mvn clean install`
### Integration Tests
   * `mvn clean install -PrunITs`

# Install
## Deploy on CloudFoundry
Please use manifest-reference.yml as template for your CF CLI manifest file.

```
$ mvn package
$ cf push sanbox-ui -p target/elpaaso-sandbox-ui-1.0-SNAPSHOT.jar -m manifest.yml
```

## Adding trusted self-signed root CA Certificate to the JVM truststore

If required, trusted Self-Signed Root CA Certificates can be added using TRUSTSTORE env property.

Here is a sample of TRUSTSTORE value :
```
{"certificates":["-----BEGIN CERTIFICATE-----\r\nMIIDhzCCAm+gAwIBAgIEYmqHlTANBgkqhkiG9w0BAQsFADB0MRAwDgYDVQQGEwdVbmtub3duMRAwDgYDVQQIEwdVbmtub3duMRAwDgYDVQQHEwdVbmtub3duMRYwFAYDVQQKEw13b3JsZCBjb21wYW55MRAwDgYDVQQLEwdVbmtub3duMRIwEAYDVQQDEwlqb2huIHBhdWwwHhcNMTUxMDI5MTQzNjEwWhcNMTYwMTI3MTQzNjEwWjB0MRAwDgYDVQQGEwdVbmtub3duMRAwDgYDVQQIEwdVbmtub3duMRAwDgYDVQQHEwdVbmtub3duMRYwFAYDVQQKEw13b3JsZCBjb21wYW55MRAwDgYDVQQLEwdVbmtub3duMRIwEAYDVQQDEwlqb2huIHBhdWwwggEiMA0GCSqGSIb3DQEBAQUAA4IBDwAwggEKAoIBAQC+UGMvPPnJowZcE5KI+FSyg8kCJtXLAK59e9JqMnbzzJUX3RQfT2BH08xN0z+cGdqOQNV7gvf2TCEJYOwFqB60JEhIgNPXGY/xOcFHY7qm+5MMXSvkxPw4yCEFj1vxfGY8kBKXWknhmE2eXG2S+bVSmwo9IBOHXgFzhOqmQly1uLP1x06NtpJV9lTWHBECWa7fIBmMUkXCrxdqVJb/OFjkjrmBhFjYhjTi+syqxO/blQiDDfGlOGTvf37ivcUtXQIvH2qce2vQuP+iZA/f5levMdySa6+Vdfdi114V83HjAsJGWStz0K2W5QRw/3ilw2D0hyCRKavOQBtG5m+o3v29AgMBAAGjITAfMB0GA1UdDgQWBBTe/Jg26TgrkhLLWBMH vinQzM4r0DANBgkqhkiG9w0BAQsFAAOCAQEAC7I3O4qNGF8KfWvJYXAcTW3cRTTzctEqaZvkR7biNoyhT6FykuCEgmrKId6HSaOCQEHp8h9/IHh/pwWFFNrIBCsPbyZBggTKC2Hj/dna/T7Ejoqsg3pXytDIlnDSPi3vsUcyLMpC1qZKRk5mYto6fxsb48IcFTyytQygcdvcYgGe5yQasYL4s55k9whwNbrzYHaWU3uNc3UVjyxkKAufrOQdWktg hIGlTE8Wm4gNNZx116hbCyFmK7UKOufRyW0pF1UcicfkaPs4Dd1ApU79uifvvN9PmjPkk88buTsMqzvkfey8HBaoZb9AiVYPn2if8HINvCOKaaLe7ixzgBGNkg==\r\n-----END CERTIFICATE-----"]}
```

Notice, that TRUSTSTORE value is a JSON String containing a tab of certificates.



# Running
## Pre-requisites
 * An OAuth2 identity provider (accessToken and userAuthorization)
 * A [Sandbox service](https://github.com/Orange-OpenSource/elpaaso-sandbox-service.git)





