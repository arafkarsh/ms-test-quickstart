/**
 * Copyright (c) 2024 Araf Karsh Hamid
 * <p>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * <p>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 * <p>
 * This program and the accompanying materials are dual-licensed under
 * either the terms of the Eclipse Public License v1.0 as published by
 * the Eclipse Foundation
 * <p>
 * or (per the licensee's choosing)
 * <p>
 * under the terms of the Apache 2 License version 2.0
 * as published by the Apache Software Foundation.
 */
package test.fusion.water.order.junit.wiremock2.tests;
// Custom
import io.fusion.water.order.adapters.external.ExternalGateWay;
import io.fusion.water.order.adapters.service.PaymentServiceImpl;
import io.fusion.water.order.domain.models.EchoData;
import io.fusion.water.order.domain.models.EchoResponseData;
import io.fusion.water.order.domain.models.PaymentDetails;
import io.fusion.water.order.domain.models.PaymentStatus;
import io.fusion.water.order.utils.Utils;
import org.junit.jupiter.api.*;
import test.fusion.water.order.junit.junit5.annotations.tests.Critical;
import test.fusion.water.order.junit.junit5.annotations.tests.Functional;
import test.fusion.water.order.junit.junit5.annotations.tools.WireMock3;
// JUnit 5
import com.github.tomakehurst.wiremock.WireMockServer;
// Spring
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestClient;
// WireMock
import org.wiremock.spring.ConfigureWireMock;
import org.wiremock.spring.EnableWireMock;
import org.wiremock.spring.InjectWireMock;
import test.fusion.water.order.utils.SampleData;
// Hamcrest
import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * ms-test-quickstart / PaymentServiceTest
 * Integrating Wiremock 3 with SpringBoot 3.3.x and JUnit 5
 * PaymentServiceTest
 * WIP - Work In Progress
 *
 * @author: Araf Karsh Hamid
 * @version: 0.1
 * @date: 2024-12-26T8:04 PM
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@WireMock3()
@Functional()
@Critical()
@SpringBootTest(classes = PaymentServiceTest.AppConfiguration.class)
@EnableWireMock({
        @ConfigureWireMock(name = "payment-service")
})
class PaymentServiceTest {

    @InjectWireMock("payment-service")
    private WireMockServer paymentServer;

    @SpringBootApplication
    static class AppConfiguration {}

    private final String host = "localhost";

    private PaymentServiceImpl paymentService;

    @BeforeEach
    public void setup() {
        System.out.println("Setup>> WireMock URL : "+ paymentServer.baseUrl() + " Port = "+paymentServer.port());
        ExternalGateWay gateway = new ExternalGateWay(host, paymentServer.port());
        paymentService = new PaymentServiceImpl(gateway);
    }

    @DisplayName("1. Payment Service: Returns a Ping")
    @Order(1)
    @Test
    void paymentServicePing() {
        System.out.println("1. WireMock URL : "+ paymentServer.baseUrl() + " Port = "+paymentServer.port());
        paymentServer.stubFor(get("/payment-service/ping").willReturn(ok("pong")));

        RestClient client = RestClient.create();
        String body = client.get()
                .uri(   paymentServer.baseUrl()+"/payment-service/ping")
                .retrieve()
                .body(String.class);

        assertThat(body, is("pong"));
    }

    @DisplayName("2. Payment Service: Echo Test")
    @Order(2)
    @Test
    void paymentServiceEchoTest() {
        EchoData requestData = new EchoData("John");
        EchoResponseData expectedResponse = new EchoResponseData("John");

        paymentServer.stubFor(
                post("/remoteEcho")
                .withRequestBody(equalToJson(Utils.toJsonString(requestData)))
                .willReturn(okJson(Utils.toJsonString(expectedResponse)))
        );

        EchoResponseData response = paymentService.remoteEcho(requestData);

        assertNotNull(response);
        assertEquals(expectedResponse.getWordData(), response.getWordData());

        verify(
                postRequestedFor(urlPathEqualTo("/remoteEcho"))
                .withRequestBody(equalToJson(Utils.toJsonString(requestData)))
                .withHeader("Content-Type", equalTo("application/json"))
        );
    }

    @Test
    @DisplayName("3. Payment Service: Tx Accepted")
    @Order(3)
    void paymentAccepted() {

        PaymentDetails pd = SampleData.getPaymentDetails();
        System.out.println("3. REQUEST: Payment Details:\n"+Utils.toJsonString(pd));

        PaymentStatus ps = SampleData.getPaymentStatusAccepted(
                pd.getTransactionId(), pd.getTransactionDate());
        System.out.println("3. RESPONSE: Payment Status:\n"+Utils.toJsonString(ps));

        // Given
        paymentServer.stubFor(
                post("/payment")
                .withRequestBody(equalToJson(Utils.toJsonString(pd)))
                .willReturn(okJson(Utils.toJsonString(ps)))
        );

        // When
        PaymentStatus payStatus = paymentService.processPaymentsExternal(pd);

        // Then
        assertNotNull(payStatus);
        assertEquals("Accepted", payStatus.getPayStatus());

        // Verify
        verify(
                postRequestedFor(urlPathEqualTo("/payment"))
                .withRequestBody(equalToJson(Utils.toJsonString(pd)))
                .withHeader("Content-Type", equalTo("application/json"))
        );
    }

    @Test
    @DisplayName("4. Payment Service: Tx Declined")
    @Order(4)
    void paymentDeclined() {

        PaymentDetails pd = SampleData.getPaymentDetails();
        System.out.println("4. REQUEST: Payment Details:\n"+Utils.toJsonString(pd));

        PaymentStatus ps = SampleData.getPaymentStatusDeclined(
                pd.getTransactionId(), pd.getTransactionDate());
        System.out.println("4. RESPONSE: Payment Status:\n"+Utils.toJsonString(ps));

        // Given
        paymentServer.stubFor(
                post("/payment")
                .withRequestBody(equalToJson(Utils.toJsonString(pd)))
                .willReturn(okJson(Utils.toJsonString(ps)))
        );

        // When
        PaymentStatus payStatus = paymentService.processPaymentsExternal(pd);

        // Then
        assertNotNull(payStatus);
        assertEquals("Declined", payStatus.getPayStatus());

        // Verify
        verify(
                postRequestedFor(urlPathEqualTo("/payment"))
                .withRequestBody(equalToJson(Utils.toJsonString(pd)))
                .withHeader("Content-Type", equalTo("application/json"))
        );
    }

}
