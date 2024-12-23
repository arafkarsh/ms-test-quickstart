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

package test.fusion.water.order.junit.mockito3.utils;

import java.util.Collection;
import java.util.HashMap;

import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import io.fusion.water.order.domain.models.DeliveryCity;
/**
 * 
 * @author arafkarsh
 *
 */
public class PaymentProcessingAnswer<T> implements Answer<Object> {
	
	private HashMap<String, DeliveryCity> cities;

	/**
	 * Custom Delivery City Answers
	 */
	public PaymentProcessingAnswer() {
		cities = new HashMap<String, DeliveryCity>();
		loadData();
	}
	
	/**
	 * Load Data
	 */
	public void loadData() {
		addCity(new DeliveryCity("Bengaluru", "", "India", "00000"));
		addCity(new DeliveryCity("Kochi", "", "India", "00000"));
		addCity(new DeliveryCity("Chennai", "", "India", "00000"));
	}

	@Override
	public DeliveryCity answer(InvocationOnMock invocation) throws Throwable {
		String city="", state="", country="";
		try {
			city = (String) invocation.getArguments()[0];
			state = (String) invocation.getArguments()[1];
			country = (String) invocation.getArguments()[2];
		} catch (Exception e) {
			e.printStackTrace();
		}
		return getDeliveryCity( city,  state,  country);
	}
	
	/**
	 * Add Delivery City
	 * 
	 * @param deliveryCity
	 * @return
	 */
	public void addCity(DeliveryCity deliveryCity) {
		cities.put(deliveryCity.getCityKey(), deliveryCity);
	}
	
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
