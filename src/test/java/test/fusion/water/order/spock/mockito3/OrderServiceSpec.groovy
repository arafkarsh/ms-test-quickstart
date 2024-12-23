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
package test.fusion.water.order.spock.mockito3

// Spock
import spock.lang.*
// SpringBoot
import org.springframework.boot.test.context.SpringBootTest
// Custom
import io.fusion.water.order.OrderApplication
import io.fusion.water.order.domain.models.*;
import io.fusion.water.order.domain.services.OrderRepository;
import io.fusion.water.order.domain.services.PaymentService;
import io.fusion.water.order.adapters.service.OrderServiceImpl

import java.time.LocalDateTime;

/**
 * Order Service Test
 *
 * @author arafkarsh
 *
 */

@SpringBootTest(classes= OrderApplication.class)
class OrderServiceSpec extends Specification {

    @Shared
    OrderEntity order
    @Shared
    PaymentStatus paymentAccepted
    @Shared
    PaymentStatus paymentDeclined

    def counter = 1

    def orderRepo = Mock(OrderRepository)
    def paymentService = Mock(PaymentService)
    def orderService = new OrderServiceImpl(orderRepo, paymentService)

    def setupSpec() {
        println("== Order Service Mock Suite Execution Started...")
    }

    def setup() {
        println("${counter}. Create Order, PaymentStatus...")
        order = createOrder()
        println("${counter}. Create Order, PaymentStatus...${order.paymentDetails}")
        paymentAccepted = createPaymentStatusAccepted(order.paymentDetails)
        paymentDeclined = createPaymentStatusDeclined(order.paymentDetails)
    }

    /**
     * Process Order and Check the Payment Status PAID
     * @return
     */
    def "1. Test for Payment Accepted"() {
        given: "Order is Ready"
            orderRepo.saveOrder(order) >> order
            paymentService.processPayments(order.paymentDetails) >> paymentAccepted

        when: "Order is Processed for Payment"
            def processedOrder = orderService.processOrder(order)

        then: "Check the Payment Status as Accepted"
            processedOrder.orderStatus == OrderStatus.PAID
    }

    /**
     * Process the Order and Check the Payment Status DECLINED
     * @return
     */
    def "2. Test for Payment Declined"() {
        given: "Order is Ready"
            orderRepo.saveOrder(order) >> order
            paymentService.processPayments(order.paymentDetails) >> paymentDeclined

        when: "Order is Processed for Payment"
            def processedOrder = orderService.processOrder(order)

        then: "Check the Payment Status as Declined"
            processedOrder.orderStatus == OrderStatus.PAYMENT_DECLINED
    }

    /**
     * Returns OrderEntity
     * @return
     */
    public OrderEntity createOrder() {
        return new OrderEntity.Builder()
                .addCustomer(new Customer
                        ("UUID", "John", "Doe", "0123456789"))
                .addOrderItem(new OrderItem
                        ("uuid1", "iPhone 12", 799, "USD", 1))
                .addOrderItem(new OrderItem
                        ("uuid2", "iPhone 12 Pro", 999, "USD", 1))
                .addOrderItem(new OrderItem
                        ("uuid3", "Apple Watch Series 6", 450, "USD", 2))
                .addShippingAddress(new ShippingAddress
                        ("321 Cobblestone Ln,", "", "Edison", "NJ", "", "USA", "08820"))
                .addPaymentType(PaymentType.CREDIT_CARD)
                .addCardDetails(new CardDetails
                        ("1234 5678 9876 5432", "John Doe", 7, 2025, 456, CardType.MASTER))
                .build();

    }

    /**
     * Payment Status - Accepted
     *
     * @param _paymentDetails
     * @return
     */
    public PaymentStatus createPaymentStatusAccepted(PaymentDetails _paymentDetails) {
        return new PaymentStatus(
                _paymentDetails.getTransactionId(),
                _paymentDetails.getTransactionDate(),
                "Accepted",
                UUID.randomUUID().toString(),
                LocalDateTime.now(),
                PaymentType.CREDIT_CARD);
    }

    /**
     * Payment Status - Declined
     *
     * @param _paymentDetails
     * @return
     */
    public PaymentStatus createPaymentStatusDeclined(PaymentDetails _paymentDetails) {
        return new PaymentStatus(
                _paymentDetails.getTransactionId(),
                _paymentDetails.getTransactionDate(),
                "Declined",
                UUID.randomUUID().toString(),
                LocalDateTime.now(),
                PaymentType.CREDIT_CARD);
    }

    def cleanup() {
        println "Should Execute After Each Test"
    }

    def cleanupSpec() {
        println "== Order tests Suite Execution Completed..."
    }
}
