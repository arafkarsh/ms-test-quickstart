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

package io.fusion.water.order.adapters.repository;

import io.fusion.water.order.adapters.utils.OrderSampleData;
import org.springframework.stereotype.Service;

import io.fusion.water.order.domain.models.OrderEntity;
import io.fusion.water.order.domain.services.OrderRepository;

/**
 * Order Repository Service
 *
 * @author arafkarsh
 *
 */
@Service
public class OrderRepositoryImpl implements OrderRepository {

	@Override
	public OrderEntity getOrderById(String id) {
		// Mock API
		return OrderSampleData.getOrderById(id);
	}

	@Override
	public OrderEntity saveOrder(OrderEntity order) {
		// Mock API
		return order;
	}

	@Override
	public OrderEntity cancelOrder(OrderEntity order) {
		// Mock API
		if(order != null) {
			order.orderCancelled();
		}
		return order;
	}

	@Override
	public OrderEntity cancelOrder(String id) {
		// Mock API
		OrderEntity order = OrderSampleData.getOrderById(id);
		order.orderCancelled();
		return order;
	}

	@Override
	public OrderEntity prepareOrder(OrderEntity order) {
		// Mock API
		if(order != null) {
			order.orderIsGettingPrepared();
		}
		return order;
	}

}
