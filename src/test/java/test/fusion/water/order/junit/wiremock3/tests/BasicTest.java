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
package test.fusion.water.order.junit.wiremock3.tests;
// Custom
import io.fusion.water.order.adapters.external.ExternalGateWay;
import io.fusion.water.order.domain.models.EchoData;
import io.fusion.water.order.domain.models.EchoResponseData;
import io.fusion.water.order.utils.Utils;
// WireMock
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
// JUnit 5
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.extension.ExtendWith;
// Custom Testing
import org.springframework.web.client.RestTemplate;
import test.fusion.water.order.junit.junit5.annotations.tests.Critical;
import test.fusion.water.order.junit.junit5.annotations.tests.Functional;
import test.fusion.water.order.junit.junit5.annotations.tools.WireMock3;
import test.fusion.water.order.junit.junit5.extensions.TestTimeExtension;
// WireMock
import static com.github.tomakehurst.wiremock.client.WireMock.*;
// JUnit 5
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * ms-test-quickstart / BasicTest
 * Integrating Wiremock 3 with SpringBoot 3.3.x and JUnit 5
 * WireMock Examples
 *
 * @author: Araf Karsh Hamid
 * @version: 0.1
 * @date: 2024-12-26T8:04 PM
 */
// Following Annotations Tags the Tests --------------------------------
@WireMock3()
@Critical()
@Functional()
// Tagging done ---------------------------------------------------------
@TestMethodOrder(OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
// Uncomment the following if you want the Spring Context
// @SpringBootTest(classes={OrderApplication.class})
@ExtendWith(TestTimeExtension.class)
class BasicTest {

	// WireMock Server for Junit 5 
	private WireMockServer wireMockServer;

    private ExternalGateWay externalGateWay;
	
	private String host	= "localhost";
	private int port	= 8081;

	private static int counter=1;
	
    @BeforeAll
    public void setupAll() {
        System.out.println("0]== Basic Example WireMock HTTP Tests Started...");
    	// Setup WireMock Server (Defaults to Port 8081)
    	wireMockServer = new WireMockServer(port);
        wireMockServer.start(); 
        WireMock.configureFor(host, port);
        externalGateWay = new ExternalGateWay(host, port);
        System.out.println(counter+"]== WireMock Server Started.. on " +wireMockServer.baseUrl());
    }
    
    @BeforeEach
    public void setup() {
        // Initialize
    }

	@DisplayName("1. HTTP Post using External Gateway with WireMock")
	@Order(1)
	@Test
	void testWireMockWithSpringBoot() {
        System.out.println("Spring Boot Example using External Gateway /api/data");
        EchoData requestData = new EchoData("John");
        EchoResponseData expectedResponse = new EchoResponseData("John");

        // Given
        wireMockServer.stubFor(
                post("/remoteEcho")
                        .withRequestBody(equalToJson(Utils.toJsonString(requestData)))
                        .willReturn(okJson(Utils.toJsonString(expectedResponse))));

        // When
        EchoResponseData actualResponse = externalGateWay.remoteEcho(requestData);

        // Then
        assertNotNull(actualResponse);
        assertEquals(expectedResponse, actualResponse);
        assertEquals(expectedResponse.getWordData(), actualResponse.getWordData());

        // Verify
        verify(postRequestedFor(urlPathEqualTo("/remoteEcho"))
                .withRequestBody(equalToJson(Utils.toJsonString(requestData)))
                .withHeader("Content-Type",
                        WireMock.equalTo("application/json")));
	}

    @DisplayName("2. HTTP Post using Rest Template with WireMock")
    @Order(2)
    @Test
    void testWireMockWithSpringBoot2() {
        System.out.println("Spring Boot Example using Rest Template /api/data");
        EchoData requestData = new EchoData("John");
        EchoResponseData expectedResponse = new EchoResponseData("John");

        // Given
        wireMockServer.stubFor(
                post("/remoteEcho")
                        .withRequestBody(equalToJson(Utils.toJsonString(requestData)))
                        .willReturn(okJson(Utils.toJsonString(expectedResponse))));

        // When
        RestTemplate restClient = new RestTemplate();
        EchoResponseData actualResponse = restClient.postForObject(
                wireMockServer.baseUrl() + "/remoteEcho",
                requestData, EchoResponseData.class);

        // Then
        assertNotNull(actualResponse);
        assertEquals(expectedResponse, actualResponse);
        assertEquals(expectedResponse.getWordData(), actualResponse.getWordData());

        // Verify
        verify(postRequestedFor(urlPathEqualTo("/remoteEcho"))
                .withRequestBody(equalToJson(Utils.toJsonString(requestData)))
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
        System.out.println("== Basic Examples WireMock HTTP Tests Completed...");
    }
}
