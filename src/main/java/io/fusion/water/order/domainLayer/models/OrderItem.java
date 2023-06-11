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

/**
 * Order Item
 * 
 * @author arafkarsh
 *
 */
public class OrderItem {

	private String itemId;
	private String itemName;
	private int itemValue;
	private String itemCurrency;
	private int quantity;
	
	public OrderItem() {
	}
	
	/**
	 * Create an Order Item
	 * 
	 * @param _itemId
	 * @param _iName
	 * @param _value
	 * @param _currency
	 * @param _qty
	 */
	public OrderItem(String _itemId, String _iName,
			int _value, String _currency, int _qty) {
		
		itemId			= _itemId;
		itemName		= _iName;
		itemValue		= _value;
		itemCurrency	= _currency;
		quantity		= _qty;
		
	}
	/**
	 * @return the itemId
	 */
	public String getItemId() {
		return itemId;
	}
	/**
	 * @return the itemName
	 */
	public String getItemName() {
		return itemName;
	}
	/**
	 * @return the itemValue
	 */
	public int getItemValue() {
		return itemValue;
	}
	/**
	 * @return the itemCurrency
	 */
	public String getItemCurrency() {
		return itemCurrency;
	}
	/**
	 * @return the quantity
	 */
	public int getQuantity() {
		return quantity;
	}
	
	/**
	 * Returns Hashcode of the Item ID
	 */
	public int hashCode()  {
		return itemId.hashCode();
	}
	
	/**
	 * Returns Item Name
	 */
	public String toString() {
		return itemName;
	}
}
