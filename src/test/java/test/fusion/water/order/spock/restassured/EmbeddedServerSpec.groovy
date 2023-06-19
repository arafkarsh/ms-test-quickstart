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

// Custom
import io.fusion.water.order.OrderApplication;
import io.fusion.water.order.domainLayer.models.OrderStatus;
import test.fusion.water.order.utils.OrderMockObjects;


// Spring
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.web.server.LocalServerPort
// Spock
import spock.lang.*
// REST Assured
import io.restassured.RestAssured
import io.restassured.filter.log.RequestLoggingFilter
import io.restassured.filter.log.ResponseLoggingFilter

/**
 * REST Assured Examples based on BDD
 *
 * These tests runs against the SpringBoot App Running in an embedded mode,
 *
 * @author: Araf Karsh Hamid
 * @version:
 * @date:
 */
@SpringBootTest(classes=OrderApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class EmbeddedServerSpec extends Specification {

    @Shared
    @LocalServerPort
    int port

    @Shared
    Closure baseURI = { -> "http://localhost:" + port + "/api/v1" }

    def setupSpec() {
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter())
        // RestAssured.baseURI = "http://localhost:" + port + "/api/v1"
    }

    def "POST: Create the Order"() {
        given: "The Order Entity is created"
            def orderEntity = OrderMockObjects.mockGetOrderById("1234")

        when: "The Order is saved in the Server"
        RestAssured.baseURI = "http://localhost:$port/api/v1"  // moved inside the test
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
                .body("orderId", equalTo(orderId))
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
                .extract().response()

        then:
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
                    .body("orderStatus", equalTo(statusId))
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
