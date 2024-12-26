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

/**
 * 
 */
package io.fusion.water.order.adapters.service;

import java.util.ArrayList;
import java.util.List;

import io.fusion.water.order.domain.models.OrderStatus;
import io.fusion.water.order.utils.Std;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.fusion.water.order.domain.models.DeliveryCity;
import io.fusion.water.order.domain.models.OrderEntity;
import io.fusion.water.order.domain.services.DeliveryCityService;
import io.fusion.water.order.domain.services.ShippingService;

/**
 * @author arafkarsh
 *
 */
@Service
public class ShippingServiceImpl implements ShippingService {
	
	// Autowired using the Constructor Injection
	private DeliveryCityService deliveryCityService;

	public ShippingServiceImpl() {
	}
	/**
	 * Autowired using the Constructor Injection
	 * @param deliveryCityService
	 */
	@Autowired
	public ShippingServiceImpl(DeliveryCityService deliveryCityService) {
		this.deliveryCityService = deliveryCityService;
	}

	/**
	 * Ship Order
	 * Only Order with Ready for Shipment status can be shipped
	 * @param orderList
	 * @return
	 */
	@Override
	public List<OrderEntity> shipOrder(List<OrderEntity> orderList) {
		Std.println(">> INSIDE Shipping Orders... ");
		if(orderList == null || orderList.isEmpty()) {
			return orderList;
		}
		Std.println(">> 2. Shipping Orders... Size = "+orderList.size());
		List<OrderEntity> ordersInTransit = new ArrayList<>();
		for(OrderEntity order : orderList) {
			if(order.getOrderStatus().equals(OrderStatus.READY_FOR_SHIPMENT)) {
				order.orderShipped();
				ordersInTransit.add(order);
				Std.println(">> 2.1 Order "+order.getOrderId()+" is SHIPPED.");
			} else {
				Std.println(">> 2.1 Order "+order.getOrderId()+" is not in READY_FOR_SHIPMENT Status. Cannot Ship the Order. Current Status = "+order.getOrderStatus());
			}
		}
		return ordersInTransit;
	}

	/**
	 * Get Delivery Cities
	 * @param cities
	 * @param state
	 * @param country
	 * @return
	 */
	public List<DeliveryCity> getCities(List<String> cities, String state, String country) {
		ArrayList<DeliveryCity> dCities = new ArrayList<>();
		if(cities == null || cities.isEmpty()) {
			return dCities;
		}
		for(String cityName : cities) {
			DeliveryCity city = deliveryCityService.getDeliveryCity(cityName, state, country);
			Std.println("ShippingServiceImpl.getCities(cities,state,country) = "+city);
			if(city != null) {
				dCities.add(city);
			}
		}
		return dCities;
	}

	/**
	 * Get Delivery City
	 * @param city
	 * @param state
	 * @param country
	 * @return
	 */
	@Override
	public DeliveryCity getCity(String city, String state, String country) {
		return deliveryCityService.getDeliveryCity(city, state, country);
	}
}
