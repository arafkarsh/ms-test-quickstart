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

import java.util.ArrayList;

/**
 * Customer Reference in Order Entity
 * 
 * @author arafkarsh
 *
 */
public class Customer {
	
	private  String customerId;
	private  String firstName;
	private  String lastName;
	private  String phoneNumber;
	private  ArrayList<String> phoneList;

	public Customer() {
	}
	
	/**
	 * Create Customer Contact Info
	 * 
	 * @param customerId
	 * @param firstName
	 * @param lastName
	 * @param phoneNumber
	 */
    public Customer(String customerId, String firstName,
    		String lastName, String phoneNumber) {
        this.customerId = customerId;
    	this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        phoneList	= new ArrayList<String>();
        addPhoneNumber(phoneNumber);
        // validate();
    }
    
    /**
     * Add Multiple Phones
     * @param phoneNumber
     */
    private void addPhoneNumber(String phoneNumber) {
    	if(phoneNumber == null) {
    		return;
    	}
    	String[] phones = phoneNumber.split(",");
    	for(int x=0; x<phones.length; x++) {
    		phoneList.add(phones[x]);
    	}
    	if(phoneList.size() == 0) {
    		phoneList.add(phoneNumber);
    	}
    }
    
    /**
     * Returns the Phone List
     * @return
     */
    public ArrayList<String> getPhoneList() {
    	return phoneList;
    }

    /**
     * Returns the Customer Id
     * @return
     */
    public String getCustomerId() {
    	return customerId;
    }
    
    /**
     * Returns the First Name
     * @return
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Returns the Last Name
     * @return
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Returns the Phone Number
     * @return
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }
    
    /**
     * Returns Customers Name
     * @return
     */
    public String name() {
    	return toString();
    }
    
    /**
     * Returns First Name and Last Name
     */
    public String toString() {
    	return String.format("%s %s", firstName, lastName);
    }
    
    /**
     * Returns the hash Code of the Customer ID
     */
    public int hashCode() {
    	return customerId.hashCode();
    }
    
    /**
     * Compares 2 Customer Objects and 
     * returns TRUE if the Customer ID is Same
     */
    public boolean equals(Object o) {
    	try {
    		Customer c = (Customer)o;
    		return c.customerId.equalsIgnoreCase(customerId);
    	} catch (Exception ignored) {}
    	return false;
    }

    /**
     * 
     * @return
     */
    public String toJSON() {
    	StringBuilder sb = new StringBuilder();
    	return sb.toString();
    }
    /**
     * Validate Customer
     */
    public void validate() {
        validateFirstName();
        validateLastName();
        validatePhoneNumber();
    }
    
    /**
     * Validate First Name
     */
    public void validateFirstName() {
        if (isBlank(firstName)) {
        	System.out.println("First Name Cannot be null or empty");
        }
    }

    /**
     * Validate Last Name
     */
    public void validateLastName() {
        if (isBlank(lastName)) {
        	System.out.println("Last Name Cannot be null or empty");
        }
    }

    /**
     * Validate All Phone Numbers
     */
    public void validatePhoneNumber() {
    	for(String phone : phoneList)  {
    		validatePhoneNumber(phone);
    	}
    	if(phoneList.size() == 0) {
    		validatePhoneNumber(null);
    	}
    }
    /**
     * Validate Phone No.
     */
    public void validatePhoneNumber(String phoneNumber) {
        if (isBlank(phoneNumber)) {
        	System.out.println("Phone No. Cannot be null or empty");
            throw new RuntimeException("Phone Name Cannot be null or empty");
        }

        if (this.phoneNumber.length() != 10) {
            throw new RuntimeException("Phone Number Should be 10 Digits Long");
        }
        if (!this.phoneNumber.matches("\\d+")) {
            throw new RuntimeException("Phone Number Contain only digits");
        }
        if (!this.phoneNumber.startsWith("0")) {
            throw new RuntimeException("Phone Number Should Start with 0");
        }
    }
    
    /**
     * Check if the Field Value is NULL or BLANK
     * @param _value
     * @return
     */
    private boolean isBlank(String _value) {
    	return (_value == null || _value.trim().length() == 0) ? true : false;
    }
}
