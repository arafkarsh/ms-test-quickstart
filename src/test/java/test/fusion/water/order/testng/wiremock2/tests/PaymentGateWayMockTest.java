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

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import io.fusion.water.order.adapters.external.ExternalGateWay;
import io.fusion.water.order.adapters.service.PaymentServiceImpl;
import io.fusion.water.order.domain.models.EchoData;
import io.fusion.water.order.domain.models.EchoResponseData;
import io.fusion.water.order.domain.models.PaymentDetails;
import io.fusion.water.order.domain.models.PaymentStatus;
import io.fusion.water.order.utils.Utils;
import org.testng.annotations.*;
import test.fusion.water.order.utils.SampleData;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.testng.Assert.*;

/**
 * @author: Araf Karsh Hamid
 * @version:
 * @date:
 */
public class PaymentGateWayMockTest {

    private WireMockServer wireMockServer;

    private String host  = "127.0.0.1";
    private int port     = 8081;

    private PaymentServiceImpl paymentService;
    private static int counter = 1;

    @BeforeClass
    public void setupAll() {
        System.out.println("== Payment Service WireMock HTTP Tests Started...");
        wireMockServer = new WireMockServer(port);
        wireMockServer.start();
        WireMock.configureFor(host, port);

        System.out.println(counter + "] WireMock Server Started.. on " + wireMockServer.baseUrl());
    }

    @BeforeMethod
    public void setup() {
        ExternalGateWay gw = new ExternalGateWay(host, port);
        paymentService = new PaymentServiceImpl(gw);
    }

    @Test(priority = 1)
    public void paymentServiceRemoteEcho() {
        EchoData param = new EchoData("John");
        EchoResponseData expectedResult = new EchoResponseData("John");

        stubFor(post("/remoteEcho")
                .withRequestBody(equalToJson(Utils.toJsonString(param)))
                .willReturn(okJson(Utils.toJsonString(expectedResult))));

        EchoResponseData response = paymentService.remoteEcho(param);

        assertNotNull(response);
        assertEquals(expectedResult.getWordData(), response.getWordData());

        verify(postRequestedFor(urlPathEqualTo("/remoteEcho"))
                .withRequestBody(equalToJson(Utils.toJsonString(param)))
                .withHeader("Content-Type", equalTo("application/json")));
    }

    @Test(priority = 2)
    public void paymentServiceTest1() {
        PaymentDetails pd = SampleData.getPaymentDetails();
        PaymentStatus ps = SampleData.getPaymentStatusAccepted(
                pd.getTransactionId(), pd.getTransactionDate());

        stubFor(post("/payment")
                .withRequestBody(equalToJson(Utils.toJsonString(pd)))
                .willReturn(okJson(Utils.toJsonString(ps))));

        PaymentStatus payStatus = paymentService.processPaymentsExternal(pd);

        assertNotNull(payStatus);
        assertEquals(payStatus.getPayStatus(),"Accepted" );

        verify(postRequestedFor(urlPathEqualTo("/payment"))
                .withRequestBody(equalToJson(Utils.toJsonString(pd)))
                .withHeader("Content-Type", equalTo("application/json")));
    }

    @Test(priority = 3)
    public void paymentServiceTest2() {
        PaymentDetails pd = SampleData.getPaymentDetails();
        PaymentStatus ps = SampleData.getPaymentStatusDeclined(
                pd.getTransactionId(), pd.getTransactionDate());

        stubFor(post("/payment")
                .withRequestBody(equalToJson(Utils.toJsonString(pd)))
                .willReturn(okJson(Utils.toJsonString(ps))));

        PaymentStatus payStatus = paymentService.processPaymentsExternal(pd);

        assertNotNull(payStatus);
        assertEquals(payStatus.getPayStatus(),"Declined");

        verify(postRequestedFor(urlPathEqualTo("/payment"))
                .withRequestBody(equalToJson(Utils.toJsonString(pd)))
                .withHeader("Content-Type", equalTo("application/json")));
    }

    @AfterMethod
    public void tearDown() {
        counter++;
    }

    @AfterClass
    public void tearDownAll() {
        wireMockServer.stop();
        System.out.println("== Payment Service WireMock HTTP Tests Completed...");
    }
}