/**
 * (C) Copyright 2021 Araf Karsh Hamid
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.fusion.water.order.adapters.security;

import io.fusion.water.order.server.ServiceConfiguration;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.firewall.StrictHttpFirewall;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;

/**
 *
 * @author: Araf Karsh Hamid
 * @version:
 * @date:
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration {
    @Autowired
    private ServiceConfiguration serviceConfig;

    /**
     * Configures the security filter chain for HTTP requests, applying various security measures
     * such as request authorization, CSRF protection, and content security policies.
     *
     * @param http the {@link HttpSecurity} object to configure HTTP security for the application
     * @return the constructed {@link SecurityFilterChain}
     * @throws Exception if there is a problem during configuration
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // Forces All Request to be Secured (HTTPS)
        // http.requiresChannel().anyRequest().requiresSecure();
        authorizeRequests(http);              // Set Authorization Policies
        // authorizeRequestsNew(http);         // Set Authorization Policies
        csrfProtection(http);                   // Set CSRF Protection
        xFrameProtection(http);               // Set X-Frame Protection
        contentSecurityPolicy(http);          // Set Content Security Policy
        return http.build();                     // Build Security Filter Chain
    }

    /**
     * Configures HTTP security to authorize requests based on the API documentation path.
     * It permits all requests matching the API documentation path and redirects unauthorized
     * access attempts to a custom access denied page.
     *
     * @param http the {@link HttpSecurity} object to configure HTTP security for the application
     * @throws Exception if there is a problem during configuration
     */
    private void authorizeRequests(HttpSecurity http) throws Exception {
        String apiPath = serviceConfig.getApiDocPath();
        http.authorizeRequests()
                .requestMatchers(apiPath + "/**")
                .permitAll()
                .and()
                // This configures exception handling, specifically specifying that when a user tries to access a page
                // they're not authorized to view, they're redirected to "/403" (typically an "Access Denied" page).
                .exceptionHandling(exceptionHandling -> exceptionHandling
                        .accessDeniedPage("/403"))
                // Make sure to add stateless session management since it's a microservice
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS));
    }

    /**
     * Configures HTTP security to authorize requests based on the API documentation path.
     * It permits all requests matching the API documentation path and redirects unauthorized
     * access attempts to a custom access denied page.
     *
     * @param http the {@link HttpSecurity} object to configure HTTP security for the application
     * @throws Exception if there is a problem during configuration
     */
    private void authorizeRequestsNew(HttpSecurity http) throws Exception {
        String apiPath = serviceConfig.getApiDocPath();
        http.authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(apiPath + "/**")
                        .permitAll()
                        // Require authentication for any other requests
                        // .anyRequest().authenticated()
                )
                // This configures exception handling, specifically specifying that when a user tries to access a page
                // they're not authorized to view, they're redirected to "/403" (typically an "Access Denied" page).
                .exceptionHandling(exceptionHandling -> exceptionHandling
                        .accessDeniedPage("/403"))
                // Make sure to add stateless session management since it's a microservice
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS));
    }

    /**
     * Configures Cross-Site Request Forgery (CSRF) protection for HTTP security. This method typically
     * enables CSRF protection using a cookie-based CSRF token repository, making CSRF tokens accessible
     * to client-side scripting. Note that CSRF protection is disabled for local testing within this method.
     *
     * @param http the {@link HttpSecurity} object to configure HTTP security for the application
     * @throws Exception if there is a problem during configuration
     */
    private void csrfProtection(HttpSecurity http) throws Exception {
        // Enable CSRF Protection
        // This line configures the Cross-Site Request Forgery (CSRF) protection, using a Cookie-based CSRF token
        // repository. This means that CSRF tokens will be stored in cookies. The withHttpOnlyFalse() method makes
        // these cookies accessible to client-side scripting, which is typically necessary for applications that use
        // a JavaScript-based frontend.
        /**
         http
         .csrf()
         .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
         // Add the above Only for testing in Swagger
         .and()
         .addFilterAfter(new CsrfTokenResponseHeaderBindingFilter(), CsrfFilter.class);
         */
        // Disable for Local Testing
        http.csrf(CsrfConfigurer::disable);
    }

    /**
     * Configures HTTP security to protect against "clickjacking" attacks by setting the "X-Frame-Options" header
     * and adding a content security policy.
     *
     * @param http the {@link HttpSecurity} object to configure HTTP security for the application
     * @throws Exception if there is a problem during configuration
     */
    private void xFrameProtection(HttpSecurity http) throws Exception {
        // X-Frame-Options is a security header that is intended to protect your website against "clickjacking" attacks.
        // Clickjacking is a malicious technique of tricking web users into revealing confidential information or taking
        // control of their interaction with the website, by loading your website in an iframe of another website and
        // then overlaying it with additional content.
        http.headers(headers -> headers
                .frameOptions(frameOptions -> frameOptions.deny())
                .contentSecurityPolicy(csp -> csp
                        .policyDirectives("default-src 'self'; frame-ancestors 'none'")
                )
        );
    }

    /**
     * Configures the Content Security Policy (CSP) for HTTP security.
     * The CSP is a security measure that helps prevent a range of attacks,
     * including Cross-Site Scripting (XSS) and data injection attacks.
     * It specifies which domains the browser should consider to be valid sources of executable scripts
     * and other resources.
     *
     * @param http the {@link HttpSecurity} object to configure HTTP security for the application
     * @throws Exception if there is a problem during configuration
     */
    private void contentSecurityPolicy(HttpSecurity http) throws Exception {
        String hostName = serviceConfig.getServerHost();
        // Content Security Policy
        // The last part sets the Content Security Policy (CSP). This is a security measure that helps prevent a range
        // of attacks, including Cross-Site Scripting (XSS) and data injection attacks. It does this by specifying which
        // domains the browser should consider to be valid sources of executable scripts. In this case, scripts
        // (script-src) and objects (object-src) are only allowed from the same origin ('self') or from a subdomain of
        // the specified host name.
        http.headers(headers -> headers
                .contentSecurityPolicy(csp -> csp
                        .policyDirectives("default-src 'self'; " +
                                "script-src 'self' *." + hostName + "; " +
                                "object-src 'self' *." + hostName + "; " +
                                "img-src 'self'; " +
                                "media-src 'self'; " +
                                "frame-src 'self'; " +
                                "font-src 'self'; " +
                                "connect-src 'self'")
                )
        );
    }

    /**
     * Customizes web security to ignore security checks for specified static resource paths.
     *
     * @return a configured {@link WebSecurityCustomizer} that ignores security for "/images/**",
     * "/js/**", and "/webjars/**" paths.
     */
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers("/images/**", "/js/**", "/webjars/**");
    }

    /**
     * Handles Malicious URI Path (handles special characters and other things
     * @return
     */
    @Bean
    public StrictHttpFirewall httpFirewall() {
        StrictHttpFirewall firewall = new StrictHttpFirewall();
        firewall.setAllowedHttpMethods(Arrays.asList("GET","POST", "PUT", "DELETE"));
        return firewall;
    }

    /**
     * ONLY For Local Testing with Custom CSRF Headers in Swagger APi Docs
     */
    private static class CsrfTokenResponseHeaderBindingFilter extends OncePerRequestFilter {
        @Override
        protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
                throws ServletException, IOException {
            CsrfToken token = (CsrfToken) request.getAttribute("_csrf");
            response.setHeader("X-CSRF-HEADER", token.getHeaderName());
            response.setHeader("X-CSRF-PARAM", token.getParameterName());
            response.setHeader("X-CSRF-TOKEN", token.getToken());
            filterChain.doFilter(request, response);
        }
    }
}

