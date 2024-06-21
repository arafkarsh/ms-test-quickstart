package io.fusion.water.order.adapters.service;

import io.fusion.water.order.domainLayer.models.OrderEntity;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {WarehouseServiceImpl.class})
@ExtendWith(SpringExtension.class)
class WarehouseServiceImplDiffblueTest {
    @Autowired
    private WarehouseServiceImpl warehouseServiceImpl;

    /**
     * Method under test: {@link WarehouseServiceImpl#processOrders(ArrayList)}
     */
    @Test
    void testProcessOrders() {
        // Arrange
        // TODO: Populate arranged inputs
        ArrayList<OrderEntity> _orderList = null;

        // Act
        ArrayList<OrderEntity> actualProcessOrdersResult = this.warehouseServiceImpl.processOrders(_orderList);

        // Assert
        // TODO: Add assertions on result
    }
}
