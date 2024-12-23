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

package io.fusion.water.order.adapters.service;

import java.time.LocalDateTime;

import io.fusion.water.order.adapters.external.ExternalGateWay;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.fusion.water.order.domainLayer.models.EchoData;
import io.fusion.water.order.domainLayer.models.EchoResponseData;
import io.fusion.water.order.domainLayer.models.PaymentDetails;
import io.fusion.water.order.domainLayer.models.PaymentStatus;
import io.fusion.water.order.domainLayer.models.PaymentType;
import io.fusion.water.order.domainLayer.services.PaymentService;

/**
 * Order Payment Service
 * 
 * @author arafkarsh
 *
 */
@Service
public class PaymentServiceImpl implements PaymentService {

	// Autowired using the Constructor Injection
	private ExternalGateWay externalGateWay;

	public PaymentServiceImpl() {
		// Nothing to Instantiate
	}
	/**
	 *
	 * @param externalGateWay
	 */
	@Autowired
	public PaymentServiceImpl(ExternalGateWay externalGateWay) {
		this.externalGateWay = externalGateWay;
	}
	
	/**
	 * Echo Returns the word with a Greeting
	 * @return
	 */
	public String echo(String word) {
		return "Hello "+word;
	}
	
	/**
	 * Returns the Echo from the Remote Server
	 * @return
	 */
	public EchoResponseData remoteEcho(EchoData word) {
		return externalGateWay.remoteEcho(word);
	}
	
	/**
	 * Returns the Echo from the Remote Server
	 */
	public EchoResponseData remoteEcho(String word) {
		return externalGateWay.remoteEcho(word);
	}
	
	/**
	 *  Process Payments (used by Rest Assured)
	 *
	 * @param paymentDetails
	 * @return
	 */
	public PaymentStatus processPayments(PaymentDetails paymentDetails) {
		// For Testing Purpose ONLY
		return processPaymentsDefault(paymentDetails);
	}

	/**
	 * Process Payments External (used by WireMock)
	 *
	 * @param paymentDetails
	 * @return
	 */
	public PaymentStatus processPaymentsExternal(PaymentDetails paymentDetails) {
		return externalGateWay.processPayments(paymentDetails);
	}


	/**
	 * Default 
	 * @param paymentDetails
	 * @return
	 */
	public PaymentStatus processPaymentsDefault(PaymentDetails paymentDetails) {
		return new PaymentStatus(
				paymentDetails.getTransactionId(),
				paymentDetails.getTransactionDate(),
				"Accepted", "Ref-uuid", 
				LocalDateTime.now(), 
				PaymentType.CREDIT_CARD);
	}

}
