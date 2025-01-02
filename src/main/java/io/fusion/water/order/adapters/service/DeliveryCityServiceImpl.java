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

import io.fusion.water.order.utils.Std;
import org.springframework.stereotype.Service;

import io.fusion.water.order.domain.models.DeliveryCity;
import io.fusion.water.order.domain.services.DeliveryCityService;
import org.springframework.web.context.annotation.RequestScope;

/**
 * 
 * @author arafkarsh
 *
 */
@Service
@RequestScope
public class DeliveryCityServiceImpl implements DeliveryCityService {

	// Due to this class variable this Service needs to be Request Scoped
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
		// Add Cities with City Name, State Name and Country Name
		cities.put(deliveryCity.getCityKey(), deliveryCity);
		// Add City with City Name and State Name
		DeliveryCity dc2 = new DeliveryCity(deliveryCity.getCityName(), deliveryCity.getStateName(), "", "");
		cities.put(dc2.getCityKey(), dc2);
		// Add City with City Name
		DeliveryCity dc3 = new DeliveryCity(deliveryCity.getCityName(), "", "", "");
		cities.put(dc3.getCityKey(), dc3);

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
		Std.println("B4 getDeliveryCity( CN="+city+" ST="+state+" CO="+country+" )");
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
