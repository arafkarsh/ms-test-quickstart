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

// Java
import java.util.ArrayList;
import java.util.List;
import static java.lang.invoke.MethodHandles.lookup;
import static org.junit.jupiter.api.Assertions.*;
import static org.slf4j.LoggerFactory.getLogger;
import org.slf4j.Logger;
// Mockito
import static org.mockito.Mockito.when;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
// JUnit
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
// Custom
import io.fusion.water.order.adapters.service.OrderServiceImpl;
import io.fusion.water.order.adapters.service.PackingServiceImpl;
import io.fusion.water.order.adapters.service.WarehouseServiceImpl;
import io.fusion.water.order.domain.services.PaymentService;
import test.fusion.water.order.junit.junit5.annotations.tests.Critical;
import test.fusion.water.order.junit.junit5.annotations.tests.Functional;
import test.fusion.water.order.junit.junit5.annotations.tools.Mockito3;
import test.fusion.water.order.junit.junit5.extensions.TestTimeExtension;
import io.fusion.water.order.domain.services.OrderRepository;
import test.fusion.water.order.utils.OrderMock;
import io.fusion.water.order.adapters.service.ShippingServiceImpl;
import io.fusion.water.order.domain.models.OrderEntity;
import io.fusion.water.order.domain.models.OrderStatus;

/**
 * Warehouse Service Test
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
class WarehouseServiceTest {

	static final Logger log = getLogger(lookup().lookupClass());

	private List<OrderEntity> orderReadyList;
	private int counter = 1;

	@Spy
	private PackingServiceImpl packingService;

	@Spy
	private ShippingServiceImpl shippingService;

	@InjectMocks
	private WarehouseServiceImpl warehouseService;

	@Mock
	private OrderRepository orderRepo;

	@Mock
	private PaymentService paymentService;

	@InjectMocks
	private OrderServiceImpl orderService;

	/**
	 * if the @TestInstance(TestInstance.Lifecycle.PER_CLASS)
	 * is available then the method need not be static
	 */
	@BeforeAll
	public void setupAll() {
		System.out.println("== Warehouse Mock Suite Execution Started...");
	}

	@BeforeEach
	public void setup() {
		System.out.println(counter + ". Create Order List");
		orderReadyList = new ArrayList<OrderEntity>();
	}

	@DisplayName("1.1 Check All the Paid Orders Ready for Packaging")
	@ParameterizedTest
	@MethodSource("createOrder")
	@Order(1)
	@Tag("critical")
	void testForPaidOrders(OrderEntity order) {
		// Setup the Order Repo and Payment Service with Mocks
		when(orderRepo.saveOrder(order)).thenReturn(order);
		// Accept Payment
		when(paymentService.processPayments(order.getPaymentDetails()))
				.thenReturn(OrderMock.paymentAccepted(order));

		// When Order is Processed for Payment
		OrderEntity processedOrder = orderService.processOrder(order);

		// Then Payment Service will return as Payment Accepted
		assertTrue(processedOrder.isCustomerAvailable());
		assertNotNull(processedOrder.getOrderStatus());
		assertEquals(OrderStatus.PAID, processedOrder.getOrderStatus());
		orderReadyList.add(processedOrder);
	}


	@DisplayName("1.2 Test for Warehouse Ready for Shipping")
	@Order(2)
	@Tag("critical")
	@Test
	void testOrderForShipping() {
		// Given Order is Ready for Shipping
		List<OrderEntity> orders = OrderMock.createOrder();
		List<OrderEntity> orderPacked = OrderMock.createPackedOrders(orders);
		when(packingService.packOrders(orders)).thenReturn(orderPacked);
		// Give Order is Ready to be shipped
		List<OrderEntity> orderShipped = OrderMock.createShippedOrders(orderPacked);
		when(shippingService.shipOrder(orderPacked)).thenReturn(orderShipped);

		// Prepare the Order for Shipping
		List<OrderEntity> processOrders = warehouseService.processOrders(orderShipped);

		// Then Check if all orders are Ready for Shipment
		for (OrderEntity order : processOrders) {
			assertEquals(OrderStatus.ORDER_SHIPPED, order.getOrderStatus());
		}
		assertEquals(orders.size(), orderShipped.size());
		orderReadyList = processOrders;
	}

	@DisplayName("1.3 Test for Warehouse Order Shipped")
	@Order(3)
	@Tag("critical")
	@Test
	void testOrderShipped() {
		// Given Order is Ready for Shipping
		List<OrderEntity> orders = OrderMock.createOrderList();
		List<OrderEntity> orderPacked = OrderMock.createPackedOrders(orders);

		// Prepare the Order for Shipping
		List<OrderEntity> procesedOrders = warehouseService.processOrders(orderPacked);

		// Then Check if all orders are IN Transit
		for (OrderEntity order : procesedOrders) {
			assertEquals(OrderStatus.ORDER_SHIPPED, order.getOrderStatus());
		}
		assertEquals(orderPacked.size(), procesedOrders.size());
		orderReadyList = procesedOrders;
	}

	@AfterEach
	public void tearDown() {
		counter++;
		printLogs(orderReadyList);
	}

	/**
	 * if the @TestInstance(TestInstance.Lifecycle.PER_CLASS)
	 * is available then the method need not be static
	 */
	@AfterAll
	public void tearDownAll() {
		System.out.println("== Warehouse Mock Suite Execution Completed...");
	}

	// ================================================================================
	// Setup Test Data for the Mock
	// ================================================================================

	public void printLogs(List<OrderEntity> orderList) {
		System.out.println("Print Logs: Orders = " + orderList.size());
		for (OrderEntity order : orderList) {
			System.out.println("ID = " + order.getOrderId() + " | Status = " + order.getOrderStatus());
		}
	}

	public ArrayList<OrderEntity> createOrder() {
		return OrderMock.createOrderList();
	}
}

