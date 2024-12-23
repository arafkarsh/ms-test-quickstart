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
package test.fusion.water.order.testng.springboot2.tests;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

import io.fusion.water.order.OrderApplication;
import io.fusion.water.order.domain.services.PaymentService;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author: Araf Karsh Hamid
 * @version:
 * @date:
 */
@SpringBootTest(classes={OrderApplication.class})
public class SpringExampleTests extends AbstractTestNGSpringContextTests {

    @Autowired
    PaymentService paymentService;

    @BeforeClass
    public void setupAll() {
        System.out.println("@BeforeClass: SpringBoot Examples Suite Execution Started...");
    }

    @BeforeMethod
    public void setup() {
        System.out.println("@BeforeMethod: Executes Before Every Test.");
    }

    @Test(priority =1, description = "1. Autowired Bean method test")
    public void paymentServiceTest() {
        System.out.println("@Test: Spring Boot test ");
        String param = "World";
        String expectedResult = "Hello "+param;
        String result = paymentService.echo("World");
        System.out.println("@Test: Spring Boot test "+result);
        assertThat(expectedResult, equalTo(result));
    }

    @Test(priority =2, description = "2. Autowired Bean method result NULL Check")
    public void paymentServiceTest2() {
        System.out.println("@Test: Spring Boot test ");
        String param = "World";
        String expectedResult = "Hello "+param;
        String result = paymentService.echo("World");
        System.out.println("@Test: Spring Boot test "+result);
        assertThat(expectedResult, notNullValue());
    }

    @AfterMethod
    public void tearDown() {
        System.out.println("@AfterMethod: Should Execute After Each Test");
    }

    @AfterClass
    public void tearDownAll() {
        System.out.println("@AfterClass: SpringBoot Examples Suite Execution Completed...");
    }
}