/**
 * (C) Copyright 2023 Araf Karsh Hamid
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
package test.fusion.water.order.testng.pact4.tests;

// PACT
import au.com.dius.pact.consumer.MockServer;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.core.model.RequestResponseInteraction;
import au.com.dius.pact.core.model.RequestResponsePact;
import au.com.dius.pact.core.model.annotations.Pact;

// App
import io.fusion.water.order.OrderApplication;
import io.fusion.water.order.domain.models.EchoResponseData;
import io.fusion.water.order.domain.models.PaymentDetails;
import io.fusion.water.order.domain.models.PaymentStatus;
import io.fusion.water.order.domain.services.PaymentService;
import io.fusion.water.order.utils.Utils;
import test.fusion.water.order.utils.SampleData;

// SpringBoot Test
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;

// TestNG
import org.testng.annotations.*;
import static org.testng.Assert.assertEquals;
import static org.testng.AssertJUnit.assertNotNull;

// Java
import java.io.IOException;
import java.util.HashMap;

// Hamcrest
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Currently PACT Doesnt support TestNG.
 *
 * Pact needs a MockServer Dynamically Injected which will do the Pact (Contract) Validation.
 * This is currently supported in Junit 5
 *
 * @author: Araf Karsh Hamid
 * @version:
 * @date:
 */
@SpringBootTest(classes={OrderApplication.class})
// @PactTestFor(providerName = "PaymentService")
public class OrderPaymentContractTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private PaymentService paymentService;

    private PaymentDetails pd;
    private PaymentStatus ps;

    @BeforeClass
    public void setupAll() {
        System.out.println("== Payment Service SpringBoot/Pact HTTP Tests Started...");
    }

    @BeforeMethod
    public void setup() {
        System.out.println("@BeforeMethod: Payment Service is autowired using SpringBoot!");
        pd = SampleData.getPaymentDetails();
        ps = SampleData.getPaymentStatusAccepted(
                pd.getTransactionId(), pd.getTransactionDate());
    }

    /**
     * PACT = Remote Echo
     * @param builder
     * @return
     */
    @Pact(consumer = "OrderApplication")
    public RequestResponsePact remoteEcho(PactDslWithProvider builder) {
        System.out.println("creating Pact for /remoteEchoGet/");

        String param = new String("Jane");
        EchoResponseData expectedResult = new EchoResponseData("Jane");

        RequestResponsePact rrp = builder
            .given("Given the word service returns a greeting.", "word", param)
                .uponReceiving("word Jane, service returns a greeting.")
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

    /**
     * No Support for PACT in TestNG
     * Consumer Test = Remote Echo
     * @param mockServer
     * @throws IOException
     */
    // @Test(priority = 1, description = "1. Pact > Payment Service > Remote Echo > GET")
    // @PactTestFor(pactMethod = "remoteEcho", port="8080")
    public void remoteEchoGet(MockServer mockServer) {
        System.out.println("PACT    |> MockServer|"+mockServer.getUrl());
        String param = new String("Jane");
        EchoResponseData expectedResult =  new EchoResponseData("Jane");
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

    /**
     * No Support for PACT in TestNG
     * PACT = Process Payments
     * @param builder
     * @return
     */
    // @Pact(consumer = "OrderApplication")
    public RequestResponsePact processPayments(PactDslWithProvider builder) {
        HashMap<String, String> headers = new HashMap<String,String>();
        headers.put("Content-Type", "application/json");
        headers.put("app", "bigBasket");

        RequestResponsePact rrp = builder
                .given("Payment Process")
                .uponReceiving("Payment Details")
                .path("/payment/")
                .method("POST")
                .headers(headers)
                .body(Utils.toJsonString(pd))
                .willRespondWith()
                .status(200)
                .body(Utils.toJsonString(ps))
                .toPact();
        System.out.println("PACT="+rrp);
        for(RequestResponseInteraction rri : rrp.getInteractions()) {
            System.out.println(rri);
        }
        return rrp;
    }

    /**
     * No Support for PACT in TestNG
     * Consumer = Process Payments
     * @param mockServer
     * @throws IOException
     */
    @Test(priority = 2, description = "2. Pact > Payment Service > Process Payments")
    @PactTestFor(pactMethod = "processPayments", port="8080")
    public void processPaymentsPost(MockServer mockServer) {
        System.out.println("PACT    |> MockServer|"+mockServer.getUrl());
        PaymentStatus paymentStatus = null;
        try {
            paymentStatus = paymentService.processPaymentsExternal(pd);
        } catch(Exception e) {
            System.out.println("ERROR: "+e.getMessage());
            e.printStackTrace();
        }
        System.out.println("Pass 2");
        assertNotNull(paymentStatus);
        assertEquals(paymentStatus.getPayStatus(), "Accepted");
        System.out.println("Pass 3");
    }

    @AfterMethod
    public void tearDown() {
        // Clean Up
    }

    @AfterClass
    public void tearDownAll() {
        System.out.println("== Payment Service SpringBoot/Pact HTTP Tests Completed...");
    }
}
