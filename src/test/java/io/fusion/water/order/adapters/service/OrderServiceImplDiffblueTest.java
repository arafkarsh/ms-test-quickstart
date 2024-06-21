package io.fusion.water.order.adapters.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

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
     * Method under test: {@link OrderServiceImpl#getOrderById(String)}
     */
    @Test
    void testGetOrderById() {
        // Arrange and Act
        OrderEntity actualOrderById = orderServiceImpl.getOrderById(" id");

        // Assert
        assertEquals(" id", actualOrderById.getOrderId());
        PaymentDetails paymentDetails = actualOrderById.getPaymentDetails();
        assertEquals(" id", paymentDetails.getTransactionId());
        ShippingAddress shippingAddress = actualOrderById.getShippingAddress();
        assertEquals("", shippingAddress.getAddressLine2());
        assertEquals("", shippingAddress.getLandMark());
        Customer customer = actualOrderById.getCustomer();
        assertEquals("0123456789", customer.getPhoneNumber());
        ArrayList<String> phoneList = customer.getPhoneList();
        assertEquals(1, phoneList.size());
        assertEquals("0123456789", phoneList.get(0));
        assertEquals("08820", shippingAddress.getZipCode());
        assertEquals("321 Cobblestone Ln,", shippingAddress.getStreetName());
        assertEquals("Doe", customer.getLastName());
        assertEquals("Edison", shippingAddress.getCity());
        CardDetails cardDetails = paymentDetails.getCardDetails();
        assertEquals("John Doe", cardDetails.getHolderName());
        assertEquals("John", customer.getFirstName());
        assertEquals("NJ", shippingAddress.getState());
        assertEquals("USA", shippingAddress.getCountry());
        assertEquals("UUID", customer.getCustomerId());
        assertEquals("XXXX XXXX XXXX 5432", cardDetails.getCardNumber());
        assertEquals(0, cardDetails.getCardCode());
        assertEquals(2025, cardDetails.getExpiryYear());
        assertEquals(2248.0d, actualOrderById.getTotalValue());
        assertEquals(2248.0d, paymentDetails.getOrderValue());
        assertEquals(3, actualOrderById.getTotalItems());
        assertEquals(7, cardDetails.getExpiryMonth());
        assertEquals(CardType.MASTER, cardDetails.getCardType());
        assertEquals(OrderStatus.INITIATED, actualOrderById.getOrderStatus());
        assertEquals(PaymentType.CREDIT_CARD, actualOrderById.getPaymentType());
        assertEquals(PaymentType.CREDIT_CARD, paymentDetails.getPaymentType());
        assertTrue(actualOrderById.isCustomerAvailable());
        assertTrue(actualOrderById.isShippingAddressAvailable());
        LocalDateTime expectedTransactionDate = actualOrderById.getOrderDate();
        assertSame(expectedTransactionDate, paymentDetails.getTransactionDate());
    }

    /**
     * Method under test: {@link OrderServiceImpl#saveOrderExternal(OrderEntity)}
     */
    @Test
    void testSaveOrderExternal() {
        // Arrange
        OrderEntity orderEntity = new OrderEntity();
        when(externalGateWay.saveOrder(Mockito.<OrderEntity>any())).thenReturn(orderEntity);

        // Act
        OrderEntity actualSaveOrderExternalResult = orderServiceImpl.saveOrderExternal(new OrderEntity());

        // Assert
        verify(externalGateWay).saveOrder(isA(OrderEntity.class));
        assertSame(orderEntity, actualSaveOrderExternalResult);
    }

    /**
     * Method under test: {@link OrderServiceImpl#processOrder(OrderEntity)}
     */
    @Test
    void testProcessOrder() {
        // Arrange
        when(orderRepository.saveOrder(Mockito.<OrderEntity>any())).thenReturn(null);

        // Act
        OrderEntity actualProcessOrderResult = orderServiceImpl.processOrder(new OrderEntity());

        // Assert
        verify(orderRepository).saveOrder(isA(OrderEntity.class));
        assertNull(actualProcessOrderResult);
    }

    /**
     * Method under test: {@link OrderServiceImpl#processOrder(OrderEntity)}
     */
    @Test
    void testProcessOrder2() {
        // Arrange
        OrderEntity orderEntity = new OrderEntity();
        when(orderRepository.saveOrder(Mockito.<OrderEntity>any())).thenReturn(orderEntity);
        when(paymentService.processPayments(Mockito.<PaymentDetails>any())).thenReturn(null);

        // Act
        OrderEntity actualProcessOrderResult = orderServiceImpl.processOrder(new OrderEntity());

        // Assert
        verify(orderRepository).saveOrder(isA(OrderEntity.class));
        verify(paymentService).processPayments(isNull());
        assertEquals(OrderStatus.PAYMENT_EXPECTED, actualProcessOrderResult.getOrderStatus());
        assertSame(orderEntity, actualProcessOrderResult);
    }

    /**
     * Method under test: {@link OrderServiceImpl#processOrder(OrderEntity)}
     */
    @Test
    void testProcessOrder3() {
        // Arrange
        OrderEntity orderEntity = new OrderEntity();
        when(orderRepository.saveOrder(Mockito.<OrderEntity>any())).thenReturn(orderEntity);
        LocalDateTime _txDate = LocalDate.of(1970, 1, 1).atStartOfDay();
        when(paymentService.processPayments(Mockito.<PaymentDetails>any())).thenReturn(new PaymentStatus("42", _txDate,
                "Accepted", "Accepted", LocalDate.of(1970, 1, 1).atStartOfDay(), PaymentType.CREDIT_CARD));

        // Act
        OrderEntity actualProcessOrderResult = orderServiceImpl.processOrder(new OrderEntity());

        // Assert
        verify(orderRepository).saveOrder(isA(OrderEntity.class));
        verify(paymentService).processPayments(isNull());
        assertEquals("Accepted", actualProcessOrderResult.getPaymentStatus().getPaymentReference());
        assertEquals(OrderStatus.PAID, actualProcessOrderResult.getOrderStatus());
        assertSame(orderEntity, actualProcessOrderResult);
    }

    /**
     * Method under test: {@link OrderServiceImpl#processOrder(OrderEntity)}
     */
    @Test
    void testProcessOrder4() {
        // Arrange
        OrderEntity orderEntity = new OrderEntity();
        when(orderRepository.saveOrder(Mockito.<OrderEntity>any())).thenReturn(orderEntity);
        PaymentStatus paymentStatus = mock(PaymentStatus.class);
        when(paymentStatus.getPaymentStatus()).thenReturn("Payment Status");
        when(paymentService.processPayments(Mockito.<PaymentDetails>any())).thenReturn(paymentStatus);

        // Act
        OrderEntity actualProcessOrderResult = orderServiceImpl.processOrder(new OrderEntity());

        // Assert
        verify(paymentStatus).getPaymentStatus();
        verify(orderRepository).saveOrder(isA(OrderEntity.class));
        verify(paymentService).processPayments(isNull());
        assertEquals(OrderStatus.PAYMENT_DECLINED, actualProcessOrderResult.getOrderStatus());
        assertSame(orderEntity, actualProcessOrderResult);
    }

    /**
     * Method under test: {@link OrderServiceImpl#cancelOrder(OrderEntity)}
     */
    @Test
    void testCancelOrder() {
        // Arrange
        OrderEntity orderEntity = new OrderEntity();
        when(orderRepository.cancelOrder(Mockito.<OrderEntity>any())).thenReturn(orderEntity);

        // Act
        OrderEntity actualCancelOrderResult = orderServiceImpl.cancelOrder(new OrderEntity());

        // Assert
        verify(orderRepository).cancelOrder(isA(OrderEntity.class));
        assertSame(orderEntity, actualCancelOrderResult);
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
    }

    /**
     * Method under test: {@link OrderServiceImpl#prepareOrder(OrderEntity)}
     */
    @Test
    void testPrepareOrder() {
        // Arrange
        OrderEntity orderEntity = new OrderEntity();
        when(orderRepository.prepareOrder(Mockito.<OrderEntity>any())).thenReturn(orderEntity);

        // Act
        OrderEntity actualPrepareOrderResult = orderServiceImpl.prepareOrder(new OrderEntity());

        // Assert
        verify(orderRepository).prepareOrder(isA(OrderEntity.class));
        assertSame(orderEntity, actualPrepareOrderResult);
    }

    /**
     * Method under test: {@link OrderServiceImpl#updateOrderStatus(String, String)}
     */
    @Test
    void testUpdateOrderStatus() {
        // Arrange
        OrderEntity orderEntity = new OrderEntity();
        when(orderRepository.saveOrder(Mockito.<OrderEntity>any())).thenReturn(orderEntity);

        // Act
        OrderEntity actualUpdateOrderStatusResult = orderServiceImpl.updateOrderStatus(" id", " status");

        // Assert
        verify(orderRepository).saveOrder(isA(OrderEntity.class));
        assertSame(orderEntity, actualUpdateOrderStatusResult);
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
    }
}
