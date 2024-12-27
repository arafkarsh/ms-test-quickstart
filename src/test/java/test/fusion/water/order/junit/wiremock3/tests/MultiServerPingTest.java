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

// JUnit 5
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Order;
// Spring
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestClient;
// WireMock
import org.wiremock.spring.ConfigureWireMock;
import org.wiremock.spring.EnableWireMock;
import org.wiremock.spring.InjectWireMock;
import com.github.tomakehurst.wiremock.WireMockServer;
import static com.github.tomakehurst.wiremock.client.WireMock.*;
// Custom
import test.fusion.water.order.junit.junit5.annotations.tests.Critical;
import test.fusion.water.order.junit.junit5.annotations.tests.Functional;
import test.fusion.water.order.junit.junit5.annotations.tools.WireMock3;
// Hamcrest
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

/**
 * ms-test-quickstart / MultiServerPingTest
 * Integrating Wiremock 3 with SpringBoot 3.3.x and JUnit 5
 * Multi Service Ping Test
 *
 * @author: Araf Karsh Hamid
 * @version: 0.1
 * @date: 2024-12-26T8:04 PM
 */
@WireMock3()
@Functional()
@Critical()
@SpringBootTest(classes = MultiServerPingTest.AppConfiguration.class)
@EnableWireMock({
        @ConfigureWireMock(name = "order-service"),
        @ConfigureWireMock(name = "payment-service")
})
class MultiServerPingTest {

    @InjectWireMock("order-service")
    private WireMockServer orderService;

    @InjectWireMock("payment-service")
    private WireMockServer paymentService;

    @SpringBootApplication
    static class AppConfiguration {}

    @DisplayName("Order Service: Returns a Ping.")
    @Order(1)
    @Test
    void orderServicePing() {
        System.out.println("1. WireMock URL : "+ orderService.baseUrl());
        orderService.stubFor(get("/order-service/ping").willReturn(ok("pong")));

        RestClient client = RestClient.create();
        String body = client.get()
                .uri(   orderService.baseUrl()+"/order-service/ping")
                .retrieve()
                .body(String.class);

        assertThat(body, is("pong"));
    }

    @DisplayName("Payment Service: Returns a Ping.")
    @Order(2)
    @Test
    void paymentServicePing() {
        System.out.println("1. WireMock URL : "+ paymentService.baseUrl());
        paymentService.stubFor(get("/payment-service/ping").willReturn(ok("pong")));

        RestClient client = RestClient.create();
        String body = client.get()
                .uri(   paymentService.baseUrl()+"/payment-service/ping")
                .retrieve()
                .body(String.class);

        assertThat(body, is("pong"));
    }
}
