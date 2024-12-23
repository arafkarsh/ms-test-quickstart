package io.fusion.water.order.adapters.repository;

import io.fusion.water.order.domain.models.OrderEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

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
        // TODO: Populate arranged inputs
        String _id = "";

        // Act
        OrderEntity actualOrderById = this.orderRepositoryImpl.getOrderById(_id);

        // Assert
        // TODO: Add assertions on result
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
        // TODO: Populate arranged inputs
        OrderEntity _order = null;

        // Act
        OrderEntity actualSaveOrderResult = this.orderRepositoryImpl.saveOrder(_order);

        // Assert
        // TODO: Add assertions on result
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
        // TODO: Populate arranged inputs
        String _id = "";

        // Act
        OrderEntity actualCancelOrderResult = this.orderRepositoryImpl.cancelOrder(_id);

        // Assert
        // TODO: Add assertions on result
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
        // TODO: Populate arranged inputs
        OrderEntity _order = null;

        // Act
        OrderEntity actualCancelOrderResult = this.orderRepositoryImpl.cancelOrder(_order);

        // Assert
        // TODO: Add assertions on result
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
        // TODO: Populate arranged inputs
        OrderEntity _order = null;

        // Act
        OrderEntity actualPrepareOrderResult = this.orderRepositoryImpl.prepareOrder(_order);

        // Assert
        // TODO: Add assertions on result
    }
}
