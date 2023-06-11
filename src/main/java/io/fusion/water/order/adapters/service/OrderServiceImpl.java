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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.fusion.water.order.domainLayer.models.OrderEntity;
import io.fusion.water.order.domainLayer.models.OrderStatus;
import io.fusion.water.order.domainLayer.models.PaymentDetails;
import io.fusion.water.order.domainLayer.models.PaymentStatus;
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
	OrderRepository orderRepo;
	
	@Autowired
	PaymentService paymentService;
	
	@Override
	public OrderEntity getOrderById(String _id) {
		return orderRepo.getOrderById(_id);
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
		OrderEntity order = orderRepo.getOrderById(_id);
		// Check Order Status and set the the status in the Order
		if(_status.equalsIgnoreCase(OrderStatus.READY_FOR_SHIPMENT.name())) {
			order.orderReadyForShipment();
		}
		return orderRepo.saveOrder(order);
	}

	@Override
	public PaymentStatus processPayments(PaymentDetails _paymentDetails) {
		return paymentService.processPayments(_paymentDetails);
	}

}
