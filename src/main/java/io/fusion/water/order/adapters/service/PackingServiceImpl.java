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

import java.util.ArrayList;
import java.util.List;

import io.fusion.water.order.domain.models.OrderStatus;
import org.springframework.stereotype.Service;

import io.fusion.water.order.domain.models.OrderEntity;
import io.fusion.water.order.domain.services.PackingService;

/**
 * Packing Service
 * 
 * @author arafkarsh
 *
 */
@Service
public class PackingServiceImpl implements PackingService {

	@Override
	public List<OrderEntity> packOrders(List<OrderEntity> orderList) {
		System.out.println(">> INSIDE Packing Orders...");

		if(orderList == null || orderList.isEmpty()) {
			return orderList;
		}
		System.out.println(">> 1. Packing Orders... Size = "+orderList.size());
		List<OrderEntity> ordersReadyForShipment = new ArrayList<>();
		for(OrderEntity order : orderList) {
			if(order.getOrderStatus().equals(OrderStatus.ORDER_PACKED)) {
				order.orderReadyForShipment();
				ordersReadyForShipment.add(order);
				System.out.println(">> 1.1 Order "+order.getOrderId()+" is PACKED. Ready for Shipment.");
			} else {
				System.out.println(">> 1.1 Order "+order.getOrderId()+" is not in PACKED Status. Cannot Ship the Order. Current Status = "+order.getOrderStatus());
			}
		}
		return ordersReadyForShipment;
	}
}
