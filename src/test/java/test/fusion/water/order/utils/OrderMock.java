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
package test.fusion.water.order.utils;

import io.fusion.water.order.domain.models.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author: Araf Karsh Hamid
 * @version:
 * @date:
 */
public class OrderMock {

    /**
     * Returns the OrderEntity with Order ID as an input
     *
     * @param orderId
     * @return
     */
    public static OrderEntity getOrderById(String orderId) {
        RandomCardNumber rcn = new RandomCardNumber();
        return new OrderEntity.Builder()
                .setOrderId(orderId)
                .addCustomer(new Customer("UUID", rcn.getFirstName(), rcn.getLastName(), "0123456789"))
                .addOrderItem(new OrderItem("uuid1", "iPhone 12", 799, "USD", 1))
                .addOrderItem(new OrderItem("uuid2", "iPhone 12 Pro", 999, "USD", 1))
                .addOrderItem(new OrderItem("uuid3", "Apple Watch Series 6", 450, "USD", 2))
                .addShippingAddress(new ShippingAddress("321 Cobblestone Ln,", "", "Edison", "NJ", "", "USA", "08820"))
                .addPaymentType(PaymentType.CREDIT_CARD)
                .addCardDetails(new CardDetails(rcn.getCardNumber(), rcn.getCardHolder(), rcn.getMonth(), rcn.getYear(), rcn.getCardCode(), CardType.MASTER))
                .build();
    }

    /**
     * Create Order List - Getting Prepared
     * @return
     */
    public static List<OrderEntity> createOrder() {
        // Implement the method to create and return a list of OrderEntity objects
        List<OrderEntity> orderList = createOrderList();
        orderList.forEach(OrderEntity::orderIsGettingPrepared);
        return orderList;
    }

    /**
     * Create Orders Waiting for Payment
     * @return
     */
    public static List<OrderEntity> createPaymentExpectedOrders() {
        return createOrderList();
    }

    /**
     * Payment Accepted
     * @return
     */
    public static List<OrderEntity> createPaymentAcceptedOrders(List<OrderEntity> orderList) {
        for(OrderEntity order : orderList) {
            order.setPaymentStatus(paymentAccepted(order));
        }
        return orderList;
    }

    /**
     * Create Packed Orders
     * @return
     */
    public static List<OrderEntity> createPackedOrders(List<OrderEntity> orderList) {
        // Implement the method to create and return a list of OrderEntity objects
        orderList.forEach(OrderEntity::orderPacked);
        return orderList;
    }

    /**
     * Create Shipped Orders
     * @return
     */
    public static List<OrderEntity> createShippedOrders(List<OrderEntity> orderList) {
        // Implement the method to create and return a list of OrderEntity objects
        orderList.forEach(OrderEntity::orderShipped);
        return orderList;
    }

    /**
     *
     * @return
     */
    public static ArrayList<OrderEntity> createOrderList() {
        ArrayList<OrderEntity> orderList = new ArrayList<OrderEntity>();
        orderList.add(createOrder1());
        orderList.add(createOrder2());
        orderList.add(createOrder3());
        return orderList;
    }

    /**
     * Create Order 1
     * @return
     */
    public static OrderEntity createOrder1() {
        RandomCardNumber rcn = new RandomCardNumber();
        RandomIDGenerator rnd = new RandomIDGenerator();
        return new OrderEntity.Builder()
                .setOrderId(rnd.nextRandomHexNumber())
                .addCustomer(new Customer("UUID", rcn.getFirstName(), rcn.getLastName(),  "0123456789"))
                .addOrderItem(new OrderItem("uuid1", "iPhone 12", 799, "USD", 1))
                .addOrderItem(new OrderItem("uuid2", "iPhone 12 Pro", 999, "USD", 1))
                .addOrderItem(new OrderItem("uuid3", "Apple Watch Series 6", 450, "USD", 2))
                .addShippingAddress(new ShippingAddress("321 Cobblestone Ln,", "", "Edison", "NJ", "", "USA", "08820"))
                .addPaymentType(PaymentType.CREDIT_CARD)
                .addCardDetails(new CardDetails(rcn.getCardNumber(), rcn.getCardHolder(), rcn.getMonth(), rcn.getYear(), rcn.getCardCode(),CardType.MASTER))
                .waitingForPayment()
                .build();
    }

    /**
     * Create Order 2
     * @return
     */
    public static OrderEntity createOrder2() {
        RandomCardNumber rcn = new RandomCardNumber();
        RandomIDGenerator rnd = new RandomIDGenerator();
        return new OrderEntity.Builder()
                .setOrderId(rnd.nextRandomHexNumber())
                .addCustomer(new Customer("UUID", rcn.getFirstName(), rcn.getLastName(),  "0123456789"))
                .addOrderItem(new OrderItem("uuid2", "iPhone 12 Pro Max", 1199, "USD", 1))
                .addOrderItem(new OrderItem("uuid3", "Apple Watch Series 6", 450, "USD", 2))
                .addShippingAddress(new ShippingAddress("323 Cobblestone Ln,", "", "Edison", "NJ", "", "USA", "08820"))
                .addPaymentType(PaymentType.DEBIT_CARD)
                .addCardDetails(new CardDetails(rcn.getCardNumber(), rcn.getCardHolder(), rcn.getMonth(), rcn.getYear(), rcn.getCardCode(),CardType.MASTER))
                .waitingForPayment()
                .build();
    }

    /**
     * Create Order 3
     * @return
     */
    public static OrderEntity createOrder3() {
        RandomCardNumber rcn = new RandomCardNumber();
        RandomIDGenerator rnd = new RandomIDGenerator();
        return new OrderEntity.Builder()
                .setOrderId(rnd.nextRandomHexNumber())
                .addCustomer(new Customer("UUID", rcn.getFirstName(), rcn.getLastName(),  "0123456789"))
                .addOrderItem(new OrderItem("uuid2", "iPhone 12 Mini", 699, "USD", 1))
                .addOrderItem(new OrderItem("uuid3", "Apple Watch Series 6", 450, "USD", 2))
                .addShippingAddress(new ShippingAddress("323 Cobblestone Ln,", "", "Edison", "NJ", "", "USA", "08820"))
                .addPaymentType(PaymentType.GOOGLE_PAY)
                .addCardDetails(new CardDetails(rcn.getCardNumber(), rcn.getCardHolder(), rcn.getMonth(), rcn.getYear(), rcn.getCardCode(),CardType.MASTER))
                .waitingForPayment()
                .build();
    }

    public static PaymentStatus paymentAccepted(OrderEntity order) {
        // Implement the method to create and return a list of OrderEntity objects
        return new  PaymentStatus(
                order.getPaymentDetails().getTransactionId(),
                order.getPaymentDetails().getTransactionDate(),
                "Accepted",
                UUID.randomUUID().toString(),
                LocalDateTime.now(),
                PaymentType.CREDIT_CARD);
    }
}