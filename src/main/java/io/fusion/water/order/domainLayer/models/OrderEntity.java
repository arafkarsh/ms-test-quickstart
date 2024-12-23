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

package io.fusion.water.order.domainLayer.models;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;


/**
 * Order Entity
 *
 * @author arafkarsh
 *
 */
public class OrderEntity {

	private String orderId;

	@JsonSerialize(using = DateJsonSerializer.class)
	private LocalDateTime orderDate;

	@NotNull
	private Customer customer;

	@NotNull
	private ArrayList<OrderItem> orderItems;

	@NotNull
	private ShippingAddress shippingAddress;

	private PaymentType paymentType;
	private OrderStatus orderStatus;

	@NotNull
	private PaymentDetails paymentDetails;
	private PaymentStatus paymentStatus;

	/**
	 * Create Order
	 */
	public OrderEntity() {
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
	 * Add Card Details
	 * @param _cardDetails
	 */
	protected void addCardDetails(CardDetails _cardDetails) {
		paymentDetails = new PaymentDetails(
				getOrderId(),
				getOrderDate(),
				getTotalValue(),
				getPaymentType(),
				_cardDetails
		);
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
	 * Returns the Total Items in the Order
	 * @return
	 */
	public int getTotalItems() {
		return orderItems.size();
	}

	/**
	 * Calculate the Total Order Value
	 * @return
	 */
	public double getTotalValue() {
		double totalValue = 0.00;
		for(OrderItem item : orderItems) {
			totalValue += item.getItemValue();
		}
		return totalValue;
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

		OrderEntity order = new OrderEntity();

		/**
		 * Add Customer
		 * @param _customer
		 * @return
		 */
		public Builder addCustomer(Customer _customer) {
			order.orderId = UUID.randomUUID().toString();
			order.orderDate = LocalDateTime.now();
			order.orderStatus = OrderStatus.INITIATED;
			order.addCustomer(_customer);
			return this;
		}

		/**
		 * THIS IS ONLY FROM TESTING PERSPECTIVE
		 * Set the Order ID
		 *
		 * @param _orderId
		 * @return
		 */
		public Builder setOrderId(String _orderId) {
			order.orderId = _orderId;
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
		 * Add a List of Order Items
		 * @param _items
		 * @return
		 */
		public Builder addOrderItems(ArrayList<OrderItem> _items) {
			if(_items != null && _items.size() > 0) {
				order.getOrderItems().addAll(_items);
			}
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
		 * Add Card Details
		 * @param _cardDetails
		 * @return
		 */
		public Builder addCardDetails(CardDetails _cardDetails) {
			order.addCardDetails(_cardDetails);
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
		 * Set Order Status to Waiting for Payment
		 * @return
		 */
		public Builder waitingForPayment() {
			order.orderStatus = OrderStatus.PAYMENT_EXPECTED;
			return this;
		}
		// ------------------------------------------------------------------------------------
		// Following Methods ADDED ONLY FOR TESTING purpose
		// ------------------------------------------------------------------------------------
		/**
		 * Order Status = PAYMENT EXPECTED
		 */
		public Builder orderWaitingForPayment() {
			order.orderStatus = OrderStatus.PAYMENT_EXPECTED;
			return this;
		}
		/**
		 * Order Status = PREPARING
		 */
		public Builder orderIsGettingPrepared() {
			order.orderStatus = OrderStatus.PREPARING;
			return this;
		}

		/**
		 * Order Status = READY_FOR_SHIPMENT
		 */
		public Builder orderReadyForShipment() {
			order.orderStatus = OrderStatus.READY_FOR_SHIPMENT;
			return this;
		}

		/**
		 * Order Status = IN_TRANSIT
		 */
		public Builder orderInTransit() {
			order.orderStatus = OrderStatus.IN_TRANSIT;
			return this;
		}

		/**
		 * Order Status = DELIVERED
		 */
		public Builder orderDelivered() {
			order.orderStatus = OrderStatus.DELIVERED;
			return this;
		}

		/**
		 * Order Status = RETURNED
		 */
		public Builder orderReturned() {
			order.orderStatus = OrderStatus.RETURNED;
			return this;
		}

		// END OF METHODS FOR TESTING --------------------------------------------------------------

		/**
		 * Build the Order
		 * @return
		 */
		public OrderEntity build() {
			return order;
		}
	}

	/**
	 * @return the orderId
	 */
	public String getOrderId() {
		return orderId;
	}

	/**
	 * @return the orderDate
	 */
	public LocalDateTime getOrderDate() {
		return orderDate;
	}

	/**
	 * @return the orderStatus
	 */
	public OrderStatus getOrderStatus() {
		return orderStatus;
	}

	/**
	 * Order Status = PAYMENT EXPECTED
	 */
	public void orderWaitingForPayment() {
		orderStatus = OrderStatus.PAYMENT_EXPECTED;
	}
	/**
	 * Order Status = PREPARING
	 */
	public void orderIsGettingPrepared() {
		orderStatus = OrderStatus.PREPARING;
	}

	/**
	 * Order Status = READY_FOR_SHIPMENT
	 */
	public void orderReadyForShipment() {
		orderStatus = OrderStatus.READY_FOR_SHIPMENT;
	}

	/**
	 * Order Status = IN_TRANSIT
	 */
	public void orderInTransit() {
		orderStatus = OrderStatus.IN_TRANSIT;
	}

	/**
	 * Order Status = DELIVERED
	 */
	public void orderDelivered() {
		orderStatus = OrderStatus.DELIVERED;
	}

	/**
	 * Order Status = RETURNED
	 */
	public void orderReturned() {
		orderStatus = OrderStatus.RETURNED;
	}

	/**
	 * Order Status = CANCELLED
	 */
	public void orderCancelled() {
		orderStatus = OrderStatus.CANCELLED;
	}

	/**
	 * @param _paymentStatus the paymentStatus to set
	 */
	public void setPaymentStatus(PaymentStatus _paymentStatus) {
		if(_paymentStatus == null) {
			orderStatus = OrderStatus.PAYMENT_EXPECTED;
			return;
		}
		paymentStatus = _paymentStatus;
		if(paymentStatus.getPaymentStatus().equalsIgnoreCase("Accepted")) {
			orderStatus = OrderStatus.PAID;
		} else {
			orderStatus = OrderStatus.PAYMENT_DECLINED;
		}
	}

	/**
	 * @return the paymentDetails
	 */
	public PaymentDetails getPaymentDetails() {
		return paymentDetails;
	}

	/**
	 * @return the paymentStatus
	 */
	public PaymentStatus getPaymentStatus() {
		return paymentStatus;
	}

	/**
	 * Shows Order ID + Order Status
	 */
	public String toString() {
		return orderId +"|" + orderStatus;
	}

}
