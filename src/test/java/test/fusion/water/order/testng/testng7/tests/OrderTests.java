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
package test.fusion.water.order.testng.testng7.tests;

/**
 * @author: Araf Karsh Hamid
 * @version:
 * @date:
 */
import io.fusion.water.order.domainLayer.models.Customer;
import io.fusion.water.order.domainLayer.models.OrderEntity;
import org.testng.Assert;
import org.testng.annotations.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import test.fusion.water.order.junit.junit5.tests.OrderTest;

public class OrderTests {

    // Set Logger
    private static final Logger log = LogManager.getLogger(OrderTest.class.getName());

    private OrderEntity order;
    private int counter = 1;

    @BeforeClass
    public void setupAll() {
        System.out.println("== Order tests Suite Execution Started...");
    }

    @BeforeMethod
    public void setup() {
        System.out.println(counter + ". Create an Empty Order..");
        order = new OrderEntity();
    }

    @Test(priority = 1)
    public void shouldCreateCustomer() {
        Customer c = new Customer("UUID", "John", "Doe", "0123456789");
        System.out.println(c);
        order = new OrderEntity.Builder().addCustomer(c).build();
        Assert.assertTrue(order.isCustomerAvailable());
    }

    @Test(priority = 2)
    public void shouldThrowRuntimeExceptionWhenFirstNameIsNull() {
        try {
            order = new OrderEntity.Builder().addCustomer(
                    new Customer("UUID", null, "Doe", "0123456789")).build();
            Assert.fail("Expected RuntimeException");
        } catch(RuntimeException ex) {
            // Exception was thrown, test passes.
        }
    }


    @AfterMethod
    public void tearDown() {
        System.out.println(counter + ". Should Execute After Each Test");
        counter++;
    }

    @AfterClass
    public void tearDownAll() {
        System.out.println("== Order tests Suite Execution Completed...");
    }
}