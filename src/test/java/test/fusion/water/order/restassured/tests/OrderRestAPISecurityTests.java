/**
 * (C) Copyright 2023 Araf Karsh Hamid
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
package test.fusion.water.order.restassured.tests;

// JUnit 5

import io.fusion.water.order.domainLayer.models.OrderEntity;
import io.fusion.water.order.domainLayer.models.OrderStatus;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import test.fusion.water.order.junit5.annotations.tests.Critical;
import test.fusion.water.order.junit5.annotations.tests.Functional;
import test.fusion.water.order.junit5.annotations.tools.RestAssured5;
import test.fusion.water.order.junit5.extensions.TestTimeExtension;
import test.fusion.water.order.restassured.utils.OrderMockObjects;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * REST Assured Examples based on BDD
 *
 * It is a good practice to include security-related HTTP headers in all your REST API responses. They provide
 * another layer of security by enforcing certain browser behaviors. However, the necessity of each of these
 * headers can vary based on the specific nature of your application and the data it handles.
 *
 * Let's break down the different scenarios:
 *
 * X-Content-Type-Options: nosniff - This is generally a good practice to include in all responses to prevent the
 * browser from doing MIME-type sniffing which can lead to security vulnerabilities.
 *
 * X-XSS-Protection: 1; mode=block - This header is becoming less important as modern browsers have built-in XSS
 * protections and are deprecating the use of this header. A more robust solution for XSS protection is to use
 * Content-Security-Policy.
 *
 * Cache-Control: no-cache, no-store, max-age=0, must-revalidate - These directives should be used for responses
 * containing sensitive information that should not be cached by the browser or any intermediate proxies. For
 * non-sensitive information, it might be beneficial from a performance perspective to allow caching.
 *
 * X-Frame-Options: DENY - If your site should not be allowed to be embedded in an iframe (on any site, including
 * your own), then this is a good header to include to prevent clickjacking attacks.
 *
 * Content-Security-Policy - This header is quite useful and provides a lot of control over how and where resources
 * are loaded from, which can prevent a variety of attacks. However, it can also be complicated to set up properly
 * without breaking your site, and a misconfiguration can potentially introduce vulnerabilities rather than preventing
 * them.
 *
 * @author: Araf Karsh Hamid
 * @version:
 * @date:
 */

@RestAssured5()
@Critical()
@Functional()
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(TestTimeExtension.class)
public class OrderRestAPISecurityTests {

    private Response response = null;

    @BeforeAll
    public void setup() {
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());

        RestAssured.baseURI = "http://localhost:9081/api/v1";
        // RestAssured.baseURI = "http://localhost";
        // RestAssured.port = 9081;
        // RestAssured.rootPath = "/api/v1";
    }

    @DisplayName("1. Order Create - Security Tests")
    @Nested
    class createSecurityTests {
        @DisplayName("1.1 Check Content Type")
        @Test
        @Order(1)
        public void testContentType() {
            OrderEntity orderEntity = OrderMockObjects.mockGetOrderById("1234");
            given()
                    .contentType("application/json")
                    .body(orderEntity)
            .when()
                    .post("/order/")
            .then()
                    .assertThat()
                    .statusCode(200)
                    .header("Content-Type", equalTo("application/json"))
            ;
        }

        /**
         * Cache-Control: no-cache, no-store, max-age=0, must-revalidate: This header is used to specify directives
         * for caching mechanisms in both the browser and the server. no-cache means the cache should always validate
         * with the server before using a cached copy, no-store means absolutely no caching, max-age=0 means the
         * resource is already expired, and must-revalidate means the cache must verify the status of the stale
         * resources before using it. This prevents sensitive data from being stored in the cache
         */
        @DisplayName("1.2 Check Cache Control")
        @Test
        @Order(2)
        public void testCacheControl() {
            OrderEntity orderEntity = OrderMockObjects.mockGetOrderById("1234");
            given()
                    .contentType("application/json")
                    .body(orderEntity)
            .when()
                    .post("/order/")
            .then()
                    .assertThat()
                    .statusCode(200)
                    .header("Cache-Control", equalTo("no-cache, no-store, max-age=0, must-revalidate"))
            ;
        }

        /**
         * X-Frame-Options: DENY: This header is used to indicate whether a browser should be allowed to render a
         * page in a frame, iframe, embed or object. By setting this to DENY, you can avoid clickjacking attacks,
         * by ensuring your content is not embedded into other sites.
         */
        @DisplayName("1.3 Check X-Frame Option")
        @Test
        @Order(3)
        public void testNoFrame() {
            OrderEntity orderEntity = OrderMockObjects.mockGetOrderById("1234");
            given()
                    .contentType("application/json")
                    .body(orderEntity)
            .when()
                    .post("/order/")
            .then()
                    .assertThat()
                    .statusCode(200)
                    .header("X-Frame-Options", equalTo("DENY"))
            ;
        }

        /**
         * This header tells the browser to not try to interpret the content of a file differently from the declared
         * Content-Type. This prevents "mime" based attacks, where an innocuous file with a safe file extension
         * (.txt, .jpg) is interpreted as a dangerous type based on its content (.html, .js), potentially leading
         * to code execution.
         */
        @DisplayName("1.4 Check X-Content-Type-Options")
        @Test
        @Order(4)
        public void testContentTypeOptions() {
            OrderEntity orderEntity = OrderMockObjects.mockGetOrderById("1234");
            given()
                    .contentType("application/json")
                    .body(orderEntity)
            .when()
                    .post("/order/")
            .then()
                    .assertThat()
                    .statusCode(200)
                    .header("X-Content-Type-Options", equalTo("nosniff"))
            ;
        }

        /**
         * X-XSS-Protection: 1; mode=block: This header is a feature of Internet Explorer, Chrome and Safari that
         * stops pages from loading when they detect reflected cross-site scripting (XSS) attacks. The mode=block
         * part of the header tells the browser to block the entire page if an attack is detected, rather than
         * sanitizing the page.
         *
         * 0: Disables the XSS Auditor
         * 1: Enables the XSS Auditor and sanitizes the page if a XSS attack is detected, i.e., removes the unsafe parts.
         *
         * 1; mode=block: Enables the XSS Auditor and rather than sanitizing the page, the browser will prevent
         * rendering of the page if an attack is detected.
         *
         * Therefore, X-XSS-Protection: 1; mode=block means that the XSS Auditor is turned on and the browser should
         * block the entire page if a potential XSS attack is detected.
         */
        @DisplayName("1.5 Check X-XSS-Protection")
        @Test
        @Order(5)
        public void testXXSSProtection() {
            OrderEntity orderEntity = OrderMockObjects.mockGetOrderById("1234");
            given()
                    .contentType("application/json")
                    .body(orderEntity)
            .when()
                    .post("/order/")
            .then()
                    .assertThat()
                    .statusCode(200)
                    .header("X-XSS-Protection", equalTo("1; mode=block"))
            ;
        }

        /**
         * Content-Security-Policy - This header is quite useful and provides a lot of control over how and where
         * resources are loaded from, which can prevent a variety of attacks. However, it can also be complicated to
         * set up properly without breaking your site, and a misconfiguration can potentially introduce vulnerabilities
         * rather than preventing them.
         *
         * Here's a breakdown of the given CSP directive:
         *
         * default-src 'self': Only resources from the same origin as the document are loaded.
         * script-src 'self' *.localhost: Scripts can be loaded from the same origin and any subdomain of localhost.
         * object-src 'self' *.localhost: Objects can be loaded from the same origin and any subdomain of localhost.
         * img-src 'self': Images can be loaded from the same origin.
         * media-src 'self': Media files can be loaded from the same origin.
         * frame-src 'self': Frames can be loaded from the same origin.
         * font-src 'self': Fonts can be loaded from the same origin.
         * connect-src 'self': Connections can be made (via XHR, WebSockets, and EventSource) to the same origin.
         */
        @DisplayName("1.6 Check Content Security Policy")
        @Test
        @Order(6)
        public void testContentSecurityPolicy() {
            OrderEntity orderEntity = OrderMockObjects.mockGetOrderById("1234");
            given()
                    .contentType("application/json")
                    .body(orderEntity)
            .when()
                    .post("/order/")
            .then()
                    .assertThat()
                    .statusCode(200)
                    .header("Content-Security-Policy", equalTo(
                            "default-src 'self'; script-src 'self' *.localhost; object-src 'self' *.localhost;"
                            +" img-src 'self'; media-src 'self'; frame-src 'self'; font-src 'self'; connect-src 'self'"))
            ;
        }
    }

    @DisplayName("2. Order Retrieve - Security Tests")
    @Nested
    class getSecurityTests {
        /**
         * Content Type
         */
        @DisplayName("2.1 Check Content Type")
        @Test
        @Order(1)
        public void getOrderById() {
            given()
                    .pathParam("orderId", "1234")
            .when()
                    .get("/order/{orderId}/")
            .then()
                    .statusCode(200)
                    .assertThat()
                    .header("Content-Type", equalTo("application/json"));
        }

        /**
         * Cache-Control:
         */
        @DisplayName("2.2 Check Cache Control")
        @Test
        @Order(2)
        public void testCacheControl() {
            OrderEntity orderEntity = OrderMockObjects.mockGetOrderById("1234");
            given()
                    .pathParam("orderId", "1234")
            .when()
                    .get("/order/{orderId}/")
            .then()
                    .assertThat()
                    .statusCode(200)
                    .header("Cache-Control", equalTo("no-cache, no-store, max-age=0, must-revalidate"))
            ;
        }

        /**
         * X-Frame-Options:
         */
        @DisplayName("2.3 Check X-Frame Option")
        @Test
        @Order(3)
        public void testNoFrame() {
            OrderEntity orderEntity = OrderMockObjects.mockGetOrderById("1234");
            given()
                    .pathParam("orderId", "1234")
            .when()
                    .get("/order/{orderId}/")
            .then()
                    .assertThat()
                    .statusCode(200)
                    .header("X-Frame-Options", equalTo("DENY"))
            ;
        }

        /**
         * X-Content-Type-Options.
         */
        @DisplayName("2.4 Check X-Content-Type-Options")
        @Test
        @Order(4)
        public void testContentTypeOptions() {
            OrderEntity orderEntity = OrderMockObjects.mockGetOrderById("1234");
            given()
                    .pathParam("orderId", "1234")
            .when()
                    .get("/order/{orderId}/")
            .then()
                    .assertThat()
                    .statusCode(200)
                    .header("X-Content-Type-Options", equalTo("nosniff"))
            ;
        }

        /**
         * X-XSS-Protection:
         */
        @DisplayName("2.5 Check X-XSS-Protection")
        @Test
        @Order(5)
        public void testXXSSProtection() {
            OrderEntity orderEntity = OrderMockObjects.mockGetOrderById("1234");
            given()
                    .pathParam("orderId", "1234")
            .when()
                    .get("/order/{orderId}/")
            .then()
                    .assertThat()
                    .statusCode(200)
                    .header("X-XSS-Protection", equalTo("1; mode=block"))
            ;
        }

        /**
         * Content-Security-Policy
         */
        @DisplayName("2.6 Check Content Security Policy")
        @Test
        @Order(6)
        public void testContentSecurityPolicy() {
            OrderEntity orderEntity = OrderMockObjects.mockGetOrderById("1234");
            given()
                    .pathParam("orderId", "1234")
            .when()
                    .get("/order/{orderId}/")
            .then()
                    .assertThat()
                    .statusCode(200)
                    .header("Content-Security-Policy", equalTo(
                            "default-src 'self'; script-src 'self' *.localhost; object-src 'self' *.localhost;"
                                    +" img-src 'self'; media-src 'self'; frame-src 'self'; font-src 'self'; connect-src 'self'"))
            ;
        }
    }

}
