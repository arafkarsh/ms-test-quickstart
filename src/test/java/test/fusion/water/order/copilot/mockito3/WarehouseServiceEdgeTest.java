/**
 * Copyright (c) 2024 Araf Karsh Hamid
 * <p>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * <p>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 * <p>
 * This program and the accompanying materials are dual-licensed under
 * either the terms of the Eclipse Public License v1.0 as published by
 * the Eclipse Foundation
 * <p>
 * or (per the licensee's choosing)
 * <p>
 * under the terms of the Apache 2 License version 2.0
 * as published by the Apache Software Foundation.
 */
package test.fusion.water.order.copilot.mockito3;

import io.fusion.water.order.adapters.service.WarehouseServiceImpl;
import io.fusion.water.order.domain.models.Customer;
import io.fusion.water.order.domain.models.OrderEntity;
import io.fusion.water.order.domain.models.OrderStatus;
import io.fusion.water.order.domain.services.PackingService;
import io.fusion.water.order.domain.services.ShippingService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

/**
 * ms-test-quickstart / WarehouseServiceTest 
 *
 * @author: Araf Karsh Hamid
 * @version: 0.1
 * @date: 2024-12-24T1:01 AM
 */
@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(OrderAnnotation.class)
class WarehouseServiceEdgeTest {

    @Mock
    private PackingService packingService;

    @Mock
    private ShippingService shippingService;

    @InjectMocks
    private WarehouseServiceImpl warehouseService;

    @DisplayName("Process Orders with Duplicate Orders")
    @Test
    @Order(4)
    void processOrdersWithDuplicateOrders() {
        List<OrderEntity> duplicateOrderList = new ArrayList<>();
        OrderEntity order = new OrderEntity.Builder()
                .addCustomer(new Customer("UUID", "John", "Doe", "0123456789"))
                .build();
        duplicateOrderList.add(order);
        duplicateOrderList.add(order);

        when(packingService.packOrders(duplicateOrderList)).thenReturn(duplicateOrderList);
        when(shippingService.shipOrder(duplicateOrderList)).thenReturn(duplicateOrderList);

        List<OrderEntity> processedOrders = warehouseService.processOrders(duplicateOrderList);

        assertEquals(2, processedOrders.size());
        for (OrderEntity processedOrder : processedOrders) {
            assertEquals(OrderStatus.READY_FOR_SHIPMENT, processedOrder.getOrderStatus());
        }
    }

    @DisplayName("Process Orders with Invalid Order Status")
    @Test
    @Order(5)
    void processOrdersWithInvalidOrderStatus() {
        List<OrderEntity> invalidOrderList = createOrder();
        OrderEntity order = new OrderEntity.Builder()
                .addCustomer(new Customer("UUID", "John", "Doe", "0123456789"))
                .build();
        order.orderCancelled();
        invalidOrderList.add(order);

        when(packingService.packOrders(invalidOrderList)).thenReturn(invalidOrderList);
        when(shippingService.shipOrder(invalidOrderList)).thenReturn(invalidOrderList);

        List<OrderEntity> processedOrders = warehouseService.processOrders(invalidOrderList);

        assertEquals(4, processedOrders.size());
        assertEquals(OrderStatus.CANCELLED, processedOrders.get(0).getOrderStatus());
    }

    @DisplayName("Process Orders with Mixed Valid and Invalid Orders")
    @Test
    @Order(6)
    void processOrdersWithMixedValidAndInvalidOrders() {
        List<OrderEntity> mixedOrderList = new ArrayList<>();
        OrderEntity validOrder = new OrderEntity.Builder()
                .addCustomer(new Customer("UUID", "John", "Doe", "0123456789"))
                .build();
        OrderEntity invalidOrder = new OrderEntity.Builder()
                .addCustomer(new Customer("UUID", "Jane", "Doe", "0123456789"))
                .build();
        invalidOrder.orderCancelled();
        mixedOrderList.add(validOrder);
        mixedOrderList.add(invalidOrder);

        when(packingService.packOrders(mixedOrderList)).thenReturn(mixedOrderList);
        when(shippingService.shipOrder(mixedOrderList)).thenReturn(mixedOrderList);

        List<OrderEntity> processedOrders = warehouseService.processOrders(mixedOrderList);

        assertEquals(2, processedOrders.size());
        assertEquals(OrderStatus.READY_FOR_SHIPMENT, processedOrders.get(0).getOrderStatus());
        assertEquals(OrderStatus.CANCELLED, processedOrders.get(1).getOrderStatus());
    }


    private List<OrderEntity> createOrder() {
        // Implement the method to create and return a list of OrderEntity objects
        List<OrderEntity> orderList = new ArrayList<>();
        // Create Order 1 for John Doe
        OrderEntity order1 = new OrderEntity.Builder()
                .addCustomer(new Customer("UUID", "John", "Doe", "0123465789"))
                .build();
        order1.orderPacked();
        orderList.add(order1);
        // Create Order 2 for Jane Doe
        OrderEntity order2 = new OrderEntity.Builder()
                .addCustomer(new Customer("UUID", "Jane", "Doe", "0132456879"))
                .build();
        order2.orderPacked();
        orderList.add(order2);
        // Create Order 3 for Joe Doe
        OrderEntity order3 = new OrderEntity.Builder()
                .addCustomer(new Customer("UUID", "Joe", "Doe", "0142358769"))
                .build();
        order3.orderPacked();
        orderList.add(order3);
        return orderList;
    }
}

