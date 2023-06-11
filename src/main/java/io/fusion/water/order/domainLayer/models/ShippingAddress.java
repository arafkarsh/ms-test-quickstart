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
	 * @param _sn
	 * @param _aline2
	 * @param _city
	 * @param _state
	 * @param _lm
	 * @param _country
	 * @param _zCode
	 */
	public ShippingAddress(String _sn, String _aline2, String _city,
			String _state, String _lm, String _country, String _zCode) {
		
		streetName 		= _sn;
		addressLine2	= _aline2;
		city			= _city;
		state			= _state;
		landMark		= _lm;
		country			= _country;
		zipCode			= _zCode;
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
