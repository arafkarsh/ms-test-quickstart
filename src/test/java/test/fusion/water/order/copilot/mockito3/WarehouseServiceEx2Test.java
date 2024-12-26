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
import io.fusion.water.order.domain.models.OrderEntity;
import io.fusion.water.order.domain.models.OrderStatus;
import io.fusion.water.order.domain.services.PackingService;
import io.fusion.water.order.domain.services.ShippingService;
import io.fusion.water.order.utils.Std;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import test.fusion.water.order.utils.OrderMock;

import java.util.List;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

/**
 * ms-test-quickstart / WarehouseServiceEx2Test
 * Code Autogenerated by GitHub CoPilot
 * Prompt:
 * /tests WarehouseServiceImpl
 *
 * @author: CoPilot, Araf Karsh Hamid
 * @version: 0.1
 * @date: 2024-12-24T1:01 AM
 */
@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(OrderAnnotation.class)
class WarehouseServiceEx2Test {

    @Mock
    private PackingService packingService;

    @Mock
    private ShippingService shippingService;

    @InjectMocks
    private WarehouseServiceImpl warehouseService;

    @Test
    @DisplayName("Process Orders - Orders with Null Status")
    @Order(4)
    @Tag("critical")
    void processOrdersWithNullStatus() {
        List<OrderEntity> ordersWithNullStatus = createOrdersWithNullStatus();
        when(packingService.packOrders(ordersWithNullStatus)).thenReturn(ordersWithNullStatus);
        when(shippingService.shipOrder(ordersWithNullStatus)).thenReturn(ordersWithNullStatus);

        List<OrderEntity> processedOrders = warehouseService.processOrders(ordersWithNullStatus);

        // Code modified to Check for Initiated Status
        for (OrderEntity order : processedOrders) {
            assertNotNull(order.getOrderStatus());
            assertEquals(OrderStatus.INITIATED, order.getOrderStatus());
            Std.println("Order ID = "+order.getOrderId()+" Status: "+order.getOrderStatus());
        }
    }

    @Test
    @DisplayName("Process Orders - Orders with Invalid Status")
    @Order(5)
    @Tag("critical")
    void processOrdersWithInvalidStatus() {
        List<OrderEntity> ordersWithInvalidStatus = createOrdersWithInvalidStatus();
        when(packingService.packOrders(ordersWithInvalidStatus)).thenReturn(ordersWithInvalidStatus);
        when(shippingService.shipOrder(ordersWithInvalidStatus)).thenReturn(ordersWithInvalidStatus);

        List<OrderEntity> processedOrders = warehouseService.processOrders(ordersWithInvalidStatus);

        for (OrderEntity order : processedOrders) {
            assertEquals(OrderStatus.INVALID, order.getOrderStatus());
        }
    }

    /**
     * Modified Code
     * Create Orders with Null Status (Default is INITIATED)
     * @return
     */
    private List<OrderEntity> createOrdersWithNullStatus() {
        return OrderMock.createOrderList();
    }

    /**
     * Modified Code as there is No setStatus method in the OrderEntity
     * Create Orders with Invalid Status
     * Updated the auto generated code with Appropriate methods
     * @return
     */
    private List<OrderEntity> createOrdersWithInvalidStatus() {
        List<OrderEntity> orderList = OrderMock.createOrderList();
        orderList.forEach(OrderEntity::orderIsInvalid);
        return orderList;
    }

}