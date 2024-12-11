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
import io.fusion.water.order.domainLayer.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.fusion.water.order.domainLayer.services.OrderRepository;
import io.fusion.water.order.domainLayer.services.OrderService;
import io.fusion.water.order.domainLayer.services.PaymentService;

/**
 * Order Service
 *
 * @author arafkarsh
 *
 */
@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	private OrderRepository orderRepo;

	@Autowired
	private PaymentService paymentService;

	/**
	 * For Auto wiring the service with Order Repo and Payment Service
	 * @param _orderRepo
	 * @param _paymentService
	 */
	public OrderServiceImpl(OrderRepository _orderRepo, PaymentService _paymentService) {
		orderRepo = _orderRepo;
		paymentService = _paymentService;
	}

	// THIS IS ONLY TO DO THE DEMO OF PACT
	@Autowired
	private ExternalGateWay externalGateWay;

	@Override
	public OrderEntity getOrderById(String _id) {
		// return orderRepo.getOrderById(_id);
		return mockGetOrderById(_id);
	}

	/**
	 * This is Only for PACT DEMO
	 * @param _order
	 * @return
	 */
	public OrderEntity saveOrderExternal(OrderEntity _order) {
		return externalGateWay.saveOrder(_order);
	}

	@Override
	public OrderEntity processOrder(OrderEntity _order) {
		// Save order
		OrderEntity order = orderRepo.saveOrder(_order);
		if(order != null) {
			// Make Payments
			PaymentStatus payStatus = paymentService.processPayments(
					order.getPaymentDetails());
			// Update Payment Status
			order.setPaymentStatus(payStatus);
		}
		return order;
	}

	@Override
	public OrderEntity cancelOrder(OrderEntity _order) {
		return orderRepo.cancelOrder(_order);
	}

	@Override
	public OrderEntity cancelOrder(String _id) {
		return orderRepo.cancelOrder(_id);
	}

	@Override
	public OrderEntity prepareOrder(OrderEntity _order) {
		return orderRepo.prepareOrder(_order);
	}

	/**
	 * Update Order Status
	 */
	public OrderEntity updateOrderStatus(String _id, String _status) {
		// Fetch Order based on Order Id
		// OrderEntity order = orderRepo.getOrderById(_id);
		OrderEntity order = mockGetOrderById(_id);

		// Check Order Status and set the the status in the Order
		if(_status.equalsIgnoreCase(OrderStatus.READY_FOR_SHIPMENT.name())) {
			order.orderReadyForShipment();
		} else if (_status.equalsIgnoreCase(OrderStatus.PAYMENT_EXPECTED.name())) {
			order.orderWaitingForPayment();
		}
		return orderRepo.saveOrder(order);
	}

	/**
	 * Adding a New Method to test DiffBlue Cover
	 * Ship Order
	 * @param _id
	 * @return
	 */
	public OrderEntity shipOrder(String _id) {
		// Fetch Order based on Order Id
		OrderEntity order =  mockGetOrderById(_id);
		order.orderReadyForShipment();
		return order;
	}

	@Override
	public PaymentStatus processPayments(PaymentDetails _paymentDetails) {
		return paymentService.processPayments(_paymentDetails);
	}

	/**
	 * This is a MOCK API for testing Only.
	 * In a real world scenario you will be retrieving the data from a database based on
	 * the input _orderId
	 *
	 * @param _orderId
	 * @return
	 */
	private OrderEntity mockGetOrderById(String _orderId) {
		return new OrderEntity.Builder()
				.addCustomer(new Customer
						("UUID", "John", "Doe", "0123456789"))
				// Set Order ID
				.setOrderId(_orderId)
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
