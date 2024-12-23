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
 * Shipping Address
 * 
 * @author arafkarsh
 *
 */
public class ShippingAddress {

	private String streetName;
	private String addressLine2;
	private String city;
	private String state;
	private String landMark;
	private String country;
	private String zipCode;
	
	/**
	 * 
	 */
	public ShippingAddress() {
	}
	
	/**
	 * Create Shipping address
	 * @param sn
	 * @param aline2
	 * @param city
	 * @param state
	 * @param lm
	 * @param country
	 * @param zCode
	 */
	public ShippingAddress(String sn, String aline2, String city,
			String state, String lm, String country, String zCode) {
		
		streetName 		= sn;
		addressLine2	= aline2;
		this.city = city;
		this.state = state;
		landMark		= lm;
		this.country = country;
		zipCode			= zCode;
	}
	
	/**
	 * @return the streetName
	 */
	public String getStreetName() {
		return streetName;
	}
	/**
	 * @return the addressLine2
	 */
	public String getAddressLine2() {
		return addressLine2;
	}
	/**
	 * @return the city
	 */
	public String getCity() {
		return city;
	}
	/**
	 * @return the state
	 */
	public String getState() {
		return state;
	}
	/**
	 * @return the landMark
	 */
	public String getLandMark() {
		return landMark;
	}
	/**
	 * @return the country
	 */
	public String getCountry() {
		return country;
	}
	/**
	 * @return the zipCode
	 */
	public String getZipCode() {
		return zipCode;
	}
}
