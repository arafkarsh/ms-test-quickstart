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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.fusion.water.order.domainLayer.models.DeliveryCity;
import io.fusion.water.order.domainLayer.models.OrderEntity;
import io.fusion.water.order.domainLayer.services.DeliveryCityService;
import io.fusion.water.order.domainLayer.services.ShippingService;

/**
 * @author arafkarsh
 *
 */
@Service
public class ShippingServiceImpl implements ShippingService {
	
	@Autowired
	private DeliveryCityService deliveryCityService;

	@Override
	public ArrayList<OrderEntity> shipOrder(ArrayList<OrderEntity> _orderList) {
		return _orderList;
	}

	/**
	 * 
	 */
	public ArrayList<DeliveryCity> getCities(ArrayList<String> cities, String state, String country) {
		ArrayList<DeliveryCity> dCities = new ArrayList<DeliveryCity>();
		if(cities != null && cities.size() > 0) {
			for(String cityName : cities) {
				DeliveryCity city = deliveryCityService.getDeliveryCity(cityName, state, country);
				if(city != null) {
					dCities.add(city);
				}
			}
		}
		return dCities;
	}

	@Override
	public DeliveryCity getCity(String city, String State, String country) {
		return deliveryCityService.getDeliveryCity(city, State, country);
	}
}
