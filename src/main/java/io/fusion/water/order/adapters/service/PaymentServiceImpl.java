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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.fusion.water.order.adapters.external.PaymentGateWay;
import io.fusion.water.order.domainLayer.models.EchoData;
import io.fusion.water.order.domainLayer.models.EchoResponseData;
import io.fusion.water.order.domainLayer.models.PaymentDetails;
import io.fusion.water.order.domainLayer.models.PaymentStatus;
import io.fusion.water.order.domainLayer.models.PaymentType;
import io.fusion.water.order.domainLayer.services.PaymentService;
import io.fusion.water.order.server.ServiceConfiguration;

/**
 * Order Payment Service
 * 
 * @author arafkarsh
 *
 */
@Service
public class PaymentServiceImpl implements PaymentService {

	@Autowired
	private ServiceConfiguration serviceConfig;
	
	@Autowired
	private PaymentGateWay paymentGateWay;
	
	public PaymentServiceImpl() {
	}
	
	/**
	 * 
	 * @param _gw
	 */
	public PaymentServiceImpl(PaymentGateWay _gw) {
		paymentGateWay = _gw;
	}
	
	/**
	 * Echo Returns the word with a Greeting
	 * @return
	 */
	public String echo(String _word) {
		return "Hello "+_word;
	}
	
	/**
	 * Returns the Echo from the Remote Server
	 * @return
	 */
	public EchoResponseData remoteEcho(EchoData _word) {
		return paymentGateWay.remoteEcho(_word);
	}
	
	/**
	 * Returns the Echo from the Remote Server
	 */
	public EchoResponseData remoteEcho(String _word) {
		return paymentGateWay.remoteEcho(_word);
	}
	
	/**
	 * 
	 * @param _paymentDetails
	 * @return
	 */
	public PaymentStatus processPayments(PaymentDetails _paymentDetails) {
		// return paymentGateWay.processPayments(_paymentDetails);
		// For Testing Purpose ONLY
		return processPaymentsDefault(_paymentDetails);
	}
	
	/**
	 * Default 
	 * @param _paymentDetails
	 * @return
	 */
	public PaymentStatus processPaymentsDefault(PaymentDetails _paymentDetails) {
		return new PaymentStatus(
				_paymentDetails.getTransactionId(), 
				_paymentDetails.getTransactionDate(), 
				"Accepted", "Ref-uuid", 
				LocalDateTime.now(), 
				PaymentType.CREDIT_CARD);
	}

}
