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


import static java.lang.invoke.MethodHandles.lookup;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import static org.slf4j.LoggerFactory.getLogger;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import io.fusion.water.order.domain.models.*;
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
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;

import io.fusion.water.order.adapters.service.OrderServiceImpl;
import io.fusion.water.order.adapters.service.PackingServiceImpl;
import io.fusion.water.order.adapters.service.WarehouseServiceImpl;
import io.fusion.water.order.domain.services.PaymentService;
import io.fusion.water.order.domain.services.ShippingService;
import test.fusion.water.order.junit.junit5.annotations.tests.Critical;
import test.fusion.water.order.junit.junit5.annotations.tests.Functional;
import test.fusion.water.order.junit.junit5.annotations.tools.Mockito3;
import test.fusion.water.order.junit.junit5.extensions.TestTimeExtension;
import io.fusion.water.order.domain.services.OrderRepository;

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
public class WarehouseServiceTest {

	static final Logger log = getLogger(lookup().lookupClass());

	private ArrayList<OrderEntity> orderList;
	private ArrayList<OrderEntity> orderReadyList;
	private int counter = 1;

	@Spy
	PackingServiceImpl packingService;
	
	@Mock
	ShippingService shippingService;
	
	@InjectMocks
	WarehouseServiceImpl  warehouseService;
	
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
        System.out.println("== Warehouse Mock Suite Execution Started...");
    }
    
    @BeforeEach
    public void setup() {
        System.out.println(counter+". Create Order List");
        orderList = createOrder();
        orderReadyList = new ArrayList<OrderEntity>();
    }
    
    @DisplayName("1.1 Check All the Paid Orders Ready for Packaging")
    @ParameterizedTest
    @MethodSource("createOrder")
    @Order(1)
    @Tag("critical")
	void testForOrderPackaging(OrderEntity order) {
    	PaymentStatus paymentAccepted = new PaymentStatus(
        		order.getPaymentDetails().getTransactionId(), 
        		order.getPaymentDetails().getTransactionDate(), 
				"Accepted", 
				UUID.randomUUID().toString(), 
				LocalDateTime.now(), 
				PaymentType.CREDIT_CARD);

    	// Setup the Order Repo and Payment Service with Mocks
		when(orderRepo.saveOrder(order)).thenReturn(order);
		when(paymentService.processPayments(order.getPaymentDetails()))
			.thenReturn(paymentAccepted);
	
		// When Order is Processed for Payment
		OrderEntity processedOrder = orderService.processOrder(order);
		
		// Then Payment Service will return as Payment Accepted
    	assertTrue(processedOrder.isCustomerAvailable());
    	assertEquals(OrderStatus.PAID, processedOrder.getOrderStatus());
    	
    	orderReadyList.add(order);
    }

    
	@Test
	@DisplayName("1.2 Test for Warehouse Ready for Shipping")
    @Order(2)
    @Tag("critical")
	void testOrderForShipping() {
		printLogs();
		// Given Order is Ready for Shipping
		when(packingService.packOrders(orderReadyList)).thenReturn(orderReadyList);
		when(shippingService.shipOrder(orderReadyList)).thenReturn(orderReadyList);

		// Prepare the Order for Shipping
		List<OrderEntity> orders = warehouseService.processOrders(orderReadyList);
		
		// Then Check if all orders are Ready for Shipment
		for(OrderEntity order : orders)  {
			assertEquals(OrderStatus.READY_FOR_SHIPMENT, order.getOrderStatus());
		}
		assertEquals(orders.size(), orderReadyList.size());
	}
	
	@Test
	@DisplayName("1.3 Test for Warehouse Order Shipped")
    @Order(3)
    @Tag("critical")
	void testOrderInTransit() {
		printLogs();
		// Given Order is Ready for Shipping
		when(packingService.packOrders(orderReadyList)).thenReturn(orderReadyList);
		// Mock Order Ready to be shipped
		ArrayList<OrderEntity> shipOrders = shipOrder(orderReadyList);
		when(shippingService.shipOrder(orderReadyList)).thenReturn(shipOrders);
		
		// Prepare the Order for Shipping
		List<OrderEntity> orders = warehouseService.processOrders(orderReadyList);
		
		// Then Check if all orders are IN Transit
		for(OrderEntity order : orders) {
			assertEquals(OrderStatus.IN_TRANSIT, order.getOrderStatus());
		}
		assertEquals(orders.size(), shipOrders.size());
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
	
    public void printLogs() {
		printLogs(orderList);
		processOrder(orderList);
		printLogs(orderReadyList);
	}
	
	public void printLogs(ArrayList<OrderEntity> orderList) {
		for(OrderEntity order : orderList)  {
			System.out.println("id="+order.getOrderId()+"|st="+order.getOrderStatus());
		}
	}
	
	public ArrayList<OrderEntity> shipOrder(ArrayList<OrderEntity> orderList) {
		ArrayList<OrderEntity> shipOrders = new ArrayList<OrderEntity>();
		for(OrderEntity order : orderList) {
			order.orderInTransit();
			shipOrders.add(order);
		}
		return shipOrders;
	}
	
	public ArrayList<OrderEntity> processOrder(ArrayList<OrderEntity> orderList) {
		for(OrderEntity order : orderList) {
	    	PaymentStatus paymentAccepted = new PaymentStatus(
	        		order.getPaymentDetails().getTransactionId(), 
	        		order.getPaymentDetails().getTransactionDate(), 
					"Accepted", 
					UUID.randomUUID().toString(), 
					LocalDateTime.now(), 
					PaymentType.CREDIT_CARD);
	    	order.setPaymentStatus(paymentAccepted);
	    	orderReadyList.add(order);
		}
		return orderReadyList;
	}
 
	/**
	 * Returns OrderEntity
	 * @return
	 */
	public static ArrayList<OrderEntity>  createOrder() {
		ArrayList<OrderEntity> orderList = new ArrayList<OrderEntity>();
		orderList.add(new OrderEntity.Builder()
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
				.waitingForPayment()
				.build());
		
		orderList.add(new OrderEntity.Builder()
				.addCustomer(new Customer
    					("UUID", "Jane", "Doe", "0123456789"))
				.addOrderItem(new OrderItem
						("uuid2", "iPhone 12 Pro Max", 1199, "USD", 1))
				.addOrderItem(new OrderItem
						("uuid3", "Apple Watch Series 6", 450, "USD", 2))
				.addShippingAddress(new ShippingAddress
						("323 Cobblestone Ln,", "", "Edison", "NJ", "", "USA", "08820"))
				.addPaymentType(PaymentType.DEBIT_CARD)
				.addCardDetails(new CardDetails
						("1234 5678 9876 5432", "John Doe", 7, 2025, 456, CardType.MASTER))
				.waitingForPayment()
				.build());
		
		orderList.add(new OrderEntity.Builder()
				.addCustomer(new Customer
    					("UUID", "Ann", "Doe", "0123456789"))
				.addOrderItem(new OrderItem
						("uuid2", "iPhone 12 Mini", 699, "USD", 1))
				.addOrderItem(new OrderItem
						("uuid3", "Apple Watch Series 6", 450, "USD", 2))
				.addShippingAddress(new ShippingAddress
						("323 Cobblestone Ln,", "", "Edison", "NJ", "", "USA", "08820"))
				.addPaymentType(PaymentType.GOOGLE_PAY)
				.addCardDetails(new CardDetails
						("1234 5678 9876 5432", "John Doe", 7, 2025, 456, CardType.MASTER))
				.waitingForPayment()
				.build());
		
		return orderList;
		
	}
	
}
