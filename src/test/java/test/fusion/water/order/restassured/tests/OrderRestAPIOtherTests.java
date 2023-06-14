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
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import test.fusion.water.order.junit5.annotations.tests.Critical;
import test.fusion.water.order.junit5.annotations.tests.Functional;
import test.fusion.water.order.junit5.annotations.tools.RestAssured5;
import test.fusion.water.order.junit5.extensions.TestTimeExtension;
import test.fusion.water.order.restassured.utils.OrderMockObjects;

import static io.restassured.RestAssured.given;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * REST Assured Examples based on BDD
 *.
 * 1. Test The Response Time
 * 2. Pass Common Specs Across the test cases
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
public class OrderRestAPIOtherTests {

    private Response response = null;

    private static RequestSpecification requestSpec;

    @BeforeAll
    public void setup() {
        /**
         * RequestLoggingFilter(): This filter is used to log the details of the request that you are sending.
         * The details include the request method, the URI, the headers, parameters, and the body.
         *
         * ResponseLoggingFilter(): This filter is used to log the details of the response that you receive.
         * The details include the status line, headers, and the body.
         */
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());

        // Set the URL for Testing
        RestAssured.baseURI = "http://localhost:9081/api/v1";

        // Set the Common Specs Across test cases.
        RequestSpecBuilder builder = new RequestSpecBuilder();
        builder.addHeader("Content-Type", "application/json");  // Content Type
        builder.addHeader("Authorization", "jwt1");             // Security Tokens
        builder.addHeader("Refresh-Token", "jwt2");             // Security Tokens
        requestSpec = builder.build();
    }

    @DisplayName("1. Order Create - Other Tests")
    @Nested
    class createOtherFeatureTests {
        @DisplayName("1.1 Performance Test: Response Time")
        @Test
        @Order(1)
        public void testContentType() {
            OrderEntity orderEntity = OrderMockObjects.mockGetOrderById("1234");
            given()
                    .spec(requestSpec)
                    .body(orderEntity)
            .when()
                    .post("/order/")
            .then()
                    .assertThat()
                    .statusCode(200)
                    .time(lessThan(2L), SECONDS)
            ;
        }

        @DisplayName("1.2. Compare Order Value and Payment Amount")
        @Test
        @Order(2)
        public void getOrderById() {
            response =
                    given()
                            .spec(requestSpec)
                            .pathParam("orderId", "1234")
                    .when()
                            .get("/order/{orderId}/")
                    .then()
                            .statusCode(200)
                            .extract().response(); // Extract the whole response to a Response object

            float orderValue = response.path("paymentDetails.orderValue");
            float totalValue = response.path("totalValue");

            assertEquals(orderValue, totalValue, 0.01f);  // comparing two values
        }
    }


}
