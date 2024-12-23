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
package test.fusion.water.order.junit.pact4.tests;

import static org.hamcrest.MatcherAssert.assertThat;

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

import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.consumer.MockServer;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.core.model.RequestResponseInteraction;
import au.com.dius.pact.core.model.RequestResponsePact;
import au.com.dius.pact.core.model.annotations.Pact;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import io.fusion.water.order.domain.models.EchoResponseData;
import io.fusion.water.order.domain.services.PaymentService;
import io.fusion.water.order.utils.Utils;
import test.fusion.water.order.junit.junit5.annotations.tests.Critical;
import test.fusion.water.order.junit.junit5.annotations.tests.Functional;
import test.fusion.water.order.junit.junit5.annotations.tools.Pact4;
import test.fusion.water.order.junit.junit5.extensions.TestTimeExtension;

/**
 * 
 * @author arafkarsh
 *
 */
//Following Annotations Tags the Tests --------------------------------
@Pact4()
@Critical()
@Functional()
// Tagging done ---------------------------------------------------------

@TestMethodOrder(OrderAnnotation.class)
@ExtendWith(TestTimeExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(PactConsumerTestExt.class)
@PactTestFor(providerName = "PaymentService")
@SpringBootTest(classes={OrderApplication.class})
public class PactContractSimpleTest {
	
	@Autowired
	PaymentService paymentService;
		
	/**
	 * if the @TestInstance(TestInstance.Lifecycle.PER_CLASS)
	 * is available then the method need not be static
	 */
    @BeforeAll
    public void setupAll() {
        System.out.println("== Payment Service SpringBoot/Pact HTTP Tests Started...");
    }
    
    @BeforeEach
    public void setup() {
        System.out.println("@BeforeEach: Payment Service is autowired using SpringBoot!");
    }

    @Pact(consumer = "OrderApplication")
    public RequestResponsePact remoteEcho(PactDslWithProvider builder) {
		System.out.println("creating Pact for /remoteEchoGet/");
		
    	String param = new String("Jane");
		EchoResponseData expectedResult = new EchoResponseData("Jane");
		
		RequestResponsePact rrp = builder
			.given("The word Jane, service returns a greeting.", "word", param)
			.uponReceiving("The word Jane, the service returns a greeting.")
				.path("/remoteEcho/Jane")
			.willRespondWith()
				.status(200)
				.body(Utils.toJsonString(expectedResult))
			.toPact();

		System.out.println("PACT="+rrp);
		for(RequestResponseInteraction rri : rrp.getInteractions()) {
			System.out.println(rri);
		}
		return rrp;
    }

	@Test
	@DisplayName("1. Pact > Payment Service > Remote Echo > GET")
	@Order(1)
	@PactTestFor(pactMethod = "remoteEcho", port="8080")
	void remoteEchoGet(MockServer mockServer)  {
		System.out.println("MockServer|"+mockServer.getUrl());
		String param = new String("Jane");
		EchoResponseData expectedResult = new EchoResponseData("Jane");
		EchoResponseData result = null;
		try {
			result = paymentService.remoteEcho(param);
		} catch(Exception e) {
			System.out.println("ERROR: "+e.getMessage());
		}
		assertNotNull(result);
        assertThat(expectedResult.getWordData(), 
        		org.hamcrest.CoreMatchers.equalTo(result.getWordData()));
	}
	
    @AfterEach
    void tearDown() {
		// Clear the Order
    }

    @AfterAll
    public void tearDownAll() {
        System.out.println("== Payment Service SpringBoot/Pact HTTP Tests Completed...");
    } 

}
