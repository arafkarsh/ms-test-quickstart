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

import io.fusion.water.order.domain.exceptions.InputDataException;

import java.util.ArrayList;
import java.util.List;

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
        phoneList	= new ArrayList<>();
        addPhoneNumber(phoneNumber);
        // Added to demo Null Check in Test Cases (JUnit 5 / OrderNestedTest)
        if(firstName == null ) {
            throw new InputDataException("First Name can't be Null!");
        }
        if(lastName == null) {
            throw new InputDataException("Last Name can't be Null!");
        }
        if(phoneNumber == null) {
            throw new InputDataException("Phone Number can't be Null!");
        }
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
    	if(phoneList.isEmpty()) {
    		phoneList.add(phoneNumber);
    	}
    }
    
    /**
     * Returns the Phone List
     * @return
     */
    public List<String> getPhoneList() {
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
    public boolean equals(Object obj) {
        	if(obj == null) {
        		return false;
        	}
        	if(obj instanceof Customer customer) {
        		return customerId.equals(customer.getCustomerId());
        	}
        	return false;
    }
}
