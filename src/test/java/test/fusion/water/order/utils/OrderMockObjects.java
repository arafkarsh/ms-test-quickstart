package test.fusion.water.order.utils;

import io.fusion.water.order.domain.models.*;

/**
 * @author: Araf Karsh Hamid
 * @version:
 * @date:
 */
public class OrderMockObjects {

    /**
     * Returns the OrderEntity with Order ID as an input
     *
     * @param orderId
     * @return
     */
    public static OrderEntity mockGetOrderById(String orderId) {
        return new OrderEntity.Builder()
                .addCustomer(new Customer
                        ("UUID", "John", "Doe", "0123456789"))
                .setOrderId(orderId)
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
                        ("XXXX XXXX XXXX 5432", "John Doe", 7, 2025, 0, CardType.MASTER))
                .build();
    }
}
