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
// JUnit 5
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
// Spring
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestClient;
// WireMock
import org.wiremock.spring.EnableWireMock;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.*;
// Hamcrest
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

/**
 * ms-test-quickstart / SingleServerPingTest
 * Integrating Wiremock 3 with SpringBoot 3.3.x and JUnit 5
 *
 * @author: Araf Karsh Hamid
 * @version: 0.1
 * @date: 2024-12-26T8:04 PM
 */
@SpringBootTest(classes = SingleServerPingTest.AppConfiguration.class)
@EnableWireMock
class SingleServerPingTest {

    @Value("${wiremock.server.baseUrl}")
    private String wireMockUrl;

    @DisplayName("Single Server Ping Test")
    @Order(1)
    @Test
    void helloWireMock3() {
        System.out.println("1. WireMock URL : "+wireMockUrl);
        stubFor(get("/ping").willReturn(ok("pong")));

        System.out.println("2. Calling  : "+wireMockUrl + "/ping");
        RestClient client = RestClient.create();
        String body = client.get()
                .uri(  wireMockUrl + "/ping")
                .retrieve()
                .body(String.class);

        System.out.println("3. Body : "+body);
        assertThat(body, is("pong"));
    }

    @SpringBootApplication
    static class AppConfiguration {}
}
