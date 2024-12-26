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
package io.fusion.water.order.adapters.repository;

import io.fusion.water.order.domain.models.OrderEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import test.fusion.water.order.utils.OrderMock;

import static com.google.common.truth.Truth.assertThat;

@ContextConfiguration(classes = {OrderRepositoryImpl.class})
@ExtendWith(SpringExtension.class)
class OrderRepositoryImplDiffblueTest {
    @Autowired
    private OrderRepositoryImpl orderRepositoryImpl;

    /**
     * Test {@link OrderRepositoryImpl#getOrderById(String)}.
     * <p>
     * Method under test: {@link OrderRepositoryImpl#getOrderById(String)}
     */
    @Test
    @DisplayName("Test getOrderById(String)")
    void testGetOrderById() {
        // Arrange
        String id = "1234";

        // Act
        OrderEntity actualOrderById = this.orderRepositoryImpl.getOrderById(id);

        // Assert
        assertThat(actualOrderById).isNotNull();
    }

    /**
     * Test {@link OrderRepositoryImpl#saveOrder(OrderEntity)}.
     * <p>
     * Method under test: {@link OrderRepositoryImpl#saveOrder(OrderEntity)}
     */
    @Test
    @DisplayName("Test saveOrder(OrderEntity)")
    void testSaveOrder() {
        // Arrange
        OrderEntity order = OrderMock.getOrderById("1234");

        // Act
        OrderEntity actualSaveOrderResult = this.orderRepositoryImpl.saveOrder(order);

        // Assert
        assertThat(actualSaveOrderResult).isNotNull();
    }

    /**
     * Test {@link OrderRepositoryImpl#cancelOrder(String)} with {@code _id}.
     * <p>
     * Method under test: {@link OrderRepositoryImpl#cancelOrder(String)}
     */
    @Test
    @DisplayName("Test cancelOrder(String) with '_id'")
    void testCancelOrderWithId() {
        // Arrange
        String id = "1234";

        // Act
        OrderEntity actualCancelOrderResult = this.orderRepositoryImpl.cancelOrder(id);

        // Assert
        assertThat(actualCancelOrderResult).isNotNull();
    }

    /**
     * Test {@link OrderRepositoryImpl#cancelOrder(OrderEntity)} with
     * {@code _order}.
     * <p>
     * Method under test: {@link OrderRepositoryImpl#cancelOrder(OrderEntity)}
     */
    @Test
    @DisplayName("Test cancelOrder(OrderEntity) with '_order'")
    void testCancelOrderWithOrder() {
        // Arrange
        OrderEntity order = OrderMock.getOrderById("1234");

        // Act
        OrderEntity actualCancelOrderResult = this.orderRepositoryImpl.cancelOrder(order);

        // Assert
        assertThat(actualCancelOrderResult).isNotNull();
    }

    /**
     * Test {@link OrderRepositoryImpl#prepareOrder(OrderEntity)}.
     * <p>
     * Method under test: {@link OrderRepositoryImpl#prepareOrder(OrderEntity)}
     */
    @Test
    @DisplayName("Test prepareOrder(OrderEntity)")
    void testPrepareOrder() {
        // Arrange
        OrderEntity order = OrderMock.getOrderById("1234");

        // Act
        OrderEntity actualPrepareOrderResult = this.orderRepositoryImpl.prepareOrder(order);

        // Assert
        assertThat(actualPrepareOrderResult).isNotNull();
    }
}
