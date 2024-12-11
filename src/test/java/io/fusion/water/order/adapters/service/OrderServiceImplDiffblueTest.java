package io.fusion.water.order.adapters.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import io.fusion.water.order.adapters.external.ExternalGateWay;
import io.fusion.water.order.domainLayer.models.CardDetails;
import io.fusion.water.order.domainLayer.models.CardType;
import io.fusion.water.order.domainLayer.models.Customer;
import io.fusion.water.order.domainLayer.models.OrderEntity;
import io.fusion.water.order.domainLayer.models.OrderStatus;
import io.fusion.water.order.domainLayer.models.PaymentDetails;
import io.fusion.water.order.domainLayer.models.PaymentStatus;
import io.fusion.water.order.domainLayer.models.PaymentType;
import io.fusion.water.order.domainLayer.models.ShippingAddress;
import io.fusion.water.order.domainLayer.services.OrderRepository;
import io.fusion.water.order.domainLayer.services.PaymentService;

import java.sql.SQLOutput;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

import org.junit.jupiter.api.Disabled;

import org.junit.jupiter.api.DisplayName;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@DisabledInAotMode
@ContextConfiguration(classes = {OrderServiceImpl.class})
@ExtendWith(SpringExtension.class)
class OrderServiceImplDiffblueTest {
    @MockBean
    private ExternalGateWay externalGateWay;

    @MockBean
    private OrderRepository orderRepository;

    @Autowired
    private OrderServiceImpl orderServiceImpl;

    @MockBean
    private PaymentService paymentService;

    /**
     * Test {@link OrderServiceImpl#getOrderById(String)}.
     * <p>
     * Method under test: {@link OrderServiceImpl#getOrderById(String)}
     */
    @Test
    @DisplayName("Test getOrderById(String)")
    void testGetOrderById() {
        System.out.println("Arrange & Act: Get Order By ID... ");
        // Arrange and Act
        OrderEntity actualOrderById = orderServiceImpl.getOrderById(" id");

        System.out.println("Assert: Check All the Critical Fields");
        // Assert
        assertEquals(" id", actualOrderById.getOrderId());
        assertNull(actualOrderById.getPaymentStatus());
        assertEquals(2248.0d, actualOrderById.getTotalValue());
        assertEquals(3, actualOrderById.getTotalItems());
        assertEquals(3, actualOrderById.getOrderItems().size());
        assertEquals(OrderStatus.INITIATED, actualOrderById.getOrderStatus());
        assertEquals(PaymentType.CREDIT_CARD, actualOrderById.getPaymentType());
        assertTrue(actualOrderById.isCustomerAvailable());
        assertTrue(actualOrderById.isShippingAddressAvailable());
        System.out.println("Test Completed... Success... ");
    }

    /**
     * Test {@link OrderServiceImpl#saveOrderExternal(OrderEntity)}.
     * <p>
     * Method under test: {@link OrderServiceImpl#saveOrderExternal(OrderEntity)}
     */
    @Test
    @DisplayName("Test saveOrderExternal(OrderEntity)")
    void testSaveOrderExternal() {
        // Arrange
        OrderEntity buildResult = (new OrderEntity.Builder()).build();
        when(externalGateWay.saveOrder(Mockito.<OrderEntity>any())).thenReturn(buildResult);

        // Act
        OrderEntity actualSaveOrderExternalResult = orderServiceImpl.saveOrderExternal(new OrderEntity());

        // Assert
        verify(externalGateWay).saveOrder(isA(OrderEntity.class));
        assertNull(actualSaveOrderExternalResult.getCustomer());
        assertNull(actualSaveOrderExternalResult.getOrderStatus());
        assertNull(actualSaveOrderExternalResult.getPaymentDetails());
        assertNull(actualSaveOrderExternalResult.getPaymentStatus());
        assertNull(actualSaveOrderExternalResult.getPaymentType());
        assertNull(actualSaveOrderExternalResult.getShippingAddress());
        assertNull(actualSaveOrderExternalResult.getOrderId());
        assertNull(actualSaveOrderExternalResult.getOrderDate());
        assertEquals(0, actualSaveOrderExternalResult.getTotalItems());
        assertEquals(0.0d, actualSaveOrderExternalResult.getTotalValue());
        assertFalse(actualSaveOrderExternalResult.isCustomerAvailable());
        assertFalse(actualSaveOrderExternalResult.isShippingAddressAvailable());
        assertTrue(actualSaveOrderExternalResult.getOrderItems().isEmpty());
        System.out.println("Test Completed... Success... ");
    }

    /**
     * Test {@link OrderServiceImpl#processOrder(OrderEntity)}.
     * <p>
     * Method under test: {@link OrderServiceImpl#processOrder(OrderEntity)}
     */
    @Test
    @DisplayName("Test processOrder(OrderEntity)")
    @Disabled("TODO: Complete this test")
    void testProcessOrder() {
        // TODO: Diffblue Cover was only able to create a partial test for this method:
        //   Reason: No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException: Cannot invoke "String.equalsIgnoreCase(String)" because the return value of "io.fusion.water.order.domainLayer.models.PaymentStatus.getPaymentStatus()" is null
        //       at io.fusion.water.order.domainLayer.models.OrderEntity.setPaymentStatus(OrderEntity.java:401)
        //       at io.fusion.water.order.adapters.service.OrderServiceImpl.processOrder(OrderServiceImpl.java:81)
        //   See https://diff.blue/R013 to resolve this issue.

        // Arrange
        OrderEntity buildResult = (new OrderEntity.Builder()).build();
        when(orderRepository.saveOrder(Mockito.<OrderEntity>any())).thenReturn(buildResult);
        when(paymentService.processPayments(Mockito.<PaymentDetails>any())).thenReturn(new PaymentStatus());

        // Act
        orderServiceImpl.processOrder(new OrderEntity());
    }

    /**
     * Method under test: {@link OrderServiceImpl#cancelOrder(String)}
     */
    @Test
    void testCancelOrder2() {
        // Arrange
        OrderEntity orderEntity = new OrderEntity();
        when(orderRepository.cancelOrder(Mockito.<String>any())).thenReturn(orderEntity);

        // Act
        OrderEntity actualCancelOrderResult = orderServiceImpl.cancelOrder(" id");

        // Assert
        verify(orderRepository).cancelOrder(eq(" id"));
        assertSame(orderEntity, actualCancelOrderResult);
        System.out.println("Test Completed... Success... ");
    }

    /**
     * Test {@link OrderServiceImpl#cancelOrder(String)} with {@code _id}.
     * <p>
     * Method under test: {@link OrderServiceImpl#cancelOrder(String)}
     */
    @Test
    @DisplayName("Test cancelOrder(String) with '_id'")
    void testCancelOrderWithId() {
        // Arrange
        OrderEntity buildResult = (new OrderEntity.Builder()).build();
        when(orderRepository.cancelOrder(Mockito.<String>any())).thenReturn(buildResult);

        // Act
        OrderEntity actualCancelOrderResult = orderServiceImpl.cancelOrder(" id");

        // Assert
        verify(orderRepository).cancelOrder(eq(" id"));
        assertNull(actualCancelOrderResult.getCustomer());
        assertNull(actualCancelOrderResult.getOrderStatus());
        assertNull(actualCancelOrderResult.getPaymentDetails());
        assertNull(actualCancelOrderResult.getPaymentStatus());
        assertNull(actualCancelOrderResult.getPaymentType());
        assertNull(actualCancelOrderResult.getShippingAddress());
        assertNull(actualCancelOrderResult.getOrderId());
        assertNull(actualCancelOrderResult.getOrderDate());
        assertEquals(0, actualCancelOrderResult.getTotalItems());
        assertEquals(0.0d, actualCancelOrderResult.getTotalValue());
        assertFalse(actualCancelOrderResult.isCustomerAvailable());
        assertFalse(actualCancelOrderResult.isShippingAddressAvailable());
        assertTrue(actualCancelOrderResult.getOrderItems().isEmpty());
        System.out.println("Test Completed... Success... ");
    }

    /**
     * Test {@link OrderServiceImpl#cancelOrder(OrderEntity)} with {@code _order}.
     * <p>
     * Method under test: {@link OrderServiceImpl#cancelOrder(OrderEntity)}
     */
    @Test
    @DisplayName("Test cancelOrder(OrderEntity) with '_order'")
    void testCancelOrderWithOrder() {
        // Arrange
        OrderEntity buildResult = (new OrderEntity.Builder()).build();
        when(orderRepository.cancelOrder(Mockito.<OrderEntity>any())).thenReturn(buildResult);

        // Act
        OrderEntity actualCancelOrderResult = orderServiceImpl.cancelOrder(new OrderEntity());

        // Assert
        verify(orderRepository).cancelOrder(isA(OrderEntity.class));
        assertNull(actualCancelOrderResult.getCustomer());
        assertNull(actualCancelOrderResult.getOrderStatus());
        assertNull(actualCancelOrderResult.getPaymentDetails());
        assertNull(actualCancelOrderResult.getPaymentStatus());
        assertNull(actualCancelOrderResult.getPaymentType());
        assertNull(actualCancelOrderResult.getShippingAddress());
        assertNull(actualCancelOrderResult.getOrderId());
        assertNull(actualCancelOrderResult.getOrderDate());
        assertEquals(0, actualCancelOrderResult.getTotalItems());
        assertEquals(0.0d, actualCancelOrderResult.getTotalValue());
        assertFalse(actualCancelOrderResult.isCustomerAvailable());
        assertFalse(actualCancelOrderResult.isShippingAddressAvailable());
        assertTrue(actualCancelOrderResult.getOrderItems().isEmpty());
        System.out.println("Test Completed... Success... ");
    }

    /**
     * Test {@link OrderServiceImpl#prepareOrder(OrderEntity)}.
     * <p>
     * Method under test: {@link OrderServiceImpl#prepareOrder(OrderEntity)}
     */
    @Test
    @DisplayName("Test prepareOrder(OrderEntity)")
    void testPrepareOrder() {
        // Arrange
        OrderEntity buildResult = (new OrderEntity.Builder()).build();
        when(orderRepository.prepareOrder(Mockito.<OrderEntity>any())).thenReturn(buildResult);

        // Act
        OrderEntity actualPrepareOrderResult = orderServiceImpl.prepareOrder(new OrderEntity());

        // Assert
        verify(orderRepository).prepareOrder(isA(OrderEntity.class));
        assertNull(actualPrepareOrderResult.getCustomer());
        assertNull(actualPrepareOrderResult.getOrderStatus());
        assertNull(actualPrepareOrderResult.getPaymentDetails());
        assertNull(actualPrepareOrderResult.getPaymentStatus());
        assertNull(actualPrepareOrderResult.getPaymentType());
        assertNull(actualPrepareOrderResult.getShippingAddress());
        assertNull(actualPrepareOrderResult.getOrderId());
        assertNull(actualPrepareOrderResult.getOrderDate());
        assertEquals(0, actualPrepareOrderResult.getTotalItems());
        assertEquals(0.0d, actualPrepareOrderResult.getTotalValue());
        assertFalse(actualPrepareOrderResult.isCustomerAvailable());
        assertFalse(actualPrepareOrderResult.isShippingAddressAvailable());
        assertTrue(actualPrepareOrderResult.getOrderItems().isEmpty());
        System.out.println("Test Completed... Success... ");
    }

    /**
     * Test {@link OrderServiceImpl#updateOrderStatus(String, String)}.
     * <p>
     * Method under test: {@link OrderServiceImpl#updateOrderStatus(String, String)}
     */
    @Test
    @DisplayName("Test updateOrderStatus(String, String)")
    void testUpdateOrderStatus() {
        // Arrange
        OrderEntity buildResult = (new OrderEntity.Builder()).build();
        when(orderRepository.saveOrder(Mockito.<OrderEntity>any())).thenReturn(buildResult);

        // Act
        OrderEntity actualUpdateOrderStatusResult = orderServiceImpl.updateOrderStatus(" id", " status");

        // Assert
        verify(orderRepository).saveOrder(isA(OrderEntity.class));
        assertNull(actualUpdateOrderStatusResult.getCustomer());
        assertNull(actualUpdateOrderStatusResult.getOrderStatus());
        assertNull(actualUpdateOrderStatusResult.getPaymentDetails());
        assertNull(actualUpdateOrderStatusResult.getPaymentStatus());
        assertNull(actualUpdateOrderStatusResult.getPaymentType());
        assertNull(actualUpdateOrderStatusResult.getShippingAddress());
        assertNull(actualUpdateOrderStatusResult.getOrderId());
        assertNull(actualUpdateOrderStatusResult.getOrderDate());
        assertEquals(0, actualUpdateOrderStatusResult.getTotalItems());
        assertEquals(0.0d, actualUpdateOrderStatusResult.getTotalValue());
        assertFalse(actualUpdateOrderStatusResult.isCustomerAvailable());
        assertFalse(actualUpdateOrderStatusResult.isShippingAddressAvailable());
        assertTrue(actualUpdateOrderStatusResult.getOrderItems().isEmpty());
        System.out.println("Test Completed... Success... ");
    }

    /**
     * Test {@link OrderServiceImpl#shipOrder(String)}.
     * <p>
     * Method under test: {@link OrderServiceImpl#shipOrder(String)}
     */
    @Test
    @DisplayName("Test shipOrder(String)")
    void testShipOrder() {
        // Arrange and Act
        OrderEntity actualShipOrderResult = orderServiceImpl.shipOrder(" id");

        // Assert
        assertEquals(" id", actualShipOrderResult.getOrderId());
        assertNull(actualShipOrderResult.getPaymentStatus());
        assertEquals(2248.0d, actualShipOrderResult.getTotalValue());
        assertEquals(3, actualShipOrderResult.getTotalItems());
        assertEquals(3, actualShipOrderResult.getOrderItems().size());
        assertEquals(OrderStatus.READY_FOR_SHIPMENT, actualShipOrderResult.getOrderStatus());
        assertEquals(PaymentType.CREDIT_CARD, actualShipOrderResult.getPaymentType());
        assertTrue(actualShipOrderResult.isCustomerAvailable());
        assertTrue(actualShipOrderResult.isShippingAddressAvailable());
        System.out.println("Test Completed... Success... ");
    }

    /**
     * Method under test: {@link OrderServiceImpl#processPayments(PaymentDetails)}
     */
    @Test
    void testProcessPayments() {
        // Arrange
        PaymentStatus paymentStatus = new PaymentStatus();
        when(paymentService.processPayments(Mockito.<PaymentDetails>any())).thenReturn(paymentStatus);

        // Act
        PaymentStatus actualProcessPaymentsResult = orderServiceImpl.processPayments(new PaymentDetails());

        // Assert
        verify(paymentService).processPayments(isA(PaymentDetails.class));
        assertSame(paymentStatus, actualProcessPaymentsResult);
        System.out.println("Test Completed... Success... ");
    }
}
