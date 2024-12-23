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

package io.fusion.water.order.domain.events;

import java.util.ArrayList;
import java.util.List;

import io.fusion.water.order.domain.models.Customer;
import io.fusion.water.order.domain.models.OrderItem;
import io.fusion.water.order.domain.models.PaymentType;
import io.fusion.water.order.domain.models.ShippingAddress;

/**
 * Order Event
 * 
 * @author arafkarsh
 *
 */
public class OrderEvent {

	private Customer customer;
	private ArrayList<OrderItem> orderItems;
	private ShippingAddress shippingAddress;
	private PaymentType paymentType;
	
	/**
	 * Create Order
	 */
	public OrderEvent() {
		orderItems = new ArrayList<>();
	}
	
	/**
	 * Adds the Customer
	 * @param customer
	 */
	protected void addCustomer(Customer customer) {
		this.customer = customer;
	}
	
	/**
	 * Add Order Item
	 * @param item
	 */
	protected void addOrderItem(OrderItem item) {
		if(item != null) {
			orderItems.add(item);
		}
	}
	
	/**
	 * Add Shipping Address
	 * @param address
	 */
	protected void addShippingAddress(ShippingAddress address) {
		shippingAddress = address;
	}
	
	/**
	 * Add Payment Type
	 * @param pType
	 */
	protected void addPaymentType(PaymentType pType) {
		paymentType = pType;
	}

	/**
	 * Returns true 
	 * @return
	 */
	public boolean isCustomerAvailable()  {
		return (customer != null);
	}
	
	/**
	 * @return the customer
	 */
	public Customer getCustomer() {
		return customer;
	}

	/**
	 * @return the orderItems
	 */
	public List<OrderItem> getOrderItems() {
		return orderItems;
	}

	/**
	 * Returns TRUE if the Shipping Address is available
	 * @return
	 */
	public boolean isShippingAddressAvailable()  {
		return (shippingAddress != null);
	}
	
	/**
	 * @return the shippingAddress
	 */
	public ShippingAddress getShippingAddress() {
		return shippingAddress;
	}

	/**
	 * @return the paymentType
	 */
	public PaymentType getPaymentType() {
		return paymentType;
	}
	
	/**
	 * Build Order
	 */
	public static class Builder {
		
		OrderEvent order = new OrderEvent();
		
		/**
		 * Add Custmer
		 * @param customer
		 * @return
		 */
		public Builder addCustomer(Customer customer) {
			order.addCustomer(customer);
			return this;
		}
		
		/**
		 * Add Order Item
		 * @param item
		 * @return
		 */
		public Builder addOrderItem(OrderItem item) {
			order.addOrderItem(item);
			return this;
		}
		
		/**
		 * Add Shipping Address
		 * @param address
		 * @return
		 */
		public Builder addShippingAddress(ShippingAddress address) {
			order.addShippingAddress(address);
			return this;
		}
		
		/**
		 * Add Payment Type
		 * @param pType
		 * @return
		 */
		public Builder addPaymentType(PaymentType pType) {
			order.addPaymentType(pType);
			return this;
		}
		
		/**
		 * Build the Order
		 * @return
		 */
		public OrderEvent build() {
			return order;
		}
	}
}
