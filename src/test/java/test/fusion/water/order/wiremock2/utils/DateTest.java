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
package test.fusion.water.order.wiremock2.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * 
 * @author arafkarsh
 *
 */
public class DateTest {

	public static void main(String[] args) {

		Date dateToday = new Date();
		LocalDate today = LocalDate.now();
		LocalDateTime todayTime = LocalDateTime.now();
		
		System.out.println(dateToday);
		System.out.println(today);
		System.out.println(todayTime);

	}

}
