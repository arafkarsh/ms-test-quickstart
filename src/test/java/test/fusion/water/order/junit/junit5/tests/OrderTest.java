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
package test.fusion.water.order.junit.junit5.tests;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.OS;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.Order;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import static java.time.Duration.ofMillis;
import static java.time.Duration.ofMinutes;


import io.fusion.water.order.domain.models.Customer;
import io.fusion.water.order.domain.models.OrderEntity;
import test.fusion.water.order.junit.junit5.annotations.tests.Critical;
import test.fusion.water.order.junit.junit5.annotations.tools.Junit5;
import test.fusion.water.order.junit.junit5.annotations.tests.Functional;
import test.fusion.water.order.junit.junit5.extensions.TestTimeExtension;

import static java.lang.invoke.MethodHandles.lookup;
import static org.junit.jupiter.api.Assertions.*;
import static org.slf4j.LoggerFactory.getLogger;
import org.slf4j.Logger;

/**
 * Order Test Suite
 * 
 * @author arafkarsh
 *
 */
@Junit5()
@Functional()
@Critical()
@TestMethodOrder(OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(TestTimeExtension.class)
public class OrderTest {
	
	// Set Logger -> Lookup will automatically determine the class name.
	private static final Logger log = getLogger(lookup().lookupClass());

	private OrderEntity order;
	private int counter = 1;
	
	/**
	 * if the @TestInstance(TestInstance.Lifecycle.PER_CLASS)
	 * is available then the method need not be static
	 */
    @BeforeAll
    public void setupAll() {
        System.out.println("== Order tests Suite Execution Started...");
    }
    
    @BeforeEach
    public void setup() {
        System.out.println(counter+". Create an Empty Order..");
        order = new OrderEntity();
    }
    
    // @TestJunit5
    @Test
    @DisplayName("1. Should Create Customer in order")
    @Order(1)
    void shouldCreateCustomer() {
    	Customer c = new Customer("UUID", "John", "Doe", "0123456789");
    	System.out.println(c);
    	order = new OrderEntity.Builder()
    			.addCustomer(c)
    			.build();
    	assertTrue(order.isCustomerAvailable());
    }
    
    @Test
    @DisplayName("2. Should Not Create Customer When First Name is Null")
    @Order(2)
    void shouldThrowRuntimeExceptionWhenFirstNameIsNull() {
        Assertions.assertThrows(RuntimeException.class, () -> {
        	order = new OrderEntity.Builder()
        			.addCustomer(
        					new Customer
        					("UUID", null, "Doe", "0123456789"))
        			.build();

        });
        assertTrue(order.isCustomerAvailable());
    }
    
    @Test
    @DisplayName("3. Should Not Create Customer When Last Name is Null")
    @Order(3)
    void shouldThrowRuntimeExceptionWhenLastNameIsNull() {
        Assertions.assertThrows(RuntimeException.class, () -> {
        	order = new OrderEntity.Builder()
        			.addCustomer(
        					new Customer
        					("UUID", "John", null, "0123456789"))
        			.build();
        });
        assertTrue(order.isCustomerAvailable());
    }
    
    @Test
    @DisplayName("4. Should Not Create Contact When Phone Number is Null")
    @Order(4)
    void shouldThrowRuntimeExceptionWhenPhoneNumberIsNull() {
        Assertions.assertThrows(RuntimeException.class, () -> {
        	order = new OrderEntity.Builder()
        			.addCustomer(
        					new Customer
        					("UUID", "John", "Doe", null))
        			.build();        
        });
    }
    
    @Test
    @DisplayName("5. Test All Customer Tests")
    @Order(5)
    void testAllCustomerTests() {
    	assertAll(
    			() -> Assertions.assertThrows(RuntimeException.class, () -> {
    	        		order = new OrderEntity.Builder().addCustomer(
    	        				new Customer("UUID", "John", "Doe", null)).build();        
    	        	}),
    			() -> Assertions.assertThrows(RuntimeException.class, () -> {
	        		order = new OrderEntity.Builder().addCustomer(
	        				new Customer("UUID", "John", null, "0123456789")).build();        
	        		}),
    			() -> Assertions.assertThrows(RuntimeException.class, () -> {
	        		order = new OrderEntity.Builder().addCustomer(
	        				new Customer("UUID", null, "Doe", "0123456789")).build();        
	        		})
    	);
    }
    
    @Test
    @DisplayName("6. Should Create Customer if run on Linux OS")
    @EnabledOnOs(value = OS.LINUX, disabledReason = "Should Run only on Linux")
    @Order(6)
    void shouldCreateOrderOnLinux() {
    	order = new OrderEntity.Builder()
    			.addCustomer(
    					new Customer
    					("UUID", "John", "Doe", "0123456789"))
    			.build();
    	assertTrue(order.isCustomerAvailable());
    }
    
    /**
     * Set the Property in Run Configuration Properties
     * -ea -DENV=DEV (in VM Arguments)
     */
    @Test
    @DisplayName("7. Test Order Creation on Developer Machine")
    @Order(7)
    void shouldTestOrderCreationOnDEV() {
        System.setProperty("ENV", "DEV");
        System.out.println("Property = "+System.getProperty("ENV"));
        Assumptions.assumeTrue("DEV".equals(System.getProperty("ENV")));
    	order = new OrderEntity.Builder()
    			.addCustomer(
    					new Customer
    					("UUID", "John", "Doe", "0123456789"))
    			.build(); 
    	assertTrue(order.isCustomerAvailable());
    }
    
    @DisplayName("8. Repeat Contact Creation Test 3 Times")
    @RepeatedTest(value = 3,
            name = "Repeating Order Creation Test {currentRepetition} of {totalRepetitions}")
    @Order(8)
    void shouldTestOrderCreationRepeatedly() {
    	order = new OrderEntity.Builder()
    			.addCustomer(
    					new Customer
    					("UUID", "John", "Doe", "0123456789"))
    			.build(); 
    	assertTrue(order.isCustomerAvailable());
    }
    
    @DisplayName("9. Phone Number should match the required Format")
    @ParameterizedTest
    @ValueSource(strings = {"0123456777", "0123456888", "0123456999"})
    @Order(9)
    void shouldTestPhoneNumberFormatUsingValueSource(String phoneNumber) {
    	order = new OrderEntity.Builder()
    			.addCustomer(
    					new Customer
    					("UUID", "John", "Doe", phoneNumber))
    			.build(); 
    	assertTrue(order.isCustomerAvailable());
    	assertEquals(1, order.getCustomer().getPhoneList().size());
    }
    
    @DisplayName("10. CSV Source Case - Phone Number should match the required Format")
    @ParameterizedTest
    @CsvSource({"0123456777", "0123456888", "0123456999"})
    @Order(10)
    void shouldTestPhoneNumberFormatUsingCSVSource(String phoneNumber) {
    	order = new OrderEntity.Builder()
    			.addCustomer(
    					new Customer
    					("UUID", "John", "Doe", phoneNumber))
    			.build(); 
    	assertTrue(order.isCustomerAvailable());
    	assertEquals(1, order.getCustomer().getPhoneList().size());
    }
    
    @DisplayName("11. CSV File Source Case - Phone Number should match the required Format")
    @ParameterizedTest
    @CsvFileSource(resources = "/phoneList.csv")
    @Order(11)
    void shouldTestPhoneNumberFormatUsingCSVFileSource(String phoneNumber) {
    	order = new OrderEntity.Builder()
    			.addCustomer(
    					new Customer
    					("UUID", "John", "Doe", phoneNumber))
    			.build(); 
    	assertTrue(order.isCustomerAvailable());
    	assertEquals(1, order.getCustomer().getPhoneList().size());
    }
    
    @DisplayName("12. Method Source Case - Phone Number should match the required Format")
    @ParameterizedTest
    @MethodSource("phoneNumberList")
    @Order(12)
    void shouldTestPhoneNumberFormatUsingMethodSource(String phoneNumber) {
    	order = new OrderEntity.Builder()
    			.addCustomer(
    					new Customer
    					("UUID", "John", "Doe", phoneNumber))
    			.build(); 
    	assertTrue(order.isCustomerAvailable());
    	assertEquals(1, order.getCustomer().getPhoneList().size());
    }

    private static List<String> phoneNumberList() {
        return Arrays.asList("0123456789", "0123456798", "0123456897");
    }
    
    @DisplayName("13. Argument Source - Customer Object")
    @ParameterizedTest
    @ArgumentsSource(CustomerArgumentProvider.class)
    @Order(13)
    void testWithArgumentsSource(String uuid, String fn, String ln, String phone) {
        log.debug(" Parameterized test with (String) {} and (int) {} {} {} ", uuid, fn, ln, phone);

        assertNotNull(uuid);
        assertNotNull(fn);
        assertNotNull(ln);
        assertNotNull(phone);
    }
   
    @Test
    @DisplayName("14. Timeout Not Exceeded")
    @Order(14)
    void timeoutNotExceeded() {
          assertTimeout(ofMinutes(2), () -> {
              // Perform task that takes less than 2 minutes
          });
    }
    

    @Test
    @DisplayName("15. Timeout Exceeded")
    @Order(15)
    void timeoutExceeded() {
          assertTimeout(ofMillis(10), () -> {
                // Simulation
        		 Thread.sleep(4);
          });
    }
    
    @Test
    @DisplayName("16. Timeout Not Exceeded with Result")
    @Order(16)
    void timeoutNotExceededWithResult() {
        String actualResult = assertTimeout(ofMinutes(1), () -> {
            return "Hello Order Microservice!";
        });
        assertEquals("Hello Order Microservice!", actualResult);
    }

    @Test
    @DisplayName("17. Timeout Not Exceeded with Result from Method")
    @Order(17)
    void timeoutNotExceededWithMethod() {
        String actualGreeting = assertTimeout(ofMinutes(1),
        		OrderTest::timeOutResult);
        assertEquals("Hello Order Microservice!", actualGreeting);
    }

    /**
     * Result to Test AssertTimeout
     * @return
     */
    private static String timeOutResult() {
        return "Hello Order Microservice!";
    }
    
    // @Test
    @DisplayName("18. Timeout Exceeded and Terminated Preemptively")
    @Order(18)
    void timeoutExceededWithPreemptiveTermination() {
        assertTimeoutPreemptively(ofMillis(10), () -> {
            // Simulation
            Thread.sleep(9);
        });
    }
    
    
    @Test
    @DisplayName("19. Exception Testing - Order Processing Failed!")
    @Order(19)
    void exceptionTesting() {
        Throwable exception = assertThrows(IllegalArgumentException.class,
                () -> {
                    throw new IllegalArgumentException("Order Processing Failed!");
                });
        assertEquals("Order Processing Failed!", exception.getMessage());
    }
    
    @Test
    @DisplayName("20. Hamcrest AssertThat Assertion with Matchers")
    @Order(20)
    void assertWithHamcrestMatcher() {
        assertThat(2 + 3, equalTo(5));
        assertThat("JohnDoe", notNullValue());
        assertThat("Hello Microservices", containsString("Hello"));
    }

    /**
     * Example to show that this test is disabled
     */
    @Test
    @DisplayName("21.Test Should Be Disabled")
    @Disabled
    @Order(21)
    void shouldBeDisabled() {
        // This an example to show that this test is disabled
        assertThat("Hello Microservices", containsString("Hello"));
        throw new RuntimeException("Test Should Not be executed");
    }

    /**
     * if the @TestInstance(TestInstance.Lifecycle.PER_CLASS)
     * is available then the method need not be static
     */
    @AfterEach
    void tearDown() {
        System.out.println(counter+". Should Execute After Each Test");
        counter++;
    }
    
	/**
	 * if the @TestInstance(TestInstance.Lifecycle.PER_CLASS)
	 * is available then the method need not be static
	 */
    @AfterAll
    void tearDownAll() {
        System.out.println("== Order tests Suite Execution Completed...");
    }
}
