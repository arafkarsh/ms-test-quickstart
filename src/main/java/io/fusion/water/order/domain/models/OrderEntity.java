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

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
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
		orderItems = new ArrayList<>();
		orderId = null;
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
	 * @param orderItem
	 */
	protected void addOrderItem(OrderItem orderItem) {
		if(orderItem != null) {
			orderItems.add(orderItem);
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
	 * Add Card Details
	 * @param carddetails
	 */
	protected void addCardDetails(CardDetails carddetails) {
		paymentDetails = new PaymentDetails(
				getOrderId(),
				getOrderDate(),
				getTotalValue(),
				getPaymentType(),
				carddetails
		);
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
		return (shippingAddress != null) ;
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
	 * =========================================================================================
	 * Build Order
	 * =========================================================================================
	 */
	public static class Builder {

		OrderEntity order = new OrderEntity();

		/**
		 * Add Customer
		 * @param customer
		 * @return
		 */
		public Builder addCustomer(Customer customer) {
			order.orderDate = LocalDateTime.now();
			order.orderStatus = OrderStatus.INITIATED;
			order.addCustomer(customer);
			return this;
		}

		/**
		 * THIS IS ONLY FROM TESTING PERSPECTIVE
		 * Set the Order ID
		 *
		 * @param orderId
		 * @return
		 */
		public Builder setOrderId(String orderId) {
			order.orderId = orderId;
			return this;
		}

		/**
		 * Add Order Item
		 * @param orderItem
		 * @return
		 */
		public Builder addOrderItem(OrderItem orderItem) {
			order.addOrderItem(orderItem);
			return this;
		}

		/**
		 * Add a List of Order Items
		 * @param orderItems
		 * @return
		 */
		public Builder addOrderItems(List<OrderItem> orderItems) {
			if(orderItems != null && orderItems.isEmpty()) {
				order.getOrderItems().addAll(orderItems);
			}
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
		 * Add Card Details
		 * @param cardDetails
		 * @return
		 */
		public Builder addCardDetails(CardDetails cardDetails) {
			order.addCardDetails(cardDetails);
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
		public Builder orderPaymentExpected() {
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
		 * Order Status = ORDER_PACKED
		 * @return
		 */
		public Builder orderPacked() {
			order.orderStatus = OrderStatus.ORDER_PACKED;
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
			if(order.orderId == null) {
				order.orderId = UUID.randomUUID().toString();
			}
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
	 * Order Status = ORDER_PACKED
	 * @return
	 */
	public void orderPacked() {
		orderStatus = OrderStatus.ORDER_PACKED;
	}

	/**
	 * Order Status = READY_FOR_SHIPMENT
	 */
	public void orderReadyForShipment() {
		orderStatus = OrderStatus.READY_FOR_SHIPMENT;
	}

	/**
	 * Order Status = ORDER_SHIPPED
	 */
	public void orderShipped() {
		orderStatus = OrderStatus.ORDER_SHIPPED;
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
	 * Order Status = INVALID
	 */
	public void orderIsInvalid() {
		orderStatus = OrderStatus.INVALID;
	}

	/**
	 * @param paymentStatus the paymentStatus to set
	 */
	public void setPaymentStatus(PaymentStatus paymentStatus) {
		if(paymentStatus == null) {
			orderStatus = OrderStatus.PAYMENT_EXPECTED;
			return;
		}
		this.paymentStatus = paymentStatus;
		if(this.paymentStatus.getPayStatus().equalsIgnoreCase("Accepted")) {
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
