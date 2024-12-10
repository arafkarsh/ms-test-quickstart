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
package test.fusion.water.order.testng.restassured5.tests;

/**
 * @author: Araf Karsh Hamid
 * @version:
 * @date:
 */
import io.fusion.water.order.OrderApplication;
import io.fusion.water.order.domainLayer.models.OrderEntity;
import io.fusion.water.order.domainLayer.models.OrderStatus;
import io.restassured.RestAssured;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
// import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;

import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.testng.Assert.assertEquals;

import test.fusion.water.order.utils.OrderMockObjects;

@SpringBootTest(classes={OrderApplication.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EmbeddedServerTests extends AbstractTestNGSpringContextTests {

    @LocalServerPort
    private int port;

    private Response response = null;

    @BeforeClass
    public void setup() {
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
        RestAssured.baseURI = "http://localhost:" + port + "/api/v1";
    }

    @Test
    public void testPostOrder() {
        OrderEntity orderEntity = OrderMockObjects.mockGetOrderById("1234");
        given()
                .contentType("application/json")
                .body(orderEntity)
                .when()
                .post("/order/")
                .then()
                .assertThat()
                .statusCode(200);
    }

    @Test(dependsOnMethods = "testPostOrder")
    public void getOrderById() {
        response =
                given()
                        .pathParam("orderId", "1234")
                        .when()
                        .get("/order/{orderId}/")
                        .then()
                        .statusCode(200)
                        .assertThat()
                        .body("orderId", equalTo("1234"))
                        .body("orderItems.size()", greaterThan(0))
                        .body("customer", notNullValue())
                        .root("customer")
                        .body("customerId", notNullValue())
                        .body("firstName", notNullValue())
                        .body("lastName", notNullValue())
                        .rootPath("") // Clear root
                        .body("shippingAddress", notNullValue())
                        .root("shippingAddress")
                        .body("streetName", notNullValue())
                        .body("city", notNullValue())
                        .body("state", notNullValue())
                        .body("zipCode", notNullValue())
                        .rootPath("") // Clear root
                        .body("totalValue", greaterThan(0.0f))
                        .body("paymentDetails", notNullValue())
                        .extract().response();

        float orderValue = response.path("paymentDetails.orderValue");
        float totalValue = response.path("totalValue");

        assertEquals(orderValue, totalValue, 0.01f);  // comparing two values
    }

    @Test(dependsOnMethods = "getOrderById")
    public void checkOrderValueAgainstPayment() {
        float orderValue = response.path("paymentDetails.orderValue");
        float totalValue = response.path("totalValue");
        assertEquals(orderValue, totalValue, 0.01f);  // comparing two values
    }

    @Test(dependsOnMethods = "checkOrderValueAgainstPayment")
    public void testPutOrderStatus() {
        String orderId = "1234";
        String statusId = OrderStatus.PAYMENT_EXPECTED.name();

        given()
                .contentType("application/json")
                .pathParam("orderId", orderId)
                .pathParam("statusId", statusId)
                .when()
                .put("/order/{orderId}/status/{statusId}/")
                .then()
                .assertThat()
                .statusCode(200)
                .body("orderStatus", equalTo(statusId));
    }

    @Test(dependsOnMethods = "testPutOrderStatus")
    public void testCancelOrder() {
        String orderId = "1234";
        given()
                .contentType("application/json")
                .pathParam("orderId", orderId)
                .when()
                .delete("/order/cancel/{orderId}/")
                .then()
                .assertThat()
                .statusCode(200);
    }
}
