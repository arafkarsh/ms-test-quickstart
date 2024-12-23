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
package test.fusion.water.order.junit.wiremock2.tests;

import org.junit.ClassRule;

import org.junit.jupiter.api.BeforeEach;

import com.github.tomakehurst.wiremock.junit.WireMockClassRule;

import org.junit.jupiter.api.Test;
import test.fusion.water.order.junit.junit5.annotations.tests.Critical;
import test.fusion.water.order.junit.junit5.annotations.tests.Functional;
import test.fusion.water.order.junit.junit5.annotations.tools.WireMock2;

import static com.github.tomakehurst.wiremock.client.WireMock.*;


/**
 * WireMock with JUnit 4
 * 
 * @author arafkarsh
 *
 */

@WireMock2()
@Critical()
@Functional()
public class HttpMockClassRuleTest {

	@ClassRule
	public static WireMockClassRule wireMockRule0 = new WireMockClassRule(8089);
	
	
	///
	@BeforeEach
	public void setup() {
		System.out.println("This will never get executed... ");
		// Code written.. 
		
	}

	@Test
	void exampleTest() {
	    stubFor(post("/my/resource")
	        .withHeader("Content-Type", containing("xml"))
	        .willReturn(ok()
	            .withHeader("Content-Type", "text/xml")
	            .withBody("<response>SUCCESS</response>")));

	    verify(postRequestedFor(urlPathEqualTo("/my/resource"))
	        .withRequestBody(matching(".*message-1234.*"))
	        .withHeader("Content-Type", equalTo("text/xml")));
	}
}
