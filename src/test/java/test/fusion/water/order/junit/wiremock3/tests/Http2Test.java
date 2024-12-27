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
import io.fusion.water.order.domain.models.EchoData;
import io.fusion.water.order.domain.models.EchoResponseData;
import io.fusion.water.order.utils.Utils;
// Custom Testing
import test.fusion.water.order.junit.junit5.annotations.tests.Critical;
import test.fusion.water.order.junit.junit5.annotations.tests.Functional;
import test.fusion.water.order.junit.junit5.annotations.tools.WireMock3;
import test.fusion.water.order.junit.junit5.extensions.TestTimeExtension;
// JUnit 5
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.extension.ExtendWith;
// Spring
import org.springframework.web.client.RestTemplate;
// WireMock
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
// SSL
// import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
// import org.apache.hc.client5.http.impl.classic.HttpClients;
// import org.apache.hc.core5.ssl.SSLContexts;
// import org.apache.hc.core5.ssl.TrustStrategy;
//import org.apache.hc.core5.http.io.ssl.NoopHostnameVerifier;
// Java
// import javax.net.ssl.SSLContext;
// import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;


/**
 * ms-test-quickstart / Http2Test
 * Integrating Wiremock 3 with SpringBoot 3.3.x and JUnit 5
 * WireMock Examples
 * SSL + Http2 (WIP)
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
class Http2Test {

	// WireMock Server for Junit 5 
	private WireMockServer wireMockServer;

	private int port	= 8081;

	private static int counter=1;
	
    @BeforeAll
    public void setupAll() {
        System.out.println("0]== Basic Example WireMock HTTP Tests Started...");
    	// Setup WireMock Server (Defaults to Port 8081)
        // Configure WireMockServer with HTTP/2
        wireMockServer = new WireMockServer(
                WireMockConfiguration.wireMockConfig()
                        .dynamicHttpsPort()  // Enable HTTPS for HTTP/2 support
                        .jettyStopTimeout(5000L) // Optional: Configure Jetty stop timeout
        );
        wireMockServer.start();
        port = wireMockServer.httpsPort();
        System.out.println(counter+"]== WireMock Server Started.. on " +wireMockServer.baseUrl());
    }
    
    @BeforeEach
    public void setup() {
        // Initialize
    }

	@DisplayName("1. Basic HTTP Post Request with WireMock")
	@Order(1)
	// @Test
	void testWireMockWithSpringBoot() throws Exception {
        System.out.println("Spring Boot Example using Rest Template /api/data HTTP/2 : ["+port+"]");
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

    public RestTemplate createUnsafeRestTemplate() throws Exception {
        /**
        // Accept all certificates (unsafe, for testing purposes only)
        TrustStrategy acceptingTrustStrategy = (cert, authType) -> true;
        SSLContext sslContext = SSLContexts.custom()
                .loadTrustMaterial(null, acceptingTrustStrategy)
                .build();

        // Create HttpClient with custom SSL context and hostname verifier

        CloseableHttpClient httpClient = HttpClients.custom()
                .setSSLContext(sslContext)
                .setSSLHostnameVerifier(NoopHostnameVerifier.INSTANCE)
                .build();

        // Use HttpClient with RestTemplate
        HttpComponentsClientHttpRequestFactory requestFactory =
                new HttpComponentsClientHttpRequestFactory(httpClient);

        return new RestTemplate(requestFactory);
         */
        return null;
    }
}
