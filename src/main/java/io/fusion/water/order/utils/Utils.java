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

package io.fusion.water.order.utils;

import java.util.stream.Stream;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

/**
 * 
 * @author arafkarsh
 *
 */
public final class Utils {


	private Utils() {
	}

	/**
	 * Return JSON
	 * 
	 * @param object
	 * @return
	 */
	public static String toJson(Object object) {
		if(object == null) {
			return "";
		}
		return new Gson().toJson(object);
	}
	
	/***
	 * 
	 * @param object
	 * @return
	 */
	public static String toJsonString(Object object) {
		if(object == null) {
			return "";
		}
		try {
			return new ObjectMapper().writeValueAsString(object);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return "";
	}
	
	/**
	 * String Utilities
	 * 
	 * @author arafkarsh
	 *
	 */
	public static class Strings {

		private Strings() {}

		/**
		 * Returns True if the String is NULL or Empty
		 * 
		 * @param input
		 * @return
		 */
	    public static boolean isBlank(String input) {
	        return input == null || input.trim().isEmpty();
	    }
	    
	    public static Stream<String> blankStrings() {
	        return Stream.of(null, "", "  ");
	    }
	}
	
	/**
	 * Number Utility
	 * 
	 * @author arafkarsh
	 *
	 */
	public static class Numbers {

		private Numbers() {}

		/**
		 * Returns True if the Number is an Odd Number
		 * @param number
		 * @return
		 */
	    public static boolean isOdd(int number) {
	        return number % 2 != 0;
	    }
	}
}
