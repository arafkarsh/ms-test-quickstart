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
import java.util.LinkedHashMap;

import io.fusion.water.order.utils.Std;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import io.fusion.water.order.domain.models.DeliveryCity;
/**
 * 
 * @author arafkarsh
 *
 */
public class DeliveryCityAnswer<T> implements Answer<Object> {
	
	private LinkedHashMap<String, DeliveryCity> cities;

	/**
	 * Custom Delivery City Answers
	 */
	public DeliveryCityAnswer() {
		cities = new LinkedHashMap<>();
		loadData();
		Std.println("DeliveryCityAnswer: Total Cities = "+cities.size());
	}

	/**
	 * Get the answer for the Delivery City
	 */
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
		Std.println("DeliveryCityAnswer: City = "+city+" State = "+state+" Country = "+country);
		return getDeliveryCity( city,  state,  country);
	}

	/**
	 * Returns Delivery City
	 *
	 * @param city
	 * @param state
	 * @param country
	 * @return
	 */
	private DeliveryCity getDeliveryCity(String city, String state, String country) {
		String key = DeliveryCity.createCityKey(city, state, country);
		Std.println("DeliveryCityAnswer: Search Key = "+key);
		return cities.get(key);
	}

	/**
	 * Load Data
	 */
	private void loadData() {
		// India
		addCity(new DeliveryCity("Bengaluru", "", "India", "00000"));
		addCity(new DeliveryCity("Kochi", "", "India", "00000"));
		addCity(new DeliveryCity("Chennai", "", "India", "00000"));
		// USA
		addCity(new DeliveryCity("New York", "NY", "USA", "00000"));
		addCity(new DeliveryCity("Los Angeles", "CA", "USA", "00000"));
		addCity(new DeliveryCity("San Francisco", "CA", "USA", "00000"));
		// UK
		addCity(new DeliveryCity("London", "", "UK", "00000"));
		addCity(new DeliveryCity("Manchester", "", "UK", "00000"));
		addCity(new DeliveryCity("Birmingham", "", "UK", "00000"));
	}

	public void printCities() {
		cities.forEach((k, v) -> {
			Std.println("City Key = "+k+" City = "+v.getCityName()+" State = "+v.getStateName()+" Country = "+v.getCountryName());
		});
	}

	/**
	 * Add Delivery City
	 *
	 * @param deliveryCity
	 * @return
	 */
	private void addCity(DeliveryCity deliveryCity) {
		// Add Cities with City Name, State Name and Country Name
		cities.put(deliveryCity.getCityKey(), deliveryCity);
		// Add City with City Name and State Name
		DeliveryCity dc2 = new DeliveryCity(deliveryCity.getCityName(), deliveryCity.getStateName(), "", "");
		cities.put(dc2.getCityKey(), dc2);
		// Add City with City Name
		DeliveryCity dc3 = new DeliveryCity(deliveryCity.getCityName(), "", "", "");
		cities.put(dc3.getCityKey(), dc3);
	}

	/**
	 * Returns All Delivery Cities
	 * @return
	 */
	public Collection<DeliveryCity> getDeliveryCities() {
		return cities.values();
	}

}
