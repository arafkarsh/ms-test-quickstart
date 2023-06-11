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

package io.fusion.water.order.domainLayer.services;

import io.fusion.water.order.domainLayer.models.EchoData;
import io.fusion.water.order.domainLayer.models.EchoResponseData;
import io.fusion.water.order.domainLayer.models.PaymentDetails;
import io.fusion.water.order.domainLayer.models.PaymentStatus;

/**
 * Order Payment Service
 * 
 * @author arafkarsh
 *
 */
public interface PaymentService {
	
	/**
	 * Implementation
	 * 
	 * 1. Local API
	 * 2. REST API
	 * 3. SOAP API
	 * 
	 */
	/**
	 * Process Payments
	 * 
	 * @param _paymentDetails
	 * @return
	 */
	public PaymentStatus processPayments(PaymentDetails _paymentDetails);
	
	
	/**
	 * Returns the word with a greeting from Local 
	 * 
	 * @return
	 */
	public String echo(String _word);
	
	/**
	 * Returns the Echo from Remote Server
	 * @param _word
	 * @return
	 */
	public EchoResponseData remoteEcho(EchoData _word);
	
	/**
	 * Returns the Echo from Remote Server
	 * @param _word
	 * @return
	 */
	public EchoResponseData remoteEcho(String _word);

}
