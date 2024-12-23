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
import io.fusion.water.order.utils.Std;
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
	
	private String payments 	= "/payment";
	private String remoteEcho 	= "/remoteEcho";
	private String order 	= "/order";

	private String paymentURL;
	private String echoURL;
	private String orderURL;

	private boolean urlsSet = false;

	// Autowired using the Constructor Injection
	private ServiceConfiguration serviceConfig;

	// Autowired using the Constructor Injection
	private RestClientService restClient;

	private static final String REMOTE = "REMOTE  |> ";
	private static final String REQUEST = "REQUEST |> ";
	private static final String RESPONSE = "RESPONSE |> ";
	private static final String SESSIONID = "sessionId";
	private static final String BIGBASKET = "bigBasket";
	
	/**
	 * Autowired using the Constructor Injection
	 * @param serviceConfig
	 * @param restClient
	 */
	@Autowired
	public ExternalGateWay(ServiceConfiguration serviceConfig, RestClientService restClient) {
		Std.println(LocalDateTime.now()+"|GateWay Constructor(RestClient) ...");
		this.serviceConfig = serviceConfig;
		this.restClient = restClient;
		setURLs();
	}
	
	/**¡
	 * Only for Testing outside SpringBoot Context
	 * Set the Payment GateWay
	 */
	public ExternalGateWay(String host, int port) {
		Std.println(LocalDateTime.now()+"|GateWay Constructor(host,port) ...");
		serviceConfig = new ServiceConfiguration(host, port);
		restClient = new RestClientService();
		setURLs();
	}
	
	/**
	 * Set the Base URLs
	 */
	private void setURLs() {
		if(!urlsSet) {
			String gwBaseURL;
			if(serviceConfig != null) {
				gwBaseURL = "http://" + serviceConfig.getRemoteHost() 
							+ ":" + serviceConfig.getRemotePort();
			} else {
				Std.println("INIT ERR|> Service Configuration NOT Available!!");
				gwBaseURL = "http://localhost:8080";
				
			}
			paymentURL = gwBaseURL + payments;
			echoURL = gwBaseURL + remoteEcho;
			orderURL = gwBaseURL + order;
			urlsSet = true;
			Std.println("INIT    |> Gateway Service Initialize.");
			Std.println(REMOTE+paymentURL+"/");
			Std.println(REMOTE+echoURL+"/");
			Std.println(REMOTE+orderURL+"/");

		}
	}

	/**
	 * Do a Remote Echo - For Testing Purpose ONLY
	 * 
	 * @param word
	 * @return
	 */
	public EchoResponseData remoteEcho(EchoData word) {
		setURLs();
		Std.println(REQUEST+Utils.toJsonString(word));
	    // Set Headers
		HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_JSON);
	    headers.add(SESSIONID, UUID.randomUUID().toString());
	    headers.add("app", BIGBASKET);

	    List<String> cookies = new ArrayList<>();
	    cookies.add("token="+UUID.randomUUID().toString());
	    cookies.add("domain=arafkarsh.com");
	    headers.put(HttpHeaders.COOKIE, cookies);
	    
		HttpEntity<EchoData> request = new HttpEntity<>(word, headers);
		Std.println(REQUEST+Utils.toJsonString(request));
		// Call Remote Service > POST
		EchoResponseData erd = restClient.postForObject(echoURL, request, EchoResponseData.class);
		Std.println(RESPONSE+Utils.toJsonString(erd));
		
		return erd;
	}
	
	/**
	 * Do a Remote Echo - For Testing Purpose ONLY
	 * 
	 * @param word
	 * @return
	 */
	public EchoResponseData remoteEcho(String word) {
		setURLs();
		Std.println(REQUEST+Utils.toJsonString(word));
		EchoResponseData erd = restClient.getForObject(echoURL +"/"+ word,  EchoResponseData.class);
		Std.println(RESPONSE+Utils.toJsonString(erd));
		return erd;
	}

	
	/**
	 * ONLY FOR TEST DEMO - PACT / WIREMOCK
	 * Process Payments
	 * 
	 * @param paymentDetails
	 * @return
	 */
	public PaymentStatus processPayments(PaymentDetails paymentDetails) {
		setURLs();
		Std.println(REQUEST+Utils.toJsonString(paymentDetails));
	    // Set Headers
		HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_JSON);
	    headers.add(SESSIONID, UUID.randomUUID().toString());
	    headers.add("app", BIGBASKET);

		HttpEntity<PaymentDetails> request = new HttpEntity<>(paymentDetails, headers);
		Std.println(REQUEST+Utils.toJsonString(request));
		// Call Remote Service > POST
		PaymentStatus ps = restClient.postForObject(paymentURL, request, PaymentStatus.class);
		Std.println(RESPONSE+Utils.toJsonString(ps));
		
		return ps;
	}

	/**
	 * ONLY FOR TEST DEMO - PACT / WIREMOCK
	 * Save Order
	 * @param order
	 * @return
	 */
	public OrderEntity saveOrder(OrderEntity order) {
		setURLs();
		Std.println(REQUEST+Utils.toJsonString(order));
		// Set Headers
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.add(SESSIONID, UUID.randomUUID().toString());
		headers.add("app", BIGBASKET);

		HttpEntity<OrderEntity> request = new HttpEntity<>(order, headers);
		Std.println(REQUEST+Utils.toJsonString(request));
		// Call Remote Service > POST
		OrderEntity ps = restClient.postForObject(orderURL, request, OrderEntity.class);
		Std.println(RESPONSE+Utils.toJsonString(ps));

		return ps;
	}
}
