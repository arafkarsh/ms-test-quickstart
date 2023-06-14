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
package test.fusion.water.order.wiremock2.tests;

import com.github.tomakehurst.wiremock.client.WireMock;
import static com.github.tomakehurst.wiremock.client.WireMock.equalToJson;
import static com.github.tomakehurst.wiremock.client.WireMock.okJson;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.get;

import static com.github.tomakehurst.wiremock.client.WireMock.postRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo;
import static com.github.tomakehurst.wiremock.client.WireMock.verify;

import static org.hamcrest.MatcherAssert.assertThat;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import io.fusion.water.order.OrderApplication;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.github.tomakehurst.wiremock.WireMockServer;

import io.fusion.water.order.domainLayer.models.EchoData;
import io.fusion.water.order.domainLayer.models.EchoResponseData;
import io.fusion.water.order.domainLayer.models.PaymentDetails;
import io.fusion.water.order.domainLayer.models.PaymentStatus;
import io.fusion.water.order.domainLayer.services.PaymentService;
import io.fusion.water.order.server.ServiceConfiguration;
import io.fusion.water.order.utils.Utils;
import test.fusion.water.order.junit5.annotations.tests.Functional;
import test.fusion.water.order.junit5.annotations.tools.SpringTest2;
import test.fusion.water.order.junit5.annotations.tools.WireMock2;
import test.fusion.water.order.junit5.extensions.TestTimeExtension;
import test.fusion.water.order.utils.SampleData;

/**
 * Spring Boot 2.5.2 / JUnit 5 WireMock 2 Integration
 * 
 * The following Application Class is defined as the package structure
 * for the Application and package structure for test packages are 
 * completely different
 * 
 * @SpringBootTest(classes={io.fusion.water.order.OrderService.class})
 * 
 * @author arafkarsh
 *
 */

// Tags using Custom Annotations For Filtering --------------------------
@WireMock2()
@SpringTest2()
@Functional()
// Tagging done ---------------------------------------------------------
@TestMethodOrder(OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(classes={OrderApplication.class})
@ExtendWith(TestTimeExtension.class)
public class PaymentGatewaySpringBootTest {
	
	@Autowired
	private PaymentService paymentService;
	
	@Autowired
	private ServiceConfiguration serviceConfig;
	
	// WireMock Server for JUnit 5 
	private WireMockServer wireMockServer;
	private static int counter=1;
	
	/**
	 * if the @TestInstance(TestInstance.Lifecycle.PER_CLASS)
	 * is available then the method need not be static
	 */
    @BeforeAll
    public void setupAll() {
        System.out.println("== Payment Service SpringBoot/WireMock HTTP Tests Started... ");
        System.out.println("Host:"+serviceConfig.getRemoteHost()+":"
        						  +serviceConfig.getRemotePort());
        // Setup WireMock Server (Defaults to Port 8080)
    	wireMockServer = new WireMockServer(serviceConfig.getRemotePort());
        wireMockServer.start(); 
        WireMock.configureFor(serviceConfig.getRemoteHost(), serviceConfig.getRemotePort());       
        System.out.println(counter+"] WireMock Server Started.. on "
        					+wireMockServer.baseUrl());
    }
    
    @BeforeEach
    public void setup() {
        System.out.println("@BeforeEach: Payment Service is autowired using SpringBoot!");
    }
	
	@Test
	@DisplayName("1. Spring Boot Testing Autowired Payment Service")
	@Order(1)
	public void paymentServiceLocalEcho() {
		String param = "World";
		String expectedResult = "Hello "+param;
		String result = paymentService.echo("World");
		System.out.println("@Test: Spring Boot test "+result);
        assertThat(expectedResult, org.hamcrest.CoreMatchers.equalTo(result));

	}
	
	@Test
	@DisplayName("2. Payment Service HTTP : Remote Echo")
	@Order(2)
	public void paymentServiceRemoteEcho() {

		EchoData param = new EchoData("John");
		EchoResponseData expectedResult =  new EchoResponseData("John");
		
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
	@DisplayName("3. Payment Service HTTP : Accepted")
	@Order(3)
	public void paymentServiceTest1() {

		PaymentDetails pd = SampleData.getPaymentDetails();
	    PaymentStatus ps = SampleData.getPaymentStatusAccepted(
	    		pd.getTransactionId(), pd.getTransactionDate());
		
	    // Given
	    stubFor(post("/payments")
		    .withRequestBody(equalToJson(Utils.toJsonString(pd)))
		    .willReturn(okJson(Utils.toJsonString(ps))));

	    // When
	    PaymentStatus payStatus = paymentService.processPaymentsExternal(pd);

	    // Then
	    assertNotNull(payStatus);
	    assertEquals("Accepted", payStatus.getPaymentStatus());

	    // Verify
	    verify(postRequestedFor(urlPathEqualTo("/payments"))
		        .withRequestBody(equalToJson(Utils.toJsonString(pd)))
		        .withHeader("Content-Type", 
		        WireMock.equalTo("application/json")));

	}
	
	@Test
	@DisplayName("4. Payment Service HTTP : Declined")
	@Order(4)
	public void paymentServiceTest2() {

		PaymentDetails pd = SampleData.getPaymentDetails();
	    PaymentStatus ps = SampleData.getPaymentStatusDeclined(
	    		pd.getTransactionId(), pd.getTransactionDate());
		
	    // Given
	    stubFor(post("/payments")
		    .withRequestBody(equalToJson(Utils.toJsonString(pd)))
		    .willReturn(okJson(Utils.toJsonString(ps))));

	    // When
	    PaymentStatus payStatus = paymentService.processPaymentsExternal(pd);

	    // Then
	    assertNotNull(payStatus);
	    assertEquals("Declined", payStatus.getPaymentStatus());

	    // Verify
	    verify(postRequestedFor(urlPathEqualTo("/payments"))
		        .withRequestBody(equalToJson(Utils.toJsonString(pd)))
		        .withHeader("Content-Type", 
		        		WireMock.equalTo("application/json")));
	
	}
	
    @AfterEach
    public void tearDown() {
        counter++;
    }

    @AfterAll
    public void tearDownAll() {
        wireMockServer.stop();
        System.out.println("== Payment Service SpringBoot/WireMock HTTP Tests Completed...");
    }
}
