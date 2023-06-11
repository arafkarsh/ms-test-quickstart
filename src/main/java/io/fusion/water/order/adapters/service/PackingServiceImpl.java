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

import org.springframework.stereotype.Service;

import io.fusion.water.order.domainLayer.models.OrderEntity;
import io.fusion.water.order.domainLayer.services.PackingService;

/**
 * Packing Service
 * 
 * @author arafkarsh
 *
 */
@Service
public class PackingServiceImpl implements PackingService {

	@Override
	public ArrayList<OrderEntity> packOrders(ArrayList<OrderEntity> _orderList) {
		for(OrderEntity order : _orderList) {
			order.orderReadyForShipment();
		}
		return _orderList;
	}

}
