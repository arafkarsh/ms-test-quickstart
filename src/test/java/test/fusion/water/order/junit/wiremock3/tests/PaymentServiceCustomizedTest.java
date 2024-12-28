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
package test.fusion.water.order.junit.wiremock3.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import io.fusion.water.order.adapters.external.ExternalGateWay;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.extension.ExtendWith;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;

import io.fusion.water.order.adapters.service.PaymentServiceImpl;
import io.fusion.water.order.domain.models.EchoData;
import io.fusion.water.order.domain.models.EchoResponseData;
import io.fusion.water.order.domain.models.PaymentDetails;
import io.fusion.water.order.domain.models.PaymentStatus;
import io.fusion.water.order.utils.Utils;
import test.fusion.water.order.junit.junit5.annotations.tests.Critical;
import test.fusion.water.order.junit.junit5.annotations.tests.Functional;
import test.fusion.water.order.junit.junit5.annotations.tools.WireMock3;
import test.fusion.water.order.junit.junit5.extensions.TestTimeExtension;
import test.fusion.water.order.utils.SampleData;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

/**
 * WireMock with JUnit 5
 * 
 * @author arafkarsh
 *
 */
//Following Annotations Tags the Tests --------------------------------
@WireMock3()
@Critical()
@Functional()
//Tagging done ---------------------------------------------------------
@TestMethodOrder(OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(TestTimeExtension.class)
class PaymentServiceCustomizedTest {

	// WireMock Server for Junit 5 
	private WireMockServer wireMockServer;
	
	private String host	= "127.0.0.1";
	private int port	= 8081;
	
	// Actual Payment Service
	PaymentServiceImpl paymentService;
	private static int counter=1;
	
    @BeforeAll
    public void setupAll() {
        System.out.println("0]== Payment Service WireMock HTTP Tests Started...");
    	// Setup WireMock Server (Defaults to Port 8080)
    	wireMockServer = new WireMockServer(port);
        wireMockServer.start(); 
        WireMock.configureFor(host, port);

        System.out.println(counter+"]== WireMock Server Started.. on " +wireMockServer.baseUrl());
    }
    
    @BeforeEach
    public void setup() {
        // Initialize Payment Service with Payment Gateway
        ExternalGateWay gw = new ExternalGateWay(host, port);
        paymentService = new PaymentServiceImpl(gw);

    }

	@Test
	@DisplayName("1. Payment Service HTTP : Remote Echo")
	@Order(1)
	void paymentServiceRemoteEcho() {
		EchoData param = new EchoData("John");
		EchoResponseData expectedResult = new EchoResponseData("John");

		// Given
	    stubFor(post("/remoteEcho")
		    .withRequestBody(equalToJson(Utils.toJsonString(param)))
		    .willReturn(okJson(Utils.toJsonString(expectedResult))));

	    // When
	    EchoResponseData response = paymentService.remoteEcho(param);

	    // Then
	    assertNotNull(response);
	    assertEquals(expectedResult.getWordData(), response.getWordData());

	    // Verify
	    verify(postRequestedFor(urlPathEqualTo("/remoteEcho"))
		        .withRequestBody(equalToJson(Utils.toJsonString(param)))
		        .withHeader("Content-Type", 
		        WireMock.equalTo("application/json")));	
	}
	
	@Test
	@DisplayName("2. Payment Service HTTP : Accepted")
	@Order(2)
	void paymentServiceTest1() {

		PaymentDetails pd = SampleData.getPaymentDetails();
	    PaymentStatus ps = SampleData.getPaymentStatusAccepted(
	    		pd.getTransactionId(), pd.getTransactionDate());
		
	    // Given
	    stubFor(post("/payment")
		    .withRequestBody(equalToJson(Utils.toJsonString(pd)))
		    .willReturn(okJson(Utils.toJsonString(ps))));

	    // When
	    PaymentStatus payStatus = paymentService.processPaymentsExternal(pd);

	    // Then
	    assertNotNull(payStatus);
	    assertEquals("Accepted", payStatus.getPayStatus());

	    // Verify
	    verify(postRequestedFor(urlPathEqualTo("/payment"))
		        .withRequestBody(equalToJson(Utils.toJsonString(pd)))
		        .withHeader("Content-Type", equalTo("application/json")));

	}
	
	@Test
	@DisplayName("3. Payment Service HTTP : Declined")
	@Order(3)
	void paymentServiceTest2() {

		PaymentDetails pd = SampleData.getPaymentDetails();
	    PaymentStatus ps = SampleData.getPaymentStatusDeclined(
	    		pd.getTransactionId(), pd.getTransactionDate());
		
	    // Given
	    stubFor(post("/payment")
		    .withRequestBody(equalToJson(Utils.toJsonString(pd)))
		    .willReturn(okJson(Utils.toJsonString(ps))));

	    // When
	    PaymentStatus payStatus = paymentService.processPaymentsExternal(pd);

	    // Then
	    assertNotNull(payStatus);
	    assertEquals("Declined", payStatus.getPayStatus());

	    // Verify
	    verify(postRequestedFor(urlPathEqualTo("/payment"))
		        .withRequestBody(equalToJson(Utils.toJsonString(pd)))
		        .withHeader("Content-Type", equalTo("application/json")));
	}
	
    @AfterEach
    public void tearDown() {
        counter++;
    }

    @AfterAll
    public void tearDownAll() {
        wireMockServer.stop();
        System.out.println("== Payment Service WireMock HTTP Tests Completed...");
    }
}
