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

import io.fusion.water.order.domain.models.OrderEntity;
import io.fusion.water.order.domain.models.PaymentDetails;
import io.fusion.water.order.domain.models.PaymentStatus;

/**
 * Order Business Service
 * 
 * @author arafkarsh
 *
 */
public interface OrderService {

	/**
	 * Returns Order By Order ID
	 * 
	 * @param id
	 * @return
	 */
	public OrderEntity getOrderById(String id);


	/**
	 * ONLY FOR PACT DEMO
	 * @param order
	 * @return
	 */
	public OrderEntity saveOrderExternal(OrderEntity order);

	/**
	 * Save Order
	 * 
	 * @param order
	 * @return
	 */
	public OrderEntity processOrder(OrderEntity order);
	
	/**
	 * Cancel Order
	 * 
	 * @param order
	 * @return
	 */
	public OrderEntity cancelOrder(OrderEntity order);
	
	/**
	 * Cancel Order by Id
	 * 
	 * @param id
	 * @return
	 */
	public OrderEntity cancelOrder(String id);
	
	/**
	 * Prepare Order for Shipping
	 * 
	 * @param order
	 * @return
	 */
	public OrderEntity prepareOrder(OrderEntity order);
	
	/**
	 * Update Order Status based on Order ID
	 * 
	 * @param id
	 * @param status
	 * @return
	 */
	public OrderEntity updateOrderStatus(String id, String status);
	
	/**
	 * Process Payments
	 * 
	 * @param paymentDetails
	 * @return
	 */
	public PaymentStatus processPayments(PaymentDetails paymentDetails);

}
