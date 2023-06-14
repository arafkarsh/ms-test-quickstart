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
package io.fusion.water.order.adapters.external;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import io.fusion.water.order.domainLayer.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import io.fusion.water.order.server.ServiceConfiguration;
import io.fusion.water.order.utils.Utils;

/***
 * 
 * @author arafkarsh
 *
 */
@Service
public class ExternalGateWay {

	@Autowired
	private ServiceConfiguration serviceConfig;
	
	private String payments 	= "/payments";
	private String remoteEcho 	= "/remoteEcho";
	private String order 	= "/order";


	private String gwBaseURL;
	private String paymentURL;
	private String echoURL;
	private String orderURL;

	private boolean urlsSet = false;
	
	@Autowired
	private RestClientService restClient;
	
	/**
	 * Only for Testing outside SpringBoot Context
	 */
	public ExternalGateWay() {
	}
	
	/**
	 * Only for Testing outside SpringBoot Context
	 * Set the Payment GateWay
	 */
	public ExternalGateWay(String _host, int _port) {
		System.out.println(LocalDateTime.now()+"|GateWay Constructor(host,port) ...");
		serviceConfig = new ServiceConfiguration(_host, _port);
		restClient = new RestClientService();
		setURLs();
	}
	
	/**
	 * Set the Base URLs
	 */
	private void setURLs() {
		if(!urlsSet) {
			if(serviceConfig != null) {
				gwBaseURL = "http://" + serviceConfig.getRemoteHost() 
							+ ":" + serviceConfig.getRemotePort();
			} else {
				System.out.println("INIT ERR|> Service Configuration NOT Available!!");
				gwBaseURL = "http://localhost:8080";
				
			}
			paymentURL = gwBaseURL + payments;
			echoURL = gwBaseURL + remoteEcho;
			orderURL = gwBaseURL + order;
			urlsSet = true;
			System.out.println("INIT    |> Gateway Service Initialize.");
			System.out.println("REMOTE  |> "+paymentURL+"/");
			System.out.println("REMOTE  |> "+echoURL+"/");
			System.out.println("REMOTE  |> "+orderURL+"/");

		}
	}

	/**
	 * Do a Remote Echo - For Testing Purpose ONLY
	 * 
	 * @param _word
	 * @return
	 */
	public EchoResponseData remoteEcho(EchoData _word) {
		setURLs();
		System.out.println("REQUEST |> "+Utils.toJsonString(_word));
	    // Set Headers
		HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_JSON);
	    headers.add("sessionId", UUID.randomUUID().toString());
	    headers.add("app", "bigBasket");

	    List<String> cookies = new ArrayList<>();
	    cookies.add("token="+UUID.randomUUID().toString());
	    cookies.add("domain=arafkarsh.com");
	    headers.put(HttpHeaders.COOKIE, cookies);
	    
		HttpEntity<EchoData> request = new HttpEntity<EchoData>(_word, headers);
		System.out.println("REQUEST |> "+Utils.toJsonString(request));
		// Call Remote Service > POST
		EchoResponseData erd = restClient.postForObject(echoURL, request, EchoResponseData.class);
		System.out.println("RESPONSE|> "+Utils.toJsonString(erd));
		
		return erd;
	}
	
	/**
	 * Do a Remote Echo - For Testing Purpose ONLY
	 * 
	 * @param _word
	 * @return
	 */
	public EchoResponseData remoteEcho(String _word) {
		setURLs();
		System.out.println("REQUEST |> "+Utils.toJsonString(_word));
		EchoResponseData erd = restClient.getForObject(echoURL +"/"+ _word,  EchoResponseData.class);
		System.out.println("RESPONSE|> "+Utils.toJsonString(erd));
		return erd;
	}

	
	/**
	 * ONLY FOR TEST DEMO - PACT / WIREMOCK
	 * Process Payments
	 * 
	 * @param _paymentDetails
	 * @return
	 */
	public PaymentStatus processPayments(PaymentDetails _paymentDetails) {
		setURLs();
		System.out.println("REQUEST |> "+Utils.toJsonString(_paymentDetails));
	    // Set Headers
		HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_JSON);
	    headers.add("sessionId", UUID.randomUUID().toString());
	    headers.add("app", "bigBasket");

		HttpEntity<PaymentDetails> request = new HttpEntity<PaymentDetails>(_paymentDetails, headers);
		System.out.println("REQUEST |> "+Utils.toJsonString(request));
		// Call Remote Service > POST
		PaymentStatus ps = restClient.postForObject(paymentURL, request, PaymentStatus.class);
		System.out.println("RESPONSE|> "+Utils.toJsonString(ps));
		
		return ps;
	}

	/**
	 * ONLY FOR TEST DEMO - PACT / WIREMOCK
	 * Save Order
	 * @param _order
	 * @return
	 */
	public OrderEntity saveOrder(OrderEntity _order) {
		setURLs();
		System.out.println("REQUEST |> "+Utils.toJsonString(_order));
		// Set Headers
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.add("sessionId", UUID.randomUUID().toString());
		headers.add("app", "bigBasket");

		HttpEntity<OrderEntity> request = new HttpEntity<OrderEntity>(_order, headers);
		System.out.println("REQUEST |> "+Utils.toJsonString(request));
		// Call Remote Service > POST
		OrderEntity ps = restClient.postForObject(orderURL, request, OrderEntity.class);
		System.out.println("RESPONSE|> "+Utils.toJsonString(ps));

		return ps;
	}
}
