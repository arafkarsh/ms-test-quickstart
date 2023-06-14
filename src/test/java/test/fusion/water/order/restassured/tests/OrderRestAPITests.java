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
 * Staticially Import Classes from the following packages
 *
 * io.restassured.RestAssured.*
 * io.restassured.matcher.RestAssuredMatchers.*
 * org.hamcrest.Matchers.*
 */

/**
 * REST Assured Examples based on BDD
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
public class OrderRestAPITests {

    private Response response = null;

    @BeforeAll
    public void setup() {
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());

        RestAssured.baseURI = "http://localhost:9081/api/v1";
        // RestAssured.baseURI = "http://localhost";
        // RestAssured.port = 9081;
        // RestAssured.rootPath = "/api/v1";

    }

    /**
     * Create a Mock Order with Order ID = 1234
     * 1. Send the Order as a POST Request
     * 2. Check the Return HTTP Status = 200
     */
    @DisplayName("1. POST: Create the Order")
    @Test
    @Order(1)
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

    /**
     * Test the OrderService.getOrderById GET Method Call
     * Parameter to the call is Order Id (String)
     *
     * Following Conditions are tested in this test case
     * 1. Checks if the service returned the right order (checks Order ID with the input)
     * 2. Check if minimum One Order Item is available
     * 3. Check if the Customer, Customer Id, First Name and Last Names are Not Null
     * 4. Check if the Shipping Address, Street, City, Zip Code are Not Null.
     * 5. Total Value is greater than Zero
     * 6. Payment Details are available
     * 7. Payment Order Value = Total Order Value
     */
    @DisplayName("2. GET: Fetch the Order")
    @Test
    @Order(2)
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
                .body("customer.customerId", notNullValue())
                .body("customer.firstName", notNullValue())
                .body("customer.lastName", notNullValue())
                .body("shippingAddress", notNullValue())
                .body("shippingAddress.streetName", notNullValue())
                .body("shippingAddress.city", notNullValue())
                .body("shippingAddress.state", notNullValue())
                .body("shippingAddress.zipCode", notNullValue())
                .body("totalValue", greaterThan(0.0f))
                .body("paymentDetails", notNullValue())
                .extract().response(); // Extract the whole response to a Response object

        float orderValue = response.path("paymentDetails.orderValue");
        float totalValue = response.path("totalValue");

        assertEquals(orderValue, totalValue, 0.01f);  // comparing two values
    }

    /**
     * 7. Payment Order Value = Total Order Value
     */
    @DisplayName("3. Check the Order Value Against Payment")
    @Test
    @Order(3)
    public void checkOrderValueAgainstPayment() {
        float orderValue = response.path("paymentDetails.orderValue");
        float totalValue = response.path("totalValue");
        assertEquals(orderValue, totalValue, 0.01f);  // comparing two values
    }

    /**
     * Update the Order ID = 1234  with Status = PAYMENT EXPECTED
     */
    @DisplayName("4. PUT: Update Order Status for Waiting for Payment")
    @Test
    @Order(4)
    public void testPutOrderStatus() {
        String orderId = "1234";  // Replace with actual order ID
        String statusId = OrderStatus.PAYMENT_EXPECTED.name();  // Replace with actual status ID

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

    /**
     * Cancel the Order ID = 1234
     */
    @DisplayName("5. DELETE: Cancel the Order Status on Order ID")
    @Test
    @Order(5)
    public void testCancelOrder() {
        String orderId = "1234";  // Replace with actual order ID
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