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
 * 
 * @author arafkarsh
 *
 */
public class DeliveryCity {

	private final String cityName;
	private final String stateName;
	private final String countryName;
	private final String zipCode;
	
	private final String cityKey;
	
	/**
	 * Create Delivery City
	 * 
	 * @param _city
	 * @param _state
	 * @param _country
	 * @param _zip
	 */
	public DeliveryCity(String _city, String _state, String _country, String _zip) {
		this.cityName		= (_city 		== null) ? "" : _city;
		this.stateName 		= (_state 		== null) ? "" : _state;
		this.countryName 	= (_country 	== null) ? "" : _country;
		this.zipCode		= (_zip 		== null) ? "" : _zip;
		
		this.cityKey		= createCityKey( cityName,  stateName,  countryName);
	}

	/**
	 * @return the cityName
	 */
	public String getCityName() {
		return cityName;
	}

	/**
	 * @return the stateName
	 */
	public String getStateName() {
		return stateName;
	}

	/**
	 * @return the countryName
	 */
	public String getCountryName() {
		return countryName;
	}

	/**
	 * @return the zipCode
	 */
	public String getZipCode() {
		return zipCode;
	}
	
	/**
	 * Returns the City Key
	 */
	public String toString() {
		return cityKey;
	}
	
	/**
	 * Returns the HashCode of the City Key
	 */
	public int hashCode() {
		return cityKey.hashCode();
	}

	/**
	 * @return the cityKey
	 */
	public String getCityKey() {
		return cityKey;
	}
	
	/**
	 * Create City Key
	 * 
	 * @param city
	 * @param state
	 * @param country
	 * @return
	 */
	public static String createCityKey(String city, String state, String country) {
		city 	= (city 	== null) ? "" : city;
		state 	= (state 	== null) ? "" : state;
		country = (country 	== null) ? "" : country;
		return new StringBuilder()
				.append(city).append("|")
				.append(state).append("|")
				.append(country).append("|")
				.toString();
	}
}
