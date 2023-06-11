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

import java.time.LocalDateTime;
import java.util.UUID;

import io.fusion.water.order.domainLayer.models.CardDetails;
import io.fusion.water.order.domainLayer.models.CardType;
import io.fusion.water.order.domainLayer.models.Customer;
import io.fusion.water.order.domainLayer.models.EchoResponseData;
import io.fusion.water.order.domainLayer.models.OrderEntity;
import io.fusion.water.order.domainLayer.models.OrderItem;
import io.fusion.water.order.domainLayer.models.PaymentDetails;
import io.fusion.water.order.domainLayer.models.PaymentStatus;
import io.fusion.water.order.domainLayer.models.PaymentType;
import io.fusion.water.order.domainLayer.models.ShippingAddress;
import io.fusion.water.order.utils.Utils;

/**
 * 
 * @author arafkarsh
 *
 */
public class SampleData {
	
	/**
	 * Order Entity SampleData Data
	 * 
	 * @param args
	 */
	public static String OrderEntityToJson() {
		OrderEntity oEntity = new OrderEntity.Builder()
					.addCustomer(new Customer
        					("UUID", "John", "Doe", "0123456789"))
					.addOrderItem(new OrderItem
							("uuid1", "iPhone 12", 799, "USD", 1))
					.addOrderItem(new OrderItem
							("uuid2", "iPhone 12 Pro", 999, "USD", 1))
					.addOrderItem(new OrderItem
							("uuid3", "Apple Watch Series 6", 450, "USD", 2))
					.addShippingAddress(new ShippingAddress
							("321 Cobblestone Ln,", "", "Edison", "NJ", "", "USA", "08820"))
					.addPaymentType(PaymentType.CREDIT_CARD)
					.addCardDetails(createCardData())
					.build();
		
		return Utils.toJsonString(oEntity);
	}
	
	/**
	 * Payment Details SampleData Data
	 * 
	 * @return
	 */
	public static String PaymentDetailsToJson() {
		PaymentDetails p = new PaymentDetails(
				"fb908151-d249-4d30-a6a1-4705729394f4", 
				LocalDateTime.now(), 
				230, 
				PaymentType.CREDIT_CARD,
				createCardData()
				);
		
		return Utils.toJsonString(p);
	}
	
	/**
	 * 
	 * @return
	 */
	public static PaymentDetails getPaymentDetails() {
		return new PaymentDetails(
				UUID.randomUUID().toString(), 
				LocalDateTime.now(), 
				230, 
				PaymentType.CREDIT_CARD,
				createCardData()
				);
	}
	/**
	 * Create Random Credit Card Details
	 */
	public static CardDetails createCardData() {
		RandomCardNumber rc = new RandomCardNumber();
		return new CardDetails(
				rc.getCardNumber(), 
				rc.getCardHolder(), 
				rc.getMonth(), 
				rc.getYear(), 
				rc.getCardCode(), 
				CardType.MASTER);
	}
	
	/**
	 * Payment Status SampleData Data
	 * 
	 * @return
	 */
	public static String PaymentStatusToJson() {
		PaymentStatus ps = new PaymentStatus(
				"fb908151-d249-4d30-a6a1-4705729394f4", 
				LocalDateTime.now(), 
				"Accepted", 
				UUID.randomUUID().toString(), 
				LocalDateTime.now(), 
				PaymentType.CREDIT_CARD);
		
		return Utils.toJsonString(ps);
	}
	
	/**
	 * Get Payment Status Accepted
	 * 
	 * @param _txId
	 * @param _txDate
	 * @return
	 */
	public static PaymentStatus getPaymentStatusAccepted(
			String _txId, LocalDateTime _txDate) {
		return new PaymentStatus(
				_txId, 
				_txDate, 
				"Accepted", 
				UUID.randomUUID().toString(), 
				LocalDateTime.now(), 
				PaymentType.CREDIT_CARD);
	}
	
	public static EchoResponseData getEchoResponseData(
			String _word) {
		return new EchoResponseData(_word);
	}
	
	/**
	 * Get Payment Status Declined
	 * 
	 * @param _txId
	 * @param _txDate
	 * @return
	 */
	public static PaymentStatus getPaymentStatusDeclined(
			String _txId, LocalDateTime _txDate) {
		return new PaymentStatus(
				_txId, 
				_txDate, 
				"Declined", 
				UUID.randomUUID().toString(), 
				LocalDateTime.now(), 
				PaymentType.CREDIT_CARD);
	}
	
	/**
	 * For Testing Purpose Only
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		
		System.out.println("-Order Entity-");
		System.out.println(OrderEntityToJson());
		System.out.println("-Payment Details Entity-");
		System.out.println(PaymentDetailsToJson());
		System.out.println("-Payment Status Entity-");
		System.out.println(PaymentStatusToJson());
		
	}
}
