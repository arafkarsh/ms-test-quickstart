package test.fusion.water.order.testng.mockito3.tests;

import io.fusion.water.order.adapters.service.OrderServiceImpl;
import io.fusion.water.order.domain.models.*;
import io.fusion.water.order.domain.services.OrderRepository;
import io.fusion.water.order.domain.services.PaymentService;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.testng.Assert.assertEquals;

/**
 * @author: Araf Karsh Hamid
 * @version:
 * @date:
 */
public class OrderServiceTests {

    private OrderEntity order;
    private PaymentStatus paymentAccepted;
    private PaymentStatus paymentDeclined;

    @Mock
    OrderRepository orderRepo;

    @Mock
    PaymentService paymentService;

    @InjectMocks
    OrderServiceImpl orderService;

    @BeforeClass
    public void setupAll() {
        MockitoAnnotations.openMocks(this);
        System.out.println("== Order Service Mock Suite Execution Started...");
    }

    @BeforeMethod
    public void setup() {
        System.out.println("Create Order, PaymentStatus...");
        order = createOrder();
        System.out.println("Create Order, PaymentStatus..." + order.getPaymentDetails());
        paymentAccepted = createPaymentStatusAccepted(order.getPaymentDetails());
        paymentDeclined = createPaymentStatusDeclined(order.getPaymentDetails());

    }

    @Test(description = "1. Test for Payment Accepted")
    public void testValidatePaymentAccepted() {
        // Given Order is Ready
        when(orderRepo.saveOrder(order))
                .thenReturn(order);
        when(paymentService.processPayments(order.getPaymentDetails()))
                .thenReturn(paymentAccepted);

        // When Order is Processed for Payment
        OrderEntity processedOrder = orderService.processOrder(order);

        // Then Check the Payment Status as Accepted
        assertEquals(processedOrder.getOrderStatus(), OrderStatus.PAID);
    }

    @Test(description = "2. Test for Payment Declined")
    public void testValidatePaymentDeclined() {
        // Given Order is Ready
        when(orderRepo.saveOrder(order))
                .thenReturn(order);
        when(paymentService.processPayments(order.getPaymentDetails()))
                .thenReturn(paymentDeclined);

        // When Order is Processed for Payment
        OrderEntity processedOrder = orderService.processOrder(order);

        // Then Check the Payment Status as Declined
        assertEquals(processedOrder.getOrderStatus(), OrderStatus.PAYMENT_DECLINED);
    }

    // Other methods remain the same

    /**
     * Returns OrderEntity
     * @return
     */
    public OrderEntity createOrder() {
        return new OrderEntity.Builder()
                .addCustomer(new Customer
                        ("UUID", "John", "Doe", "0123456789"))
                .addOrderItem(new OrderItem
                        ("uuid1", "iPhone 12", 799, "USD", 1))
                .addOrderItem(new OrderItem
                        ("uuid2", "iPhone 12 Pro", 999, "USD", 1))
                .addOrderItem(new OrderItem
                        ("uuid3", "Apple Watch Series 6", 450, "USD", 2))
                .addShippingAddress(new ShippingAddress
                        ("321 Cobblestone Ln,", "", "Edison", "NJ", "", "USA", "08820"))
                .addPaymentType(PaymentType.CREDIT_CARD)
                .addCardDetails(new CardDetails
                        ("1234 5678 9876 5432", "John Doe", 7, 2025, 456, CardType.MASTER))
                .build();

    }

    /**
     * Payment Status - Accepted
     *
     * @param paymentDetails
     * @return
     */
    public PaymentStatus createPaymentStatusAccepted(PaymentDetails paymentDetails) {
        return new PaymentStatus(
                paymentDetails.getTransactionId(),
                paymentDetails.getTransactionDate(),
                "Accepted",
                UUID.randomUUID().toString(),
                LocalDateTime.now(),
                PaymentType.CREDIT_CARD);
    }

    /**
     * Payment Status - Declined
     *
     * @param paymentDetails
     * @return
     */
    public PaymentStatus createPaymentStatusDeclined(PaymentDetails paymentDetails) {
        return new PaymentStatus(
                paymentDetails.getTransactionId(),
                paymentDetails.getTransactionDate(),
                "Declined",
                UUID.randomUUID().toString(),
                LocalDateTime.now(),
                PaymentType.CREDIT_CARD);
    }

}