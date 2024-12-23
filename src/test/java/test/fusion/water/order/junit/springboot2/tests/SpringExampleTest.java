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
package test.fusion.water.order.junit.springboot2.tests;

import static org.hamcrest.MatcherAssert.assertThat;

import io.fusion.water.order.OrderApplication;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.hamcrest.CoreMatchers.equalTo;

import io.fusion.water.order.domain.services.PaymentService;
import test.fusion.water.order.junit.junit5.annotations.tests.NonFunctional;
import test.fusion.water.order.junit.junit5.annotations.tools.SpringTest2;

/**
 * Spring Boot 2.5.2 with JUnit 5 Integration
 * 
 * The following Application Class is defined as the package structure
 * for the Application and package structure for test packages are 
 * completely different
 * 
 * @SpringBootTest(classes={io.fusion.water.order.OrderService.class})
 * 
 * @author arafkarsh
 *
 */

// Tags using Custom Annotations --------------
@SpringTest2()
@NonFunctional()
// --------------------------------------------
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(classes={OrderApplication.class})
public class SpringExampleTest {
	
	@Autowired
	PaymentService paymentService;
	
	/**
	 * if the @TestInstance(TestInstance.Lifecycle.PER_CLASS)
	 * is available then the method need not be static
	 */
    @BeforeAll
    public void setupAll() {
        System.out.println("@BeforeAll: SpringBoot Examples Suite Execution Started...");
    }
    
    @BeforeEach
    public void setup() {
        System.out.println("@BeforeEach: Executes Before Every Test.");
    }
	
	@Test
	@DisplayName("1. Spring Boot Testing Autowired Payment Service")
	public void paymentServiceTest() {
		String param = "World";
		String expectedResult = "Hello "+param;
		String result = paymentService.echo("World");
		System.out.println("@Test: Spring Boot test "+result);
        assertThat(expectedResult, equalTo(result));

	}
	
    @AfterEach
    public void tearDown() {
        System.out.println("@AferEach: Should Execute After Each Test");
    }

	/**
	 * if the @TestInstance(TestInstance.Lifecycle.PER_CLASS)
	 * is available then the method need not be static
	 */
    @AfterAll
    public void tearDownAll() {
        System.out.println("@AfterAll: SpringBoot Examples Suite Execution Completed...");
    }

}
