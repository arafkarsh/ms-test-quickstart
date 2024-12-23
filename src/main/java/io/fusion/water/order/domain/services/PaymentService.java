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

package io.fusion.water.order.domain.services;

import io.fusion.water.order.domain.models.EchoData;
import io.fusion.water.order.domain.models.EchoResponseData;
import io.fusion.water.order.domain.models.PaymentDetails;
import io.fusion.water.order.domain.models.PaymentStatus;

/**
 * Order Payment Service
 * 
 * @author arafkarsh
 *
 */
public interface PaymentService {

	/**
	 * Process Payments (used by Rest Assured)
	 * 
	 * @param paymentDetails
	 * @return
	 */
	public PaymentStatus processPayments(PaymentDetails paymentDetails);

	/**
	 * Process Payments External (used by WireMock)
	 * @param paymentDetails
	 * @return
	 */
	public PaymentStatus processPaymentsExternal(PaymentDetails paymentDetails);

	/**
	 * Returns the word with a greeting from Local 
	 * 
	 * @return
	 */
	public String echo(String word);
	
	/**
	 * Returns the Echo from Remote Server
	 * @param word
	 * @return
	 */
	public EchoResponseData remoteEcho(EchoData word);
	
	/**
	 * Returns the Echo from Remote Server
	 * @param word
	 * @return
	 */
	public EchoResponseData remoteEcho(String word);

}
