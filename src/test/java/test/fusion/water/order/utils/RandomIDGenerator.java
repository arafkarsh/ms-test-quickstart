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

import java.util.Random;

/**
 * 
 * @author arafkarsh
 */
public final class RandomIDGenerator {
	
	private final long timeMillis;
	private final String idBase;
	private final String idHexBase;
	private final int[] cArray = {	1100, 1200,1300,1400,1500,1600,1700,1800,1900,
									2100, 2200,2300,2400,2500,2600,2700,2800,2900,
									3100, 3200,3300,3400,3500,3600,3700,3800,3900,
									4100, 4200,4300,4400,4500,4600,4700,4800,4900,
									5100, 5200,5300,5400,5500,5600,5700,5800,5900,
									6100, 6200,6300,6400,6500,6600,6700,6800,6900,
									7100, 7200,7300,7400,7500,7600,7700,7800,7900,
									8100, 8200,8300,8400,8500,8600,8700,8800,8900,
									9100, 9200,9300,9400,9500,9600,9700,9800 };
	private int counter;
	
	
	/**
	 * Create Random ID Base
	 */
	public RandomIDGenerator() {
		timeMillis	= System.currentTimeMillis();
		idBase		= createIdBase();
		idHexBase	= createIdHexBase();
		counter		= getCounterInitialState();
	}
	
	/**
	 * Returns Counter Initial State
	 * @return
	 */
	private int getCounterInitialState() {
		try {
			return cArray[RandomIDGenerator.getRandomNumber(1,80)];
		} catch(Exception ignored) {}
		return 9900;
	}
	
	/**
	 * Generates a Random Number Between a Range
	 * 
	 * @param start
	 * @param end
	 * @return
	 */
	public static int getRandomNumber(int start, int end) {
		return new Random().nextInt(end-start) + start;
	}
	
	/**
	 * Create ID Base
	 * 
	 * @return
	 */
	private String createIdBase() {
		int result  = RandomIDGenerator.getRandomNumber(100, 1000);
		String ts 	= timeMillis+""+result;

		StringBuilder sb = new StringBuilder();
		for(int x=0,s=0; x<ts.length(); x++,s++) {
			sb.append(ts.charAt(x));
			if(s==4) {
				sb.append("-");
				s=-1;
			}
		}
		return sb.toString();
	}
	
	/**
	 * Creates Hex Base
	 * 
	 * @return
	 */
	private String createIdHexBase() {
		int result  = RandomIDGenerator.getRandomNumber(10000, 100000);
		String ts 	= timeMillis+""+result;
		try {
			ts = Long.toHexString(Long.parseLong(ts));
		} catch(Exception ignored) {}
				
		StringBuilder sb = new StringBuilder();
		for(int x=0,s=0; x<ts.length(); x++,s++) {
			sb.append(ts.charAt(x));
			if(s==5) {
				sb.append("-");
				s=-1;
			}
		}
		return sb.toString();
	}
	
	/**
	 * Returns Next Random + Sequence Number
	 * Generates a Random Number Base using Time in Millis + 3 Digit Random Number
	 * To the base number a 3 digit running sequence is added
	 * 
	 * @return
	 */
	public String nextRandomNumber() {
		counter++;
		return idBase + counter;
	}
	
	/**
	 * Returns Next Random + Sequence Number
	 * Generates a Random Number Base using Time in Millis + 5 Digit Random Number
	 * To the base number a 3 digit running sequence is added
	 * 
	 * @return
	 */
	public String nextRandomHexNumber() {
		counter++;
		return idHexBase + counter;
	}
}
