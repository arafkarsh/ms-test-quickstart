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
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;

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
class WarehouseServiceTest {

    @Mock
    private PackingService packingService;

    @Mock
    private ShippingService shippingService;

    @InjectMocks
    private WarehouseServiceImpl warehouseService;

    @DisplayName("Process Orders Successfully")
    @Test
    @Order(1)
    void processOrdersSuccessfully() {
        List<OrderEntity> orderList = createOrder();
        when(packingService.packOrders(orderList)).thenReturn(orderList);
        when(shippingService.shipOrder(orderList)).thenReturn(orderList);

        List<OrderEntity> processedOrders = warehouseService.processOrders(orderList);

        assertEquals(orderList.size(), processedOrders.size());
        for (OrderEntity order : processedOrders) {
            assertEquals(OrderStatus.READY_FOR_SHIPMENT, order.getOrderStatus());
        }
    }

    @DisplayName("Process Orders with Empty List")
    @Test
    @Order(2)
    void processOrdersWithEmptyList() {
        List<OrderEntity> emptyOrderList = new ArrayList<>();
        when(packingService.packOrders(emptyOrderList)).thenReturn(emptyOrderList);
        when(shippingService.shipOrder(emptyOrderList)).thenReturn(emptyOrderList);

        List<OrderEntity> processedOrders = warehouseService.processOrders(emptyOrderList);

        assertTrue(processedOrders.isEmpty());
    }

    @DisplayName("Process Orders with Null List")
    @Test
    @Order(3)
    void processOrdersWithNullList() {
        List<OrderEntity> nullOrderList = null;
        when(packingService.packOrders(nullOrderList)).thenReturn(nullOrderList);
        when(shippingService.shipOrder(nullOrderList)).thenReturn(nullOrderList);

        List<OrderEntity> processedOrders = warehouseService.processOrders(nullOrderList);

        assertNull(processedOrders);
    }

    private List<OrderEntity> createOrder() {
        // Implement the method to create and return a list of OrderEntity objects
        return new ArrayList<>();
    }
}

