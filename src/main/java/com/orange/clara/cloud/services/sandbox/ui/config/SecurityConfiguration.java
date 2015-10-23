
/*
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
 *
 */

package com.orange.clara.cloud.services.sandbox.ui.config;


/**
 * Created by sbortolussi on 16/09/2015.
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.autoconfigure.ManagementServerProperties;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.WebUtils;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@EnableWebSecurity(debug = false)
//@EnableOAuth2Sso
public class SecurityConfiguration /*extends WebSecurityConfigurerAdapter */{
    private static final Logger LOGGER = LoggerFactory.getLogger(SecurityConfiguration.class);

    @Autowired
    SecurityProperties securityProperties;

    @Autowired
    ManagementServerProperties managementServerProperties;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {

        String username = securityProperties.getUser().getName();
        String password = securityProperties.getUser().getPassword();
        String adminRole = managementServerProperties.getSecurity().getRole();
        LOGGER.info("Authorizing user {} with role {} ",username,adminRole);
        auth.inMemoryAuthentication().withUser(username).password(password).roles(adminRole);
    }

    @Configuration
    @Order(1)
    public static class ActuatorBasicAuthConfig extends WebSecurityConfigurerAdapter {
        private static final Logger LOGGER = LoggerFactory.getLogger(ActuatorBasicAuthConfig.class);

        @Autowired
        SecurityProperties securityProperties;

        @Autowired
        ManagementServerProperties managementServerProperties;

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            String adminRole = managementServerProperties.getSecurity().getRole();
            String managementContextPath = managementServerProperties.getContextPath();
            LOGGER.info("Enabling basic auth security for management path {} with role {}",managementContextPath,adminRole);
            http
                    .antMatcher(managementContextPath + "/**")
                    .authorizeRequests()
                    .anyRequest()
                    .hasRole("ADMIN")
                    .and()
                    .formLogin().disable()
                    .csrf().disable()
                    .httpBasic();
//                .antMatcher("/**").authorizeRequests()
//                    .anyRequest()

//                .antMatchers(managementContextPath + "/health", managementContextPath + "/info").permitAll()
//                .antMatchers(managementContextPath + "/**").hasRole(adminRole)
//                .and()
//                .formLogin().disable()
//                .csrf().disable()
//                .httpBasic();
        }
    }

    @Configuration
    @EnableOAuth2Sso
    public static class OAuth2SecurityConfig extends WebSecurityConfigurerAdapter {
        private static final Logger LOGGER = LoggerFactory.getLogger(OAuth2SecurityConfig.class);

        @Autowired
        SecurityProperties securityProperties;

        @Override
        public void configure(HttpSecurity http) throws Exception {
            if (securityProperties.isRequireSsl()) {
                LOGGER.info("SSL enabled in springboot config, cannot access this app using http");
                http.requiresChannel().anyRequest().requiresSecure();
            }
            if (securityProperties.isEnableCsrf()) {
                LOGGER.info("CSRF enabled in springboot config");
                http.csrf()
                        .csrfTokenRepository(csrfTokenRepository())
                        .and()
                        .addFilterAfter(csrfHeaderFilter(), CsrfFilter.class);
            }
            http
                    .antMatcher("/**").authorizeRequests()
                    .anyRequest().authenticated()
                    .and()
                    .formLogin()
                    .and()
                    .sessionManagement().sessionCreationPolicy(securityProperties.getSessions());
        }

        private Filter csrfHeaderFilter() {
            return new OncePerRequestFilter() {
                @Override
                protected void doFilterInternal(HttpServletRequest request,
                                                HttpServletResponse response, FilterChain filterChain)
                        throws ServletException, IOException {
                    CsrfToken csrf = (CsrfToken) request.getAttribute(CsrfToken.class
                            .getName());
                    if (csrf != null) {
                        Cookie cookie = WebUtils.getCookie(request, "XSRF-TOKEN");
                        String token = csrf.getToken();
                        if (cookie == null || token != null
                                && !token.equals(cookie.getValue())) {
                            cookie = new Cookie("XSRF-TOKEN", token);
                            cookie.setPath("/");
                            response.addCookie(cookie);
                        }
                    }
                    filterChain.doFilter(request, response);
                }
            };
        }

        private CsrfTokenRepository csrfTokenRepository() {
            HttpSessionCsrfTokenRepository repository = new HttpSessionCsrfTokenRepository();
            repository.setHeaderName("X-XSRF-TOKEN");
            return repository;
        }
    }
}