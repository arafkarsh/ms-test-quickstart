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


import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import io.fusion.water.order.domain.models.Customer;
import io.fusion.water.order.domain.models.OrderEntity;
import test.fusion.water.order.junit.junit5.annotations.tests.Functional;
import test.fusion.water.order.junit.junit5.annotations.tools.Junit5;

/**
 * Order Test Suite
 * 
 * @author arafkarsh
 *
 */
@Junit5()
@Functional()
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class OrderNestedTests {

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

    @DisplayName("1. Generic Test Cases")
    @Nested
    class genericTests {
        @Test
        @DisplayName("1.1 Should Create Customer in order")
        @Order(1)
        public void shouldCreateCustomer() {
            Customer c = new Customer("UUID", "John", "Doe", "0123456789");
            order = new OrderEntity.Builder().addCustomer(c).build();
            assertTrue(order.isCustomerAvailable());
        }

        @Test
        @DisplayName("1.2 Should Not Create Customer When First Name is Null")
        @Order(2)
        public void shouldThrowRuntimeExceptionWhenFirstNameIsNull() {
            Assertions.assertThrows(RuntimeException.class, () -> {
                order = new OrderEntity.Builder()
                        .addCustomer(new Customer("UUID", null, "Doe", "0123456789"))
                        .build();
            });
        }

        @Test
        @DisplayName("1.3 Should Not Create Customer When Last Name is Null")
        @Order(3)
        public void shouldThrowRuntimeExceptionWhenLastNameIsNull() {
            Assertions.assertThrows(RuntimeException.class, () -> {
                order = new OrderEntity.Builder()
                        .addCustomer(new Customer("UUID", "John", null, "0123456789"))
                        .build();
            });
        }

        @Test
        @DisplayName("1.4 Should Not Create Contact When Phone Number is Null")
        @Order(4)
        public void shouldThrowRuntimeExceptionWhenPhoneNumberIsNull() {
            Assertions.assertThrows(RuntimeException.class, () -> {
                order = new OrderEntity.Builder()
                        .addCustomer(new Customer("UUID", "John", "Doe", null))
                        .build();
            });
        }
    }

    @DisplayName("2. Repeat Contact Creation Test n Times")
    @Nested
    class RepeatedTests {
        @DisplayName("2.1 Repeat Contact Creation Test 3 Times")
        @RepeatedTest(value = 3,
                name = "Repeating Order Creation Test {currentRepetition} of {totalRepetitions}")
        @Order(1)
        public void shouldTestOrderCreationRepeatedly() {
            order = new OrderEntity.Builder()
                    .addCustomer(
                            new Customer
                                    ("UUID", "John", "Doe", "0123456789"))
                    .build();
            assertTrue(order.isCustomerAvailable());
        }
        @DisplayName("2.2 Repeat Contact Creation Test 5 Times")
        @RepeatedTest(value = 5,
                name = "Repeating Order Creation Test {currentRepetition} of {totalRepetitions}")
        @Order(2)
        public void shouldTestOrderCreationRepeatedly2() {
            order = new OrderEntity.Builder()
                    .addCustomer(
                            new Customer
                                    ("UUID", "John", "Doe", "0123456789"))
                    .build();
            assertTrue(order.isCustomerAvailable());
        }
    }

    @DisplayName("3. Parametrized Tests")
    @Nested
    class ParametrizedTests {
        @DisplayName("3.1 Phone Number should match the required Format")
        @ParameterizedTest
        @ValueSource(strings = {"0123456777", "0123456888", "0123456999"})
        @Order(1)
        public void shouldTestPhoneNumberFormatUsingValueSource(String phoneNumber) {
            order = new OrderEntity.Builder()
                    .addCustomer(
                            new Customer
                                    ("UUID", "John", "Doe", phoneNumber))
                    .build();
            assertTrue(order.isCustomerAvailable());
            assertEquals(1, order.getCustomer().getPhoneList().size());
        }

        @DisplayName("3.2 CSV Source Case - Phone Number should match the required Format")
        @ParameterizedTest
        @CsvSource({"0123456777", "0123456888", "0123456999"})
        @Order(2)
        public void shouldTestPhoneNumberFormatUsingCSVSource(String phoneNumber) {
            order = new OrderEntity.Builder()
                    .addCustomer(
                            new Customer
                                    ("UUID", "John", "Doe", phoneNumber))
                    .build();
            assertTrue(order.isCustomerAvailable());
            assertEquals(1, order.getCustomer().getPhoneList().size());
        }

        @DisplayName("3.3 CSV File Source Case - Phone Number should match the required Format")
        @ParameterizedTest
        @CsvFileSource(resources = "/phoneList.csv")
        @Order(3)
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
