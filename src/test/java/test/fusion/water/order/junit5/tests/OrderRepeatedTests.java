package test.fusion.water.order.junit5.tests;
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

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.OS;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Assumptions;

import io.fusion.water.order.domainLayer.models.Customer;
import io.fusion.water.order.domainLayer.models.OrderEntity;
import test.fusion.water.order.junit5.annotations.tests.Functional;
import test.fusion.water.order.junit5.annotations.tools.Junit5;

/**
 * Order Test Suite
 * 
 * @author arafkarsh
 *
 */
@Junit5()
@Functional()
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class OrderRepeatedTests {

	private OrderEntity order;
	
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
        System.out.println("Create an Empty Order..");
        order = new OrderEntity();
    }
    
    @Nested
    class genericTests {
	    @Test
	    @DisplayName("Should Create Customer in order")
	    public void shouldCreateCustomer() {
	    	Customer c = new Customer("UUID", "John", "Doe", "0123456789");
	    	order = new OrderEntity.Builder().addCustomer(c).build();
	    	assertTrue(order.isCustomerAvailable());
	    }
	    
	    @Test
	    @DisplayName("Should Not Create Customer When First Name is Null")
	    public void shouldThrowRuntimeExceptionWhenFirstNameIsNull() {
	        Assertions.assertThrows(RuntimeException.class, () -> {
	        	order = new OrderEntity.Builder()
	        			.addCustomer(new Customer("UUID", null, "Doe", "0123456789"))
	        			.build();
	        });
	    }
	    
	    @Test
	    @DisplayName("Should Not Create Customer When Last Name is Null")
	    public void shouldThrowRuntimeExceptionWhenLastNameIsNull() {
	        Assertions.assertThrows(RuntimeException.class, () -> {
	        	order = new OrderEntity.Builder()
	        			.addCustomer(new Customer("UUID", "John", null, "0123456789"))
	        			.build();
	        });
	    }

	    @Test
	    @DisplayName("Should Not Create Contact When Phone Number is Null")
	    public void shouldThrowRuntimeExceptionWhenPhoneNumberIsNull() {
	        Assertions.assertThrows(RuntimeException.class, () -> {
	        	order = new OrderEntity.Builder()
	        			.addCustomer(new Customer("UUID", "John", "Doe", null))
	        			.build();        
	        });
	    }
	    
	    @Test
	    @DisplayName("Should Create Customer if run on Linux OS")
	    @EnabledOnOs(value = OS.LINUX, disabledReason = "Should Run only on Linux")
	    public void shouldCreateOrderOnLinux() {
	    	order = new OrderEntity.Builder()
	    			.addCustomer(new Customer("UUID", "John", "Doe", "0123456789"))
	    			.build();
	    	assertTrue(order.isCustomerAvailable());
	    }
	    
	    /**
	     * Set the Property in Run Configuration Properties
	     * -ea -DENV=DEV (in VM Arguments)
	     */
	    @Test
	    @DisplayName("Test Order Creation on Developer Machine")
	    public void shouldTestOrderCreationOnDEV() {
	        Assumptions.assumeTrue("DEV".equals(System.getProperty("ENV")));
	    	order = new OrderEntity.Builder()
	    			.addCustomer(new Customer("UUID", "John", "Doe", "0123456789"))
	    			.build(); 
	    	assertTrue(order.isCustomerAvailable());
	    }
    }
    
    @Nested
    class RepeatedTests {
	    @DisplayName("Repeat Contact Creation Test 3 Times")
	    @RepeatedTest(value = 3,
	            name = "Repeating Order Creation Test {currentRepetition} of {totalRepetitions}")
	    public void shouldTestOrderCreationRepeatedly() {
	    	order = new OrderEntity.Builder()
	    			.addCustomer(
	    					new Customer
	    					("UUID", "John", "Doe", "0123456789"))
	    			.build(); 
	    	assertTrue(order.isCustomerAvailable());
	    }
    }
    
    @Nested
    class ParametrizedTests {
	    @DisplayName("Phone Number should match the required Format")
	    @ParameterizedTest
	    @ValueSource(strings = {"0123456777", "0123456888", "0123456999"})
	    public void shouldTestPhoneNumberFormatUsingValueSource(String phoneNumber) {
	    	order = new OrderEntity.Builder()
	    			.addCustomer(
	    					new Customer
	    					("UUID", "John", "Doe", phoneNumber))
	    			.build(); 
	    	assertTrue(order.isCustomerAvailable());
	    	assertEquals(1, order.getCustomer().getPhoneList().size());
	    }
	    
	    @DisplayName("CSV Source Case - Phone Number should match the required Format")
	    @ParameterizedTest
	    @CsvSource({"0123456777", "0123456888", "0123456999"})
	    public void shouldTestPhoneNumberFormatUsingCSVSource(String phoneNumber) {
	    	order = new OrderEntity.Builder()
	    			.addCustomer(
	    					new Customer
	    					("UUID", "John", "Doe", phoneNumber))
	    			.build(); 
	    	assertTrue(order.isCustomerAvailable());
	    	assertEquals(1, order.getCustomer().getPhoneList().size());
	    }
	    
	    @DisplayName("CSV File Source Case - Phone Number should match the required Format")
	    @ParameterizedTest
	    @CsvFileSource(resources = "/phoneList.csv")
	    public void shouldTestPhoneNumberFormatUsingCSVFileSource(String phoneNumber) {
	    	order = new OrderEntity.Builder()
	    			.addCustomer(
	    					new Customer
	    					("UUID", "John", "Doe", phoneNumber))
	    			.build(); 
	    	assertTrue(order.isCustomerAvailable());
	    	assertEquals(1, order.getCustomer().getPhoneList().size());
	    }
	    
    }
    
    @DisplayName("Method Source Case - Phone Number should match the required Format")
    @ParameterizedTest
    @MethodSource("phoneNumberList")
    public void shouldTestPhoneNumberFormatUsingMethodSource(String phoneNumber) {
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
    
    @AfterEach
    public void tearDown() {
        System.out.println("Should Execute After Each Test");
    }

	/**
	 * if the @TestInstance(TestInstance.Lifecycle.PER_CLASS)
	 * is available then the method need not be static
	 */
    @AfterAll
    public void tearDownAll() {
        System.out.println("== Order tests Suite Execution Completed...");
    }
}
