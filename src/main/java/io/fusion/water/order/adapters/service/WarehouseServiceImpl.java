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

import java.util.List;

import org.springframework.stereotype.Service;

import io.fusion.water.order.domain.models.OrderEntity;
import io.fusion.water.order.domain.services.PackingService;
import io.fusion.water.order.domain.services.ShippingService;
import io.fusion.water.order.domain.services.WarehouseService;

/**
 * Warehouse Service
 * 
 * @author arafkarsh
 *
 */
@Service
public class WarehouseServiceImpl implements WarehouseService {
	
	//  Autowired using the Constructor Injection
	private PackingService packingService;
	
	// Autowired using the Constructor Injection
	private ShippingService shippingService;

	/**
	 * Autowired using the Constructor Injection
	 * @param packingService
	 * @param shippingService
	 */
	public WarehouseServiceImpl(PackingService packingService, ShippingService shippingService) {
		this.packingService = packingService;
		this.shippingService = shippingService;
	}

	/**
	 * Process Orders
	 * @param orderList
	 * @return
	 */
	@Override
	public List<OrderEntity> processOrders(List<OrderEntity> orderList) {
		if(orderList == null || orderList.isEmpty()) {
			return orderList;
		}
		List<OrderEntity> orders = packingService.packOrders(orderList);
		for(OrderEntity order : orders) {
			order.orderReadyForShipment();
		}
		return shippingService.shipOrder(orders);
	}
}
