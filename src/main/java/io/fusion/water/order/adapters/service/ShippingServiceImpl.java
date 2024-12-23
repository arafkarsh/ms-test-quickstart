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

	/**
	 * Autowired using the Constructor Injection
	 * @param deliveryCityService
	 */
	public ShippingServiceImpl(DeliveryCityService deliveryCityService) {
		this.deliveryCityService = deliveryCityService;
	}

	/**
	 * Ship Order
	 * @param orderList
	 * @return
	 */
	@Override
	public List<OrderEntity> shipOrder(List<OrderEntity> orderList) {
		return orderList;
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
		if(cities != null && cities.isEmpty()) {
			for(String cityName : cities) {
				DeliveryCity city = deliveryCityService.getDeliveryCity(cityName, state, country);
				if(city != null) {
					dCities.add(city);
				}
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
