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

package test.fusion.water.order.junit.mockito3.tests;

// Mockito
import static java.lang.invoke.MethodHandles.lookup;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import org.mockito.junit.jupiter.MockitoExtension;
// Java
import static org.slf4j.LoggerFactory.getLogger;
import org.slf4j.Logger;
// JUnit 5
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
// Custom
import test.fusion.water.order.junit.junit5.annotations.tests.Critical;
import test.fusion.water.order.junit.junit5.annotations.tests.Functional;
import test.fusion.water.order.junit.junit5.annotations.tools.Mockito3;
import test.fusion.water.order.junit.junit5.extensions.TestTimeExtension;
import io.fusion.water.order.domain.models.*;
import io.fusion.water.order.domain.services.OrderRepository;
import io.fusion.water.order.domain.services.PaymentService;
import io.fusion.water.order.adapters.service.OrderServiceImpl;
import test.fusion.water.order.utils.OrderMock;

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
	
	private int counter = 1;

	@Mock
	OrderRepository orderRepo;
	
	@Mock
	PaymentService paymentService;

	// Order Service needs OrderRepository and PaymentService
	// 1. Mocks are created for these two (OrderRepository, PaymentService) services
	// 2. Inject these mocks into OrderServiceImpl
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
		order = OrderMock.createOrder1();
	}

	@DisplayName("1. Test for Payment Accepted")
	@Order(1)
	@Test
	void testValidatePaymentAccepted() {
		// Given Order is Ready
		PaymentStatus paymentAccepted = OrderMock.paymentAccepted(order);

		// When
		when(orderRepo.saveOrder(order))
			.thenReturn(order);
		when(paymentService.processPayments(order.getPaymentDetails()))
			.thenReturn(paymentAccepted);
		OrderEntity processedOrder = orderService.processOrder(order);
		
		// Then Check the Payment Status as Accepted
		assertEquals(
				OrderStatus.PAID,
				processedOrder.getOrderStatus()
				);
	}

	@DisplayName("2. Test for Payment Declined")
	@Order(2)
	@Test
	void testValidatePaymentDeclined() {
		// Given Order is Ready
		PaymentStatus paymentDeclined = OrderMock.paymentDeclined(order.getPaymentDetails());

		// When
		when(orderRepo.saveOrder(order))
			.thenReturn(order);
		when(paymentService.processPayments(order.getPaymentDetails()))
			.thenReturn(paymentDeclined);
		OrderEntity processedOrder = orderService.processOrder(order);
		
		// Then Check the Payment Status as Declined
		assertEquals(
				OrderStatus.PAYMENT_DECLINED,
				processedOrder.getOrderStatus()
				);
	}

	@DisplayName("3. Process Order with Verification")
	@Order(3)
	@Test
	void testPlaceOrder() {
		// Given Order is Ready
		PaymentStatus paymentAccepted = OrderMock.paymentAccepted(order);

		// When
		when(orderRepo.saveOrder(order)).thenReturn(order);
		when(paymentService.processPayments(order.getPaymentDetails()))
				.thenReturn(paymentAccepted);

		orderService.processOrder(order);

		// Then
		assertNotNull(order);
		assertNotNull(order.getPaymentDetails());
		assertNotNull(order.getOrderItems());

		// Verify
		verify(orderRepo).saveOrder(order); // Ensure the order was saved
		verify(paymentService).processPayments(any()); // Ensure payment was processed
	}

	/**
	 * if the @TestInstance(TestInstance.Lifecycle.PER_CLASS)
	 * is available then the method need not be static
	 */
	@AfterEach
	public void tearDown() {
		System.out.println(counter+". Should Execute After Each Test");
		counter++;
	}

	/**
	 * if the @TestInstance(TestInstance.Lifecycle.PER_CLASS)
	 * is available then the method need not be static
	 */
	@AfterAll
	public void tearDownAll() {
		System.out.println("== Order tests Suite Execution Completed...");
	}
	
}
