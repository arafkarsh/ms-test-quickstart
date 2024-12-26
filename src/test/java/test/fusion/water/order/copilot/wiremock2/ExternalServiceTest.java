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
package test.fusion.water.order.copilot.wiremock2;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import io.fusion.water.order.adapters.external.ExternalGateWay;
import io.fusion.water.order.adapters.service.PaymentServiceImpl;
import io.fusion.water.order.domain.models.EchoData;
import io.fusion.water.order.domain.models.EchoResponseData;
import io.fusion.water.order.utils.Utils;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import test.fusion.water.order.junit.junit5.annotations.tests.Critical;
import test.fusion.water.order.junit.junit5.annotations.tests.Functional;
import test.fusion.water.order.junit.junit5.annotations.tools.WireMock2;
import test.fusion.water.order.junit.junit5.extensions.TestTimeExtension;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


/**
 * ms-test-quickstart / ExternalServiceTest
 *
 * @author: Araf Karsh Hamid
 * @version: 0.1
 * @date: 2024-12-26T5:04 PM
 */

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@WireMock2
@Critical
@Functional
@ExtendWith(TestTimeExtension.class)
public class ExternalServiceTest {

    private WireMockServer wireMockServer;
    private PaymentServiceImpl paymentService;
    private final String host = "127.0.0.1";
    private final int port = 8081;

    @BeforeAll
    public void setupAll() {
        wireMockServer = new WireMockServer(port);
        wireMockServer.start();
        WireMock.configureFor(host, port);
    }

    @BeforeEach
    public void setup() {
        ExternalGateWay gateway = new ExternalGateWay(host, port);
        paymentService = new PaymentServiceImpl(gateway);
    }

    @Test
    @DisplayName("External Service - Echo Test")
    @Order(1)
    void externalServiceEchoTest() {
        EchoData requestData = new EchoData("John");
        EchoResponseData expectedResponse = new EchoResponseData("John");

        stubFor(post("/remoteEcho")
                .withRequestBody(equalToJson(Utils.toJsonString(requestData)))
                .willReturn(okJson(Utils.toJsonString(expectedResponse))));

        EchoResponseData response = paymentService.remoteEcho(requestData);

        assertNotNull(response);
        assertEquals(expectedResponse.getWordData(), response.getWordData());

        verify(postRequestedFor(urlPathEqualTo("/remoteEcho"))
                .withRequestBody(equalToJson(Utils.toJsonString(requestData)))
                .withHeader("Content-Type", equalTo("application/json")));
    }

    @AfterAll
    public void tearDownAll() {
        wireMockServer.stop();
    }
}