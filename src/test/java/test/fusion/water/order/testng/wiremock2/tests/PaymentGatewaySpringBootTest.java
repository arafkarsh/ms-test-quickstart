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
package test.fusion.water.order.testng.wiremock2.tests;

import io.fusion.water.order.OrderApplication;
import io.fusion.water.order.domain.models.EchoData;
import io.fusion.water.order.domain.models.EchoResponseData;
import io.fusion.water.order.domain.models.PaymentDetails;
import io.fusion.water.order.domain.models.PaymentStatus;
import io.fusion.water.order.domain.services.PaymentService;
import io.fusion.water.order.server.ServiceConfiguration;
import io.fusion.water.order.utils.Utils;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.*;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import test.fusion.water.order.utils.SampleData;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.testng.Assert.*;

/**
 *
 * @author: Araf Karsh Hamid
 * @version:
 * @date:
 */
@SpringBootTest(classes={OrderApplication.class})
public class PaymentGatewaySpringBootTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private ServiceConfiguration serviceConfig;

    private WireMockServer wireMockServer;
    private static int counter = 1;

    @BeforeClass
    public void setupAll() {
        System.out.println("== Payment Service SpringBoot/WireMock HTTP Tests Started... ");
        System.out.println("Host:"+serviceConfig.getRemoteHost()+":"+serviceConfig.getRemotePort());

        wireMockServer = new WireMockServer(serviceConfig.getRemotePort());
        wireMockServer.start();
        WireMock.configureFor(serviceConfig.getRemoteHost(), serviceConfig.getRemotePort());
        System.out.println(counter+"] WireMock Server Started.. on "+wireMockServer.baseUrl());
    }

    @BeforeMethod
    public void setup() {
        System.out.println("@BeforeMethod: Payment Service is autowired using SpringBoot!");
    }


    @Test(priority = 1)
    public void paymentServiceLocalEcho() {
        String param = "World";
        String expectedResult = "Hello "+param;
        String result = paymentService.echo("World");
        System.out.println("@Test: Spring Boot test "+result);
        assertEquals(expectedResult, result);
    }

    @Test(priority = 2)
    public void paymentServiceRemoteEcho() {
        EchoData param = new EchoData("John");
        EchoResponseData expectedResult =  new EchoResponseData("John");

        stubFor(post("/remoteEcho")
                .withRequestBody(equalToJson(Utils.toJsonString(param)))
                .willReturn(okJson(Utils.toJsonString(expectedResult))));

        EchoResponseData response = paymentService.remoteEcho(param);

        assertNotNull(response);
        assertEquals(expectedResult.getWordData(), response.getWordData());

        verify(postRequestedFor(urlPathEqualTo("/remoteEcho"))
                .withRequestBody(equalToJson(Utils.toJsonString(param)))
                .withHeader("Content-Type", WireMock.equalTo("application/json")));
    }

    @Test(priority = 3)
    public void paymentServiceTest1() {
        PaymentDetails pd = SampleData.getPaymentDetails();
        PaymentStatus ps = SampleData.getPaymentStatusAccepted(
                pd.getTransactionId(), pd.getTransactionDate());

        stubFor(post("/payment")
                .withRequestBody(equalToJson(Utils.toJsonString(pd)))
                .willReturn(okJson(Utils.toJsonString(ps))));

        PaymentStatus payStatus = paymentService.processPaymentsExternal(pd);

        assertNotNull(payStatus);
        assertEquals(payStatus.getPayStatus(), "Accepted" );

        verify(postRequestedFor(urlPathEqualTo("/payment"))
                .withRequestBody(equalToJson(Utils.toJsonString(pd)))
                .withHeader("Content-Type", WireMock.equalTo("application/json")));
    }

    @Test(priority = 4)
    public void paymentServiceTest2() {
        PaymentDetails pd = SampleData.getPaymentDetails();
        PaymentStatus ps = SampleData.getPaymentStatusDeclined(
                pd.getTransactionId(), pd.getTransactionDate());

        stubFor(post("/payment")
                .withRequestBody(equalToJson(Utils.toJsonString(pd)))
                .willReturn(okJson(Utils.toJsonString(ps))));

        PaymentStatus payStatus = paymentService.processPaymentsExternal(pd);

        assertNotNull(payStatus);
        assertEquals(payStatus.getPayStatus(), "Declined") ;

        verify(postRequestedFor(urlPathEqualTo("/payment"))
                .withRequestBody(equalToJson(Utils.toJsonString(pd)))
                .withHeader("Content-Type", WireMock.equalTo("application/json")));
    }

    @AfterMethod
    public void tearDown() {
        counter++;
    }

    @AfterClass
    public void tearDownAll() {
        wireMockServer.stop();
        System.out.println("== Payment Service SpringBoot/WireMock HTTP Tests Completed...");
    }
}
