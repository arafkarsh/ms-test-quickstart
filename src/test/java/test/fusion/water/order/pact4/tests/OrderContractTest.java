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
package test.fusion.water.order.pact4.tests;

import au.com.dius.pact.consumer.MockServer;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.core.model.RequestResponseInteraction;
import au.com.dius.pact.core.model.RequestResponsePact;
import au.com.dius.pact.core.model.annotations.Pact;
import io.fusion.water.order.OrderApplication;
import io.fusion.water.order.domainLayer.models.OrderEntity;
import io.fusion.water.order.domainLayer.models.PaymentDetails;
import io.fusion.water.order.domainLayer.models.PaymentStatus;
import io.fusion.water.order.domainLayer.services.PaymentService;
import io.fusion.water.order.utils.Utils;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import test.fusion.water.order.junit5.annotations.tests.Critical;
import test.fusion.water.order.junit5.annotations.tests.Functional;
import test.fusion.water.order.junit5.annotations.tools.Pact4;
import test.fusion.water.order.junit5.extensions.TestTimeExtension;
import test.fusion.water.order.restassured.utils.OrderMockObjects;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * 
 * @author arafkarsh
 *
 */
//Following Annotations Tags the Tests --------------------------------
@Pact4()
@Critical()
@Functional()
//Tagging done ---------------------------------------------------------
@TestMethodOrder(OrderAnnotation.class)
@ExtendWith(TestTimeExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(PactConsumerTestExt.class)
@PactTestFor(providerName = "OrderService")
@SpringBootTest(classes={OrderApplication.class})
public class OrderContractTest {

    
	/**
	 * if the @TestInstance(TestInstance.Lifecycle.PER_CLASS)
	 * is available then the method need not be static
	 */
    @BeforeAll
    public void setupAll() {
        System.out.println("== Order Service SpringBoot/Pact HTTP Tests Started...");
    }
    
    @BeforeEach
    public void setup() {
        System.out.println("@BeforeEach: Order Service is autowired using SpringBoot!");
    }

	@Pact(consumer = "OrderApplication")
	public RequestResponsePact createPact(PactDslWithProvider builder) {
		System.out.println("creating Pact for Order Contract /order/");
		OrderEntity order = OrderMockObjects.mockGetOrderById("1234");
		RequestResponsePact rrp = builder
				.given("A request to save a shopping cart")
					.uponReceiving("A request to save a shopping cart")
					.path("/order/")
					.method("POST")
					.headers("Content-Type", MediaType.APPLICATION_JSON_VALUE)
					.body(Utils.toJson(order))
				.willRespondWith()
					.status(200)
					.body("Successfully saved Order")
				.toPact();

		System.out.println("PACT="+rrp);
		for(RequestResponseInteraction rri : rrp.getInteractions()) {
			System.out.println(rri);
		}
		return rrp;
	}
	
	@Test
	@DisplayName("1. Pact > Order Service: Save Order")
	@Order(1)
	@PactTestFor(pactMethod = "saveOrder", port="8080")
	public void saveOrder(MockServer mockServer) throws IOException {
		System.out.println("PACT    |> MockServer|"+mockServer.getUrl());
	}
	
    @AfterEach
    public void tearDown() {
    }

    @AfterAll
    public void tearDownAll() {
        System.out.println("== Payment Service SpringBoot/Pact HTTP Tests Completed...");
    }
}
