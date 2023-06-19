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
package test.fusion.water.order.spock.restassured

import io.fusion.water.order.OrderApplication

// Custom
import io.fusion.water.order.domainLayer.models.OrderStatus
import test.fusion.water.order.utils.OrderMockObjects
// REST Assured
import io.restassured.RestAssured
import io.restassured.filter.log.RequestLoggingFilter
import io.restassured.filter.log.ResponseLoggingFilter
// Spring
import org.springframework.boot.test.context.SpringBootTest
// Spock
import spock.lang.Specification

/**
 * REST Assured Examples based on BDD
 *
 * These tests runs against the SpringBoot App Running outside the Test Environment
 *
 * @author: Araf Karsh Hamid
 * @version:
 * @date:
 */
@SpringBootTest(classes=OrderApplication.class)
class OrderAPISpec extends Specification {


    def setupSpec() {
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter())
        RestAssured.baseURI = "http://localhost:9081/api/v1"
    }

    def "POST: Create the Order"() {
        given: "The Order Entity is created"
            def orderEntity = OrderMockObjects.mockGetOrderById("1234")

        when: "The Order is saved in the Server"
            def response = RestAssured.given()
                    .contentType("application/json")
                    .body(orderEntity)
                    .post("/order/")

        then: "You get Status Code = 200"
            response.then().statusCode(200)
    }

    def "GET: Fetch the Order"() {
        given:
        def orderId = "1234"

        when:
        def response = RestAssured.given()
                .pathParam("orderId", orderId)
                .get("/order/{orderId}/")
                .then()
                .statusCode(200)
                .extract().response()
        then:
            response.jsonPath().get("orderId") == orderId
            response.jsonPath().getList("orderItems").size() > 0
            response.jsonPath().get("customer") != null
            response.jsonPath().get("customer.customerId") != null
            response.jsonPath().get("customer.firstName") != null
            response.jsonPath().get("customer.lastName") != null
            response.jsonPath().get("shippingAddress") != null
            response.jsonPath().get("shippingAddress.streetName") != null
            response.jsonPath().get("shippingAddress.city") != null
            response.jsonPath().get("shippingAddress.state") != null
            response.jsonPath().get("shippingAddress.zipCode") != null
            response.jsonPath().getFloat("totalValue") > 0.0f
            response.jsonPath().get("paymentDetails") != null

        and:
            def orderValue = response.path("paymentDetails.orderValue")
            def totalValue = response.path("totalValue")
            orderValue == totalValue
    }

    def "PUT: Update Order Status for Waiting for Payment"() {
        given: "The Order ID and Status Id available"
            String orderId = "1234"
            String statusId = OrderStatus.PAYMENT_EXPECTED.name()

        when: "The status is updated in the server"
            def response = RestAssured.given()
                .contentType("application/json")
                .pathParam("orderId", orderId)
                .pathParam("statusId", statusId)
                .put("/order/{orderId}/status/{statusId}/")

        then: "The response code will be 200"
            response.then()
                    .statusCode(200)
                    // .body("orderStatus", equalTo(statusId))
    }

    def "DELETE: Cancel the Order Status on Order ID"() {
        given: "The Order ID is available"
            String orderId = "1234"

        when:
            def response = RestAssured.given()
                .contentType("application/json")
                .pathParam("orderId", orderId)
                .delete("/order/cancel/{orderId}/")

        then:
            response.then()
                    .statusCode(200)

    }
}
