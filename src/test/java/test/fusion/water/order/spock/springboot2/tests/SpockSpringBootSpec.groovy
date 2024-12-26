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
package test.fusion.water.order.spock.springboot2.tests

// Custom
import io.fusion.water.order.OrderApplication
import io.fusion.water.order.domain.models.OrderEntity
import io.fusion.water.order.domain.models.PaymentStatus
import io.fusion.water.order.domain.services.OrderService
import io.fusion.water.order.domain.services.PaymentService
import test.fusion.water.order.utils.OrderMock

// SpringBoot
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

// Spock
import spock.lang.Specification
import spock.lang.Stepwise


/**
 * Spock - SpringBoot Auto wiring example
 *
 * @author arafkarsh
 *
 */
@Stepwise
@SpringBootTest(classes = OrderApplication.class)
class SpockSpringBootSpec extends Specification {

    @Autowired
    PaymentService paymentService;

    @Autowired
    OrderService orderService;

    OrderEntity order
    PaymentStatus paymentStatus

    def setupSpec() {
        println "== Order tests Suite Execution Started..."
    }

    def setup() {
        println "Create an Empty Order.."
        order = new OrderEntity()
    }

    /**
     * Spock SpringBoot Example with Auto wiring and executing the services.
     * For Processing the Order
     * Only for Demo Purpose for Auto wiring
     */
    def "1. Do Order Processing and Check Status"() {
        given: "An Order is created for a Customer"
            order = OrderMock.getOrderById("1234")

        when: "The Order is processed for payment"
            OrderEntity result = orderService.prepareOrder(order)
            order = result;
            System.out.println("Result = "+result.getOrderStatus())

        then:
            result.getOrderStatus().toString() == "INITIATED"
    }

    /**
     * Spock SpringBoot Example with Auto wiring and executing the services.
     * For Payment Service get the Payment Status
     * Only for Demo Purpose for Auto wiring
     */
    def "2. Get the Payment status"() {
        given: "The order is already created "
            order = OrderMock.getOrderById("1234")
            OrderEntity result = orderService.prepareOrder(order)

        when: "Payment is Processed"
            paymentStatus = paymentService.processPayments(result.getPaymentDetails())

        then: "The Payment Status must be Accepted"
            paymentStatus.getPaymentStatus() == "Accepted"
    }

    def cleanup() {
        println "Should Execute After Each Test"
    }

    def cleanupSpec() {
        println "== Order tests Suite Execution Completed..."
    }
}