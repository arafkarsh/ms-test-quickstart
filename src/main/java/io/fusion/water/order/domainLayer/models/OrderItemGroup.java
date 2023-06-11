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

import java.util.ArrayList;
import java.util.Date;

/**
 * 
 * @author arafkarsh
 *
 */
public class OrderItemGroup {
	
	private String itemGroupName;
	private ArrayList<OrderItem> orderItems;
	private Date deliveryDate;
	
	private double totalValue;
	
	/**
	 * Create Order Item Group
	 * 
	 * @param _grp
	 * @param _items
	 * @param _dt
	 */
	public OrderItemGroup(String _grp, ArrayList<OrderItem> _items, Date _dt) {
		itemGroupName 	= (_grp != null) 	? _grp 	: "Default-Group";
		orderItems		= (_items != null) 	? _items: new ArrayList<OrderItem>();
		deliveryDate	= (_dt != null) 	? _dt 	: new Date();
		
		orderItems.forEach(item -> totalValue += item.getItemValue());
	}

	/**
	 * @return the itemGroupName
	 */
	public String getItemGroupName() {
		return itemGroupName;
	}

	/**
	 * @return the orderItems
	 */
	public ArrayList<OrderItem> getOrderItems() {
		return orderItems;
	}

	/**
	 * @return the deliveryDate
	 */
	public Date getDeliveryDate() {
		return deliveryDate;
	}

	/**
	 * @return the totalValue
	 */
	public double getTotalValue() {
		return totalValue;
	}
	
	/**
	 * Returns Item Group Name
	 */
	public String toString() {
		return itemGroupName;
	}
	
	/**
	 * Returns Hashcode of Item Group Name
	 */
	public int hashCode() {
		return itemGroupName.hashCode();
	}

}
