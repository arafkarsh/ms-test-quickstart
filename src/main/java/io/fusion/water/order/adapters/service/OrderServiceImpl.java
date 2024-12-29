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

package io.fusion.water.order.adapters.service;

import io.fusion.water.order.adapters.external.ExternalGateWay;
import io.fusion.water.order.domain.models.*;
import jakarta.resource.ResourceException;
import org.springframework.stereotype.Service;

import io.fusion.water.order.domain.services.OrderRepository;
import io.fusion.water.order.domain.services.OrderService;
import io.fusion.water.order.domain.services.PaymentService;

/**
 * Order Service
 *
 * @author arafkarsh
 *
 */
@Service
public class OrderServiceImpl implements OrderService {

	// Autowiring using Constructor Injection
	private OrderRepository orderRepo;

	// Autowiring using Constructor Injection
	private PaymentService paymentService;

	// THIS IS ONLY TO DO THE DEMO OF PACT
	// Autowiring using Constructor Injection
	private ExternalGateWay externalGateWay;

	/**
	 * For Auto wiring the service with Order Repo and Payment Service
	 * @param orderRepo
	 * @param paymentService
	 */
	public OrderServiceImpl(OrderRepository orderRepo, PaymentService paymentService,
							ExternalGateWay externalGateWay) {
		this.orderRepo = orderRepo;
		this.paymentService = paymentService;
		this.externalGateWay = externalGateWay;
	}

	@Override
	public OrderEntity getOrderById(String id) {
		// return orderRepo.getOrderById(_id);
		return mockGetOrderById(id);
	}

	/**
	 * This is Only for PACT DEMO
	 * @param order
	 * @return
	 */
	public OrderEntity saveOrderExternal(OrderEntity order) {
		return externalGateWay.saveOrder(order);
	}

	@Override
	public OrderEntity processOrder(OrderEntity orderEntity) {
		// Save order
		OrderEntity order = orderRepo.saveOrder(orderEntity);
		if(order != null) {
			// Make Payments
			PaymentStatus payStatus = paymentService.processPayments(
					order.getPaymentDetails());
			// Update Payment Status
			if(payStatus == null) {
				throw new RuntimeException("Payment Failed - Null Status");
			}
			order.setPaymentStatus(payStatus);
		}
		return order;
	}

	@Override
	public OrderEntity cancelOrder(OrderEntity order) {
		return orderRepo.cancelOrder(order);
	}

	@Override
	public OrderEntity cancelOrder(String id) {
		return orderRepo.cancelOrder(id);
	}

	@Override
	public OrderEntity prepareOrder(OrderEntity order) {
		return orderRepo.prepareOrder(order);
	}

	/**
	 * Update Order Status
	 */
	public OrderEntity updateOrderStatus(String id, String status) {
		// Fetch Order based on Order Id
		// OrderEntity order = orderRepo.getOrderById(_id);
		OrderEntity order = mockGetOrderById(id);

		// Check Order Status and set the the status in the Order
		if(status.equalsIgnoreCase(OrderStatus.READY_FOR_SHIPMENT.name())) {
			order.orderReadyForShipment();
		} else if (status.equalsIgnoreCase(OrderStatus.PAYMENT_EXPECTED.name())) {
			order.orderWaitingForPayment();
		}
		return orderRepo.saveOrder(order);
	}

	/**
	 *
	 * Ship Order
	 * @param id
	 * @return
	 */
	public OrderEntity shipOrder(String id) {
		// Fetch Order based on Order Id
		OrderEntity order =  mockGetOrderById(id);
		order.orderReadyForShipment();
		return order;
	}

	/**
	 * Adding a New Method to test DiffBlue Cover
	 * @param id
	 * @param status
	 * @return
	 * @throws ResourceException
	 */
	public OrderEntity trackOrder(String id, String status)  {
		// Fetch Order based on Order Id
		OrderEntity order =  mockGetOrderById(id);
		if(order == null) {
			throw new RuntimeException("Order Not Found");
		}
		// Set Order Status
		if(status.equalsIgnoreCase(OrderStatus.IN_TRANSIT.name())) {
			order.orderReadyForShipment();
		} else if(status.equalsIgnoreCase(OrderStatus.CANCELLED.name())) {
			order.orderDelivered();
		} else if(status.equalsIgnoreCase(OrderStatus.DELIVERED.name())) {
			order.orderDelivered();
		} else if(status.equalsIgnoreCase(OrderStatus.RETURNED.name())) {
			order.orderReturned();
		} else {
			throw new RuntimeException("Invalid Status");
		}
		// Save Order
		return orderRepo.saveOrder(order);
	}

	@Override
	public PaymentStatus processPayments(PaymentDetails paymentDetails) {
		return paymentService.processPayments(paymentDetails);
	}

	/**
	 * This is a MOCK API for testing Only.
	 * In a real world scenario you will be retrieving the data from a database based on
	 * the input _orderId
	 *
	 * @param orderId
	 * @return
	 */
	private OrderEntity mockGetOrderById(String orderId) {
		return new OrderEntity.Builder()
				.addCustomer(new Customer
						("UUID", "John", "Doe", "0123456789"))
				// Set Order ID
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
