package io.fusion.water.order.adapters.repository;

import io.fusion.water.order.domain.models.OrderEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import test.fusion.water.order.utils.OrderMockObjects;

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
        OrderEntity order = OrderMockObjects.mockGetOrderById("1234");

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
        OrderEntity order = OrderMockObjects.mockGetOrderById("1234");

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
        OrderEntity order = OrderMockObjects.mockGetOrderById("1234");

        // Act
        OrderEntity actualPrepareOrderResult = this.orderRepositoryImpl.prepareOrder(order);

        // Assert
        assertThat(actualPrepareOrderResult).isNotNull();
    }
}
