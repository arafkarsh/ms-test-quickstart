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
package test.fusion.water.order.utils;

import java.time.LocalDate;
import java.util.Random;

/**
 * 
 * @author arafkarsh
 *
 */
public class RandomCardNumber {

	private final int[] cArray = {	1100, 1200,1300,1400,1500,1600,1700,1800,1900,
			2100, 2200,2300,2400,2500,2600,2700,2800,2900,
			3100, 3200,3300,3400,3500,3600,3700,3800,3900,
			4100, 4200,4300,4400,4500,4600,4700,4800,4900,
			5100, 5200,5300,5400,5500,5600,5700,5800,5900,
			6100, 6200,6300,6400,6500,6600,6700,6800,6900,
			7100, 7200,7300,7400,7500,7600,7700,7800,7900,
			8100, 8200,8300,8400,8500,8600,8700,8800,8900,
			9100, 9200,9300,9400,9500,9600,9700,9800 };
	
	private final String[] cardHolders = {
								"John Doe", "Jane Doe", 
								"Ann Doe", "Sam Doe", "Mary Doe",
								"Jewel Doe", "June Doe", "Jeni Doe",
								"Tom Doe", "Fred Doe"
							};
	
	private final String cardNumber;
	private final String cardHolder;
	private final int month;
	private final int year;
	private final int cardCode;
	private final String firstName;
	private final String lastName;

	/**
	 * Generates Random Credit/Debit Card
	 */
	public RandomCardNumber() {
		cardNumber	= calculateCardNumber();
		month 		= getRandomNumber(1,12);
		year  		= calculateYear();
		cardCode 	= getRandomNumber(100,999);
		cardHolder	= createCardHolder();
		String[] names = cardHolder.split(" ");
		firstName = names[0];
		lastName = names[1];
	}
	
	/**
	 * Create Card Holder Name
	 * @return
	 */
	private String createCardHolder() {
		try {
			return cardHolders[getRandomNumber(0,9)];
		} catch(Exception ignored) {
			// nothing to print
		}
		return cardHolders[2];

	}
	
	/**
	 * Calculate Expiry Year
	 * @return
	 */
	private int calculateYear() {
		int startYear 	= LocalDate.now().getYear()+1;
		return getRandomNumber(startYear,startYear+10);
	}
	
	/**
	 * Generate Random Credit Card Number
	 * @return
	 */
	private String calculateCardNumber() {
		int block1 = getBlockNumber();
		int block2 = getBlockNumber();
		int block3 = getBlockNumber();
		int block4 = getBlockNumber();
		StringBuilder sb = new StringBuilder();
		sb.append(block1).append("-");
		sb.append(block2).append("-");
		sb.append(block3).append("-");
		sb.append(block4);
		return sb.toString();
	}
	
	/**
	 * Get a Block Number
	 * @return
	 */
	private int getBlockNumber() {
		int block = 9901;
		try {
			block = cArray[getRandomNumber(1,80)];
			block += getRandomNumber(0,99);
		} catch(Exception ignored) {
			// nothing to print
		}
		return block;
	}
	
	/**
	 * Get a Random Number within a Range
	 * @param start
	 * @param end
	 * @return
	 */
	private int getRandomNumber(int start, int end) {
		return new Random().nextInt(end-start) + start;
	}

	/**
	 * @return the month
	 */
	public int getMonth() {
		return month;
	}

	/**
	 * @return the year
	 */
	public int getYear() {
		return year;
	}

	/**
	 * @return the cardCode
	 */
	public int getCardCode() {
		return cardCode;
	}

	/**
	 * @return the cardNumber
	 */
	public String getCardNumber() {
		return cardNumber;
	}
	
	/**
	 * @return the cardHolder
	 */
	public String getCardHolder() {
		return cardHolder;
	}
	
	/**
	 * Test Card Number Generator
	 * @param args
	 */
	public static void main(String[] args) {
		for(int x=0; x<10; x++) {
			RandomCardNumber r1 = new RandomCardNumber();
			System.out.println(x+">> "+r1.getCardNumber()
				+"|N="+r1.getCardHolder()
				+"|M="+r1.getMonth()+"|Y="+r1.getYear()
				+"|CVV="+r1.getCardCode()
				);
		}
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}
}
