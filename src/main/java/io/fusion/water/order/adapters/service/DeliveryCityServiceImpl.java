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

import java.util.Collection;
import java.util.HashMap;

import org.springframework.stereotype.Service;

import io.fusion.water.order.domain.models.DeliveryCity;
import io.fusion.water.order.domain.services.DeliveryCityService;

/**
 * 
 * @author arafkarsh
 *
 */
@Service
public class DeliveryCityServiceImpl implements DeliveryCityService {
	
	private HashMap<String, DeliveryCity> cities;

	/**
	 * DeliveryCity Service Impl
	 */
	public DeliveryCityServiceImpl() {
		cities = new HashMap<>();
	}
	
	/**
	 * Add Delivery City
	 * 
	 * @param city
	 * @param state
	 * @param country
	 * @return
	 */
	public DeliveryCityService addCity(String city, String state, String country) {
		return addCity(new DeliveryCity( city,  state,  country, "00000"));
	}
	
	/**
	 * Add Delivery City
	 * 
	 * @param city
	 * @param state
	 * @param country
	 * @param zip
	 * @return
	 */
	public DeliveryCityService addCity(String city, String state, String country, String zip) {
		return addCity(new DeliveryCity( city,  state,  country, zip));
	}
	
	/**
	 * Add Delivery City
	 * 
	 * @param deliveryCity
	 * @return
	 */
	public DeliveryCityService addCity(DeliveryCity deliveryCity) {
		cities.put(deliveryCity.getCityKey(), deliveryCity);
		return this;
	}
	
	@Override
	/**
	 * Returns Delivery City
	 * 
	 * @param city
	 * @param state
	 * @param country
	 * @return
	 */
	public DeliveryCity getDeliveryCity(String city, String state, String country) {
		return cities.get(DeliveryCity.createCityKey(city, state, country));
	}
	
	/**
	 * Returns Delivery City
	 * 
	 * @param cityName
	 * @return
	 */
	public DeliveryCity getDeliveryCity(String cityName) {
		if(cityName == null) {
			return null;
		}
		for(DeliveryCity city : cities.values()) {
			if(city.getCityName().equalsIgnoreCase(cityName)) {
				return city;
			}
		}
		return null;
	}
	
	/**
	 * Returns All Delivery Cities
	 * @return
	 */
	public Collection<DeliveryCity> getDeliveryCities() {
		return cities.values();
	}
}
