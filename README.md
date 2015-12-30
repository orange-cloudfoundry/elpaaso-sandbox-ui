# elpaaso-sandbox-ui [![Build Status](https://travis-ci.org/Orange-OpenSource/elpaaso-sandbox-service.svg?branch=master)](https://travis-ci.org/Orange-OpenSource/elpaaso-sandbox-ui)
[![Apache Version 2 Licence](http://img.shields.io/:license-Apache%20v2-blue.svg)](LICENSE)
[![Bintray](https://www.bintray.com/docs/images/bintray_badge_color.png)](https://bintray.com/elpaaso/maven/elpaaso-sandbox-ui/view?source=watch)
[![JCenter](https://img.shields.io/badge/JCenter-available-blue.svg)](https://bintray.com/bintray/jcenter?filterByPkgName=elpaaso-sandbox-ui)
[![Join the chat at https://gitter.im/Orange-OpenSource/elpaaso](https://img.shields.io/badge/gitter-join%20chat%20→-brightgreen.svg)](https://gitter.im/Orange-OpenSource/elpaaso?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge&utm_content=badge)

## Overview 

elpaaso sandbox ui is an Angular and spring Oauth2 security based GUI to create a sandbox space leveraging  [sandbox-service](https://github.com/Orange-OpenSource/elpaaso-sandbox-service).

Following the UAA login screen with user consent, then the following screen displays the created sandbox space details: 
![Sandbox-ui screenshot](https://cloud.githubusercontent.com/assets/4748380/12049351/4776fcf0-aee6-11e5-83ef-893b26ac745c.png)

Credits to [spring security angular sample](https://github.com/dsyer/spring-security-angular.git).

## Tech specs of the sandbox service

### Overview

![Sandbox UI](http://g.gravizo.com/g?
@startuml;
User -> SandboxUI: GET sandboxes/me;
User <-- SandboxUI: 302 location=uaa/oauth/authorize;
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

If required, trusted Self-Signed Root CA Certificate can be added using <i>TRUSTED_CA_CERTIFICATE</i> env property.

Here is snippet of manifest.yml :
```
applications:
- name: elpaaso-sandbox-ui
  env:
     TRUSTED_CA_CERTIFICATE: |
          -----BEGIN CERTIFICATE-----
                        XXXXXX
          -----END CERTIFICATE-----
```


# Running
## Pre-requisites
 * An OAuth2 identity provider (accessToken and userAuthorization)
 * A [Sandbox service](https://github.com/Orange-OpenSource/elpaaso-sandbox-service.git)





