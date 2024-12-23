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

package io.fusion.water.order.domain.services;

import java.util.List;

import io.fusion.water.order.domain.models.DeliveryCity;
import io.fusion.water.order.domain.models.OrderEntity;

/**
 * 
 * @author arafkarsh
 *
 */
public interface ShippingService {
	
	
	/**
	 * Ship Order and Set Order Status to IN_TRANSIT
	 * 
	 * @return
	 */
	public List<OrderEntity> shipOrder(List<OrderEntity> orderList);
	
	/**
	 * Return a List of Delivery Cities
	 * 
	 * @param cities
	 * @return
	 */
	public List<DeliveryCity> getCities(List<String> cities, String state, String country);
	
	
	/**
	 * Return Delivery City
	 * 
	 * @param city
	 * @return
	 */
	public DeliveryCity getCity(String city, String state, String country);

}
