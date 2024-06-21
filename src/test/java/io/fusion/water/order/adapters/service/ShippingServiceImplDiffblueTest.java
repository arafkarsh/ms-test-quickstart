package io.fusion.water.order.adapters.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import io.fusion.water.order.domainLayer.models.DeliveryCity;
import io.fusion.water.order.domainLayer.models.OrderEntity;
import io.fusion.water.order.domainLayer.services.DeliveryCityService;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {ShippingServiceImpl.class})
@ExtendWith(SpringExtension.class)
class ShippingServiceImplDiffblueTest {
    @MockBean
    private DeliveryCityService deliveryCityService;

    @Autowired
    private ShippingServiceImpl shippingServiceImpl;

    /**
     * Method under test: {@link ShippingServiceImpl#shipOrder(ArrayList)}
     */
    @Test
    void testShipOrder() {
        // Arrange
        ArrayList<OrderEntity> _orderList = new ArrayList<>();

        // Act
        ArrayList<OrderEntity> actualShipOrderResult = shippingServiceImpl.shipOrder(_orderList);

        // Assert
        assertTrue(actualShipOrderResult.isEmpty());
        assertSame(_orderList, actualShipOrderResult);
    }

    /**
     * Method under test: {@link ShippingServiceImpl#shipOrder(ArrayList)}
     */
    @Test
    void testShipOrder2() {
        // Arrange
        ArrayList<OrderEntity> _orderList = new ArrayList<>();
        _orderList.add(new OrderEntity());

        // Act
        ArrayList<OrderEntity> actualShipOrderResult = shippingServiceImpl.shipOrder(_orderList);

        // Assert
        assertEquals(1, actualShipOrderResult.size());
        assertSame(_orderList, actualShipOrderResult);
    }

    /**
     * Method under test: {@link ShippingServiceImpl#shipOrder(ArrayList)}
     */
    @Test
    void testShipOrder3() {
        // Arrange
        ArrayList<OrderEntity> _orderList = new ArrayList<>();
        _orderList.add(new OrderEntity());
        _orderList.add(new OrderEntity());

        // Act
        ArrayList<OrderEntity> actualShipOrderResult = shippingServiceImpl.shipOrder(_orderList);

        // Assert
        assertEquals(2, actualShipOrderResult.size());
        assertSame(_orderList, actualShipOrderResult);
    }

    /**
     * Method under test:
     * {@link ShippingServiceImpl#getCities(ArrayList, String, String)}
     */
    @Test
    void testGetCities() {
        // Arrange, Act and Assert
        assertTrue(shippingServiceImpl.getCities(new ArrayList<>(), "MD", "GB").isEmpty());
    }

    /**
     * Method under test:
     * {@link ShippingServiceImpl#getCity(String, String, String)}
     */
    @Test
    void testGetCity() {
        // Arrange
        // TODO: Populate arranged inputs
        String city = "";
        String State = "";
        String country = "";

        // Act
        DeliveryCity actualCity = this.shippingServiceImpl.getCity(city, State, country);

        // Assert
        // TODO: Add assertions on result
    }
}
