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

package test.fusion.water.order.mockito3.tests;


import static java.lang.invoke.MethodHandles.lookup;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.slf4j.LoggerFactory.getLogger;

import java.time.LocalDateTime;
import java.util.UUID;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;

import io.fusion.water.order.adapters.service.OrderServiceImpl;
import io.fusion.water.order.domainLayer.models.Customer;
import io.fusion.water.order.domainLayer.models.OrderEntity;
import io.fusion.water.order.domainLayer.models.OrderItem;
import io.fusion.water.order.domainLayer.models.OrderStatus;
import io.fusion.water.order.domainLayer.models.PaymentDetails;
import io.fusion.water.order.domainLayer.models.PaymentStatus;
import io.fusion.water.order.domainLayer.models.PaymentType;
import io.fusion.water.order.domainLayer.models.ShippingAddress;
import io.fusion.water.order.domainLayer.services.PaymentService;
import test.fusion.water.order.junit5.annotations.tests.Critical;
import test.fusion.water.order.junit5.annotations.tests.Functional;
import test.fusion.water.order.junit5.annotations.tools.Mockito3;
import test.fusion.water.order.junit5.extensions.TestTimeExtension;
import io.fusion.water.order.domainLayer.services.OrderRepository;

/**
 * Order Service Test
 * 
 * @author arafkarsh
 *
 */

@Mockito3()
@Critical()
@Functional()
@TestMethodOrder(OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(TestTimeExtension.class)
@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {

	static final Logger log = getLogger(lookup().lookupClass());

	private OrderEntity order;
	private PaymentStatus paymentAccepted;
	private PaymentStatus paymentDeclined;
	
	private int counter = 1;

	@Mock
	OrderRepository orderRepo;
	
	@Mock
	PaymentService paymentService;
	
	@InjectMocks
	OrderServiceImpl orderService;
	
	/**
	 * if the @TestInstance(TestInstance.Lifecycle.PER_CLASS)
	 * is available then the method need not be static
	 */
    @BeforeAll
    public void setupAll() {
        System.out.println("== Order Service Mock Suite Execution Started...");
    }
    
    @BeforeEach
    public void setup() {
        System.out.println(counter+". Create Order, PaymentStatus...");
        order = createOrder();
        paymentAccepted = createPaymentStatusAccepted(order.getPaymentDetails());
        paymentDeclined = createPaymentStatusDeclined(order.getPaymentDetails());

    }
    
	@Test
	@DisplayName("Test for Payment Accepted")
	public void testValidatePaymentAccepted() {
		// Given Order is Ready
		when(orderRepo.saveOrder(order))
			.thenReturn(order);
		when(paymentService.processPayments(order.getPaymentDetails()))
			.thenReturn(paymentAccepted);
		// when(paymentService.processPayments(order.getPaymentDetails()))
		// .thenAnswer(new PaymentProcessing());
		
		// When Order is Processed for Payment
		OrderEntity processedOrder = orderService.processOrder(order);
		
		// Then Check the Payment Status as Accepted
		assertEquals(
				OrderStatus.PAID,
				processedOrder.getOrderStatus()
				);
	}
	
	@Test
	@DisplayName("Test for Payment Declined")
	public void testValidatePaymentDeclined() {
		// Given Order is Ready
		when(orderRepo.saveOrder(order))
			.thenReturn(order);
		when(paymentService.processPayments(order.getPaymentDetails()))
			.thenReturn(paymentDeclined);
		
		// When Order is Processed for Payment
		OrderEntity processedOrder = orderService.processOrder(order);
		
		// Then Check the Payment Status as Declined
		assertEquals(
				OrderStatus.PAYMENT_DECLINED,
				processedOrder.getOrderStatus()
				);
	}
 
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
				.build();
		
	}
	
	/**
	 * Payment Status - Accepted
	 * 
	 * @param _paymentDetails
	 * @return
	 */
	public PaymentStatus createPaymentStatusAccepted(PaymentDetails _paymentDetails) {
		return new PaymentStatus(
				_paymentDetails.getTransactionId(), 
				_paymentDetails.getTransactionDate(), 
				"Accepted", 
				UUID.randomUUID().toString(), 
				LocalDateTime.now(), 
				PaymentType.CREDIT_CARD);
	}
	
	/**
	 * Payment Status - Declined
	 * 
	 * @param _paymentDetails
	 * @return
	 */
	public PaymentStatus createPaymentStatusDeclined(PaymentDetails _paymentDetails) {
		return new PaymentStatus(
				_paymentDetails.getTransactionId(), 
				_paymentDetails.getTransactionDate(), 
				"Declined", 
				UUID.randomUUID().toString(), 
				LocalDateTime.now(), 
				PaymentType.CREDIT_CARD);
	}
	
}
