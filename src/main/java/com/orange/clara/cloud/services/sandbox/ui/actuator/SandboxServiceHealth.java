/**
 * Copyright (C) 2015 Orange
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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

/**
 * Created by O. Orand on 26/11/2015.
 */
@Component(value = "sandboxService")
public class SandboxServiceHealth implements HealthIndicator {
    private static final Logger logger = LoggerFactory.getLogger(SandboxServiceHealth.class);

    private RestTemplate restTemplate;

    private SandboxServiceHealthProperties sandboxServiceHealthProperties;

    public SandboxServiceHealth() {
        this.restTemplate = new RestTemplate();
    }

    public SandboxServiceHealth setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
        return this;
    }

    @Autowired
    public SandboxServiceHealth setSandboxServiceHealthProperties(SandboxServiceHealthProperties sandboxServiceHealthProperties) {
        this.sandboxServiceHealthProperties = sandboxServiceHealthProperties;
        return this;
    }

    @Override
    public Health health() {
        Health.Builder builder = new Health.Builder();
        try {
            HttpStatus httpStatus = check();
            builder.up();
        }catch (Exception ex) {
            builder.down(ex);
        }
        return builder.build();
    }

    private HttpStatus check() {
        ResponseEntity<Map> sandboxServiceResponse = restTemplate.getForEntity(sandboxServiceHealthProperties.getHealthEndpoint(), Map.class);
        return sandboxServiceResponse.getStatusCode();
    }
}