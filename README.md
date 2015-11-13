[![Build Status](https://travis-ci.org/Orange-OpenSource/elpaaso-sandbox-service.svg?branch=master)](https://travis-ci.org/Orange-OpenSource/elpaaso-sandbox-ui)

# elpaaso-sandbox-ui
[![Build Status](https://travis-ci.org/Orange-OpenSource/elpaaso-sandbox-ui.svg?branch=master)](https://travis-ci.org/Orange-OpenSource/elpaaso-sandbox-ui)
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
Please use manifest-reference as template.

# Running
## Pre-requisites
 * An OAuth2 identity provider (accessToken and userAuthorization)
 * A [Sandbox service](https://github.com/Orange-OpenSource/elpaaso-sandbox-service.git)





