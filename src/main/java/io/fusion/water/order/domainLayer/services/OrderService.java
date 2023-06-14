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

package io.fusion.water.order.domainLayer.services;

import io.fusion.water.order.domainLayer.models.OrderEntity;
import io.fusion.water.order.domainLayer.models.PaymentDetails;
import io.fusion.water.order.domainLayer.models.PaymentStatus;

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
	 * @param _id
	 * @return
	 */
	public OrderEntity getOrderById(String _id);


	/**
	 * ONLY FOR PACT DEMO
	 * @param _order
	 * @return
	 */
	public OrderEntity saveOrderExternal(OrderEntity _order);

	/**
	 * Save Order
	 * 
	 * @param _order
	 * @return
	 */
	public OrderEntity processOrder(OrderEntity _order);
	
	/**
	 * Cancel Order
	 * 
	 * @param _order
	 * @return
	 */
	public OrderEntity cancelOrder(OrderEntity _order);
	
	/**
	 * Cancel Order by Id
	 * 
	 * @param _id
	 * @return
	 */
	public OrderEntity cancelOrder(String _id);
	
	/**
	 * Prepare Order for Shipping
	 * 
	 * @param _order
	 * @return
	 */
	public OrderEntity prepareOrder(OrderEntity _order);
	
	/**
	 * Update Order Status based on Order ID
	 * 
	 * @param _id
	 * @param _status
	 * @return
	 */
	public OrderEntity updateOrderStatus(String _id, String _status);
	
	/**
	 * Process Payments
	 * 
	 * @param _paymentDetails
	 * @return
	 */
	public PaymentStatus processPayments(PaymentDetails _paymentDetails);

}
