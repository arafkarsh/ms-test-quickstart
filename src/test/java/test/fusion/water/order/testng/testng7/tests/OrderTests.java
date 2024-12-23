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
import io.fusion.water.order.domain.models.Customer;
import io.fusion.water.order.domain.models.OrderEntity;
import org.junit.Assume;
import org.testng.Assert;
import org.testng.annotations.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import test.fusion.water.order.junit.junit5.tests.OrderTest;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import static org.testng.Assert.fail;
import static org.testng.Assert.assertThrows;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.expectThrows;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.containsString;

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

    @Test(priority = 1, description = "1. Should Create Customer in order", timeOut = 120000)
    public void shouldCreateCustomer() {
        Customer c = new Customer("UUID", "John", "Doe", "0123456789");
        System.out.println(c);
        order = new OrderEntity.Builder().addCustomer(c).build();
        Assert.assertTrue(order.isCustomerAvailable());
    }

    @Test(priority = 2, description = "2. Should Not Create Customer When First Name is Null")
    public void shouldThrowRuntimeExceptionWhenFirstNameIsNull() {
        try {
            order = new OrderEntity.Builder()
                    .addCustomer(
                            new Customer
                            ("UUID", null, "Doe", "0123456789"))
                    .build();
            Assert.fail("Expected RuntimeException");
        } catch(RuntimeException ex) {
            // Exception was thrown, test passes.
        }
    }

    @Test(priority = 3, description = "3. Should Not Create Customer When Last Name is Null")
    public void shouldThrowRuntimeExceptionWhenLastNameIsNull() {
        try {
            order = new OrderEntity.Builder()
                    .addCustomer(
                            new Customer
                                    ("UUID", "John", null, "0123456789"))
                    .build();
            Assert.fail("Expected RuntimeException");
        } catch(RuntimeException ex) {
            // Exception was thrown, test passes.
        }
    }

    @Test(priority = 4, description = "4. Should Not Create Contact When Phone Number is Nul")
    public void shouldThrowRuntimeExceptionWhenPhoneNumberIsNull() {
        try {
            order = new OrderEntity.Builder()
                    .addCustomer(
                            new Customer
                                    ("UUID", "John", "Doe", null))
                    .build();
            Assert.fail("Expected RuntimeException");
        } catch(RuntimeException ex) {
            // Exception was thrown, test passes.
        }
    }

    @Test(priority = 5, description = "5. Test All Customer Tests")
    public void testAllCustomerTests() {
        assertThrows(RuntimeException.class, () -> {
            order = new OrderEntity.Builder().addCustomer(
                    new Customer("UUID", "John", "Doe", null)).build();
        });

        assertThrows(RuntimeException.class, () -> {
            order = new OrderEntity.Builder().addCustomer(
                    new Customer("UUID", "John", null, "0123456789")).build();
        });

        assertThrows(RuntimeException.class, () -> {
            order = new OrderEntity.Builder().addCustomer(
                    new Customer("UUID", null, "Doe", "0123456789")).build();
        });
    }

    @Test(priority = 6, description = "6. Should Create Customer if run on Mac OS")
    public void shouldCreateOrderOnMac() {
        // Skip test if not on Linux
        Assume.assumeTrue("Should Run only on Mac", System.getProperty("os.name").startsWith("Mac"));

        order = new OrderEntity.Builder()
                .addCustomer(
                        new Customer
                                ("UUID", "John", "Doe", "0123456789"))
                .build();
        Assert.assertTrue(order.isCustomerAvailable());
    }

    @Test(priority = 7, description = "7. Test Order Creation on Developer Machine")
    public void shouldTestOrderCreationOnDEV() {
        System.setProperty("ENV", "DEV");
        System.out.println("Property = " + System.getProperty("ENV"));
        // Skip the test if the system property "ENV" is not "DEV"
        Assume.assumeTrue("Running on non-DEV machine", "DEV".equals(System.getProperty("ENV")));

        order = new OrderEntity.Builder()
                .addCustomer(
                        new Customer
                                ("UUID", "John", "Doe", "0123456789"))
                .build();
        Assert.assertTrue(order.isCustomerAvailable());
    }

    @Test(priority = 8, description = "8. Repeat Contact Creation Test 3 Times", invocationCount = 3)
    public void shouldTestOrderCreationRepeatedly() {
        order = new OrderEntity.Builder()
                .addCustomer(
                        new Customer
                                ("UUID", "John", "Doe", "0123456789"))
                .build();
        Assert.assertTrue(order.isCustomerAvailable());
    }

    @Test(priority = 9, description = "9. Phone Number should match the required Format", dataProvider = "phoneNumberProvider")
    public void shouldTestPhoneNumberFormatUsingValueSource(String phoneNumber) {
        order = new OrderEntity.Builder()
                .addCustomer(
                        new Customer
                                ("UUID", "John", "Doe", phoneNumber))
                .build();
        Assert.assertTrue(order.isCustomerAvailable());
        Assert.assertEquals(1, order.getCustomer().getPhoneList().size());
    }

    @DataProvider(name = "phoneNumberProvider")
    public Object[][] phoneNumberProvider() {
        return new Object[][] {
                {"0123456777"},
                {"0123456888"},
                {"0123456999"}
        };
    }

    @Test(priority = 13, description = "13. Argument Source - Customer Object", dataProvider = "customerArgumentProvider")
    public void testWithArgumentsSource(String uuid, String fn, String ln, String phone) {
        log.debug(">>> Parameterized test with (String) {} and (int) {} ", uuid, fn, ln, phone);
        Assert.assertNotNull(uuid);
        Assert.assertNotNull(fn);
        Assert.assertNotNull(ln);
        Assert.assertNotNull(phone);
    }


    @DataProvider(name = "customerArgumentProvider")
    public static Object[][] customerArgumentProvider() {
        return new Object[][] {
                {"UUID1", "John", "Doe", "0123456666"},
                {"UUID2", "Jane", "Doe", "0123456777"},
                {"UUID3", "John", "Doe Jr.", "0123456888"},
                {"UUID4", "Jane", "Doe Jr.", "0123456999"}
        };
    }

    @Test(priority = 14, description = "14. Timeout Not Exceeded", timeOut = 120000) // timeout value is in milliseconds
    public void timeoutNotExceeded() {
        // Perform task that takes less than 2 minutes
        System.out.println("Timeout as parameter to @Test Annotation");
    }

    @Test(priority = 15, description = "15. Timeout Exceeded", timeOut = 10) // timeout value is in milliseconds
    public void timeoutExceeded() throws InterruptedException {
        // Perform task that should take less than 10 milliseconds
        Thread.sleep(4);
    }

    @Test(priority = 16, description = "16. Timeout Not Exceeded with Result") // timeout value is in milliseconds
    public void timeoutNotExceededWithResult() throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newSingleThreadExecutor();

        Future<String> future = executorService.submit(() -> {
            // Task that returns a String
            return "Hello Order Microservice!";
        });

        String actualResult;
        try {
            actualResult = future.get(1, TimeUnit.MINUTES); // set timeout to 1 minute
        } catch (TimeoutException e) {
            fail("Test timed out.");
            return;
        }
        executorService.shutdownNow();

        assertEquals("Hello Order Microservice!", actualResult);
    }

    @Test(priority = 17, description = "17. Timeout Not Exceeded with Result from Method", invocationTimeOut = 60000)
    public void timeoutNotExceededWithMethod() {
        String actualGreeting;
        try {
            actualGreeting = timeOutResult();
        } catch (InterruptedException e) {
            throw new AssertionError("Test failed due to timeout");
        }
        assertEquals(actualGreeting, "Hello Order Microservice!");
    }

    /**
     * Result to Test AssertTimeout
     * @return
     */
    private static String timeOutResult() throws InterruptedException  {
        return "Hello Order Microservice!";
    }

    // @Test(priority = 18, description = "18. Timeout Exceeded and Terminated Preemptively", timeOut = 10000) // time in milliseconds
    public void timeoutExceededWithPreemptiveTermination() throws InterruptedException, TimeoutException, ExecutionException {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<?> future = executor.submit(() -> {
            try {
                // Perform task
                Thread.sleep(9000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        executor.shutdown();

        try {
            future.get(10, TimeUnit.MILLISECONDS);
        } catch (TimeoutException e) {
            future.cancel(true);
            throw e;
        }
    }

    @Test(priority = 19, description = "19. Exception Testing - Order Processing Failed!")
    public void exceptionTesting() {
        Throwable exception = expectThrows(IllegalArgumentException.class,
                () -> {
                    throw new IllegalArgumentException("Order Processing Failed!");
                });
        assertEquals("Order Processing Failed!", exception.getMessage());
    }

    @Test(priority = 20, description = "20. Hamcrest AssertThat Assertion with Matchers")
    public void assertWithHamcrestMatcher() {
        assertThat(2 + 3, equalTo(5));
        assertThat("JohnDoe", notNullValue());
        assertThat("Hello Microservices", containsString("Hello"));
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