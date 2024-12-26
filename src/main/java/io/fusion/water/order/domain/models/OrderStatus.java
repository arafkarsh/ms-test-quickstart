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

package io.fusion.water.order.domain.models;

/**
 * Order Status
 * 
 * @author arafkarsh
 *
 */
public enum OrderStatus {

	/**
	 * Order Initiated
	 */
	INITIATED,
	
	/**
	 * Placed, but not payed yet. Still changeable.
	 */
	PAYMENT_EXPECTED,
	
	/**
	 * Order is Cancelled
	 */
	CANCELLED,
	
	/**
	 * Payment Done. No changes allowed.
	 */
	PAID,
	
	/**
	 * Payment didn't go thru
	 */
	PAYMENT_DECLINED,
	
	/**
	 * Payment Done, Order is getting prepared for Shipping
	 */
	PREPARING,

	ORDER_PACKED,

	/**
	 * Order is ready for Shipment.
	 */
	READY_FOR_SHIPMENT,

	/**
	 * Order is Shipped
	 */
	ORDER_SHIPPED,

	/**
	 * Order in Transit > To Customer
	 */
	IN_TRANSIT,
	
	/**
	 * Order Delivered
	 */
	DELIVERED,
	
	/**
	 * Order Returned
	 */
	RETURNED;

}
