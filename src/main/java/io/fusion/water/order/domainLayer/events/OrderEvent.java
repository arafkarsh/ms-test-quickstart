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

package io.fusion.water.order.domainLayer.events;

import java.util.ArrayList;

import io.fusion.water.order.domainLayer.models.Customer;
import io.fusion.water.order.domainLayer.models.OrderItem;
import io.fusion.water.order.domainLayer.models.PaymentType;
import io.fusion.water.order.domainLayer.models.ShippingAddress;

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
		orderItems = new ArrayList<OrderItem>();
	}
	
	/**
	 * Adds the Customer
	 * @param _customer
	 */
	protected void addCustomer(Customer _customer) {
		customer = _customer;
		customer.validate();
	}
	
	/**
	 * Add Order Item
	 * @param _item
	 */
	protected void addOrderItem(OrderItem _item) {
		if(_item != null) {
			orderItems.add(_item);
		}
	}
	
	/**
	 * Add Shipping Address
	 * @param _address
	 */
	protected void addShippingAddress(ShippingAddress _address) {
		shippingAddress = _address;
	}
	
	/**
	 * Add Payment Type
	 * @param _pType
	 */
	protected void addPaymentType(PaymentType _pType) {
		paymentType = _pType;
	}

	/**
	 * Returns true 
	 * @return
	 */
	public boolean isCustomerAvailable()  {
		return (customer != null) ? true : false;
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
	public ArrayList<OrderItem> getOrderItems() {
		return orderItems;
	}

	/**
	 * Returns TRUE if the Shipping Address is available
	 * @return
	 */
	public boolean isShippingAddressAvailable()  {
		return (shippingAddress != null) ? true : false;
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
		 * @param _customer
		 * @return
		 */
		public Builder addCustomer(Customer _customer) {
			order.addCustomer(_customer);
			return this;
		}
		
		/**
		 * Add Order Item
		 * @param _item
		 * @return
		 */
		public Builder addOrderItem(OrderItem _item) {
			order.addOrderItem(_item);
			return this;
		}
		
		/**
		 * Add Shipping Address
		 * @param _address
		 * @return
		 */
		public Builder addShippingAddress(ShippingAddress _address) {
			order.addShippingAddress(_address);
			return this;
		}
		
		/**
		 * Add Payment Type
		 * @param _pType
		 * @return
		 */
		public Builder addPaymentType(PaymentType _pType) {
			order.addPaymentType(_pType);
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
	
	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		OrderEvent o = new OrderEvent.Builder()
					.addCustomer(null)
					.addOrderItem(null)
					.addShippingAddress(null)
					.addPaymentType(null)
					.build();
	}
}
