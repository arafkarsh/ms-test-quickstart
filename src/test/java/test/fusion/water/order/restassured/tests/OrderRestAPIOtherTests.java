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
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.lessThan;

/**
 * REST Assured Examples based on BDD
 *.
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

    @BeforeAll
    public void setup() {
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());

        RestAssured.baseURI = "http://localhost:9081/api/v1";
        // RestAssured.baseURI = "http://localhost";
        // RestAssured.port = 9081;
        // RestAssured.rootPath = "/api/v1";
    }

    @DisplayName("1. Order Create - Other Tests")
    @Nested
    class createSecurityTests {
        @DisplayName("1.1 Check Response Time")
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
                    .time(lessThan(1L), SECONDS)
            ;
        }
    }


}
