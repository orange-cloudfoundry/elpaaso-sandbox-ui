/**
 * Copyright (C) 2015-2016 Orange
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.orange.clara.cloud.services.sandbox.ui.actuator;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * Created by O. Orand on 26/11/2015.
 */
@Configuration
@ConfigurationProperties(prefix = "sandbox.service")
public class SandboxServiceHealthProperties {
    String url;
    String healthContextPath;

    public String getUrl() {
        return url;
    }

    public SandboxServiceHealthProperties setUrl(String url) {
        this.url = url;
        return this;
    }

    public String getHealthContextPath() {
        return healthContextPath;
    }

    public SandboxServiceHealthProperties setHealthContextPath(String healthContextPath) {
        this.healthContextPath = healthContextPath;
        return this;
    }

    public URI getHealthEndpoint(){
        try {
            return new URI(url+healthContextPath);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
}
