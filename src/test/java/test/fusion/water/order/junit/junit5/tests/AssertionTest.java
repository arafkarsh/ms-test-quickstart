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
package test.fusion.water.order.junit.junit5.tests;
// JUnit 5
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTimeout;
import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;
import static org.junit.jupiter.api.Assertions.assertTrue;
// Custom
import test.fusion.water.order.junit.junit5.annotations.tests.Critical;
import test.fusion.water.order.junit.junit5.annotations.tests.Functional;
import test.fusion.water.order.junit.junit5.annotations.tools.Junit5;
import test.fusion.water.order.junit.junit5.extensions.TestTimeExtension;
import test.fusion.water.order.utils.Calculator;
import test.fusion.water.order.utils.Person;
// Java
import java.util.concurrent.CountDownLatch;
import static java.time.Duration.ofMillis;
import static java.time.Duration.ofMinutes;

/**
 * ms-test-quickstart / AssertionTest 
 *
 * @author: Araf Karsh Hamid
 * @version: 0.1
 * @date: 2024-12-28T8:08 PM
 */
@Junit5()
@Functional()
@Critical()
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(TestTimeExtension.class)
class AssertionTest {

    private Calculator calculator;
    private Person person;

    private int counter = 1;

    /**
     * if the @TestInstance(TestInstance.Lifecycle.PER_CLASS)
     * is available then the method need not be static
     */
    @BeforeAll
    public void setupAll() {
        System.out.println("== Assertion tests Suite Execution Started...");
    }

    @BeforeEach
    public void setup() {
        System.out.println(counter+". Create Calculator & Person.");
        calculator = new Calculator();
        person = new Person("Jane", "Doe", 27, "New York");
    }

    @DisplayName("1. Standard Assertion")
    @Order(1)
    @Test
    void standardAssertions() {
        assertEquals(2, calculator.add(1, 1));
        assertEquals(4, calculator.multiply(2, 2),
                "The optional failure message is now the last parameter");

        // Lazily evaluates generateFailureMessage('a','b').
        assertTrue('a' < 'b', () -> generateFailureMessage('a','b'));
    }

    @DisplayName("2. Grouped Assertion")
    @Order(2)
    @Test
    void groupedAssertions() {
        // In a grouped assertion all assertions are executed, and all
        // failures will be reported together.
        assertAll("person",
                () -> assertEquals("Jane", person.getFirstName()),
                () -> assertEquals("Doe", person.getLastName())
        );
    }

    @DisplayName("3. Dependent Assertion")
    @Order(3)
    @Test
    void dependentAssertions() {
        // Within a code block, if an assertion fails the
        // subsequent code in the same block will be skipped.
        assertAll("properties",
                () -> {
                    String firstName = person.getFirstName();
                    assertNotNull(firstName);

                    // Executed only if the previous assertion is valid.
                    assertAll("first name",
                            () -> assertTrue(firstName.startsWith("J")),
                            () -> assertTrue(firstName.endsWith("e"))
                    );
                },
                () -> {
                    // Grouped assertion, so processed independently
                    // of results of first name assertions.
                    String lastName = person.getLastName();
                    assertNotNull(lastName);

                    // Executed only if the previous assertion is valid.
                    assertAll("last name",
                            () -> assertTrue(lastName.startsWith("D")),
                            () -> assertTrue(lastName.endsWith("e"))
                    );
                }
        );
    }

    @DisplayName("4. Exception")
    @Order(4)
    @Test
    void exceptionTesting() {
        Exception exception = assertThrows(ArithmeticException.class, () ->
                calculator.divide(1, 0));
        assertEquals("/ by zero", exception.getMessage());
    }

    @DisplayName("5. Timeout Assertion")
    @Order(5)
    @Test
    void timeoutNotExceeded() {
        // The following assertion succeeds.
        assertTimeout(ofMinutes(2), () -> {
            // Perform task that takes less than 2 minutes.
        });
    }

    @DisplayName("6. Timeout Exceeded Assertion")
    @Order(6)
    @Test
    void timeoutNotExceededWithResult() {
        // The following assertion succeeds, and returns the supplied object.
        String actualResult = assertTimeout(ofMinutes(2), () -> {
            return "a result";
        });
        assertEquals("a result", actualResult);
    }

    @DisplayName("7. Timeout Not Exceeded with Method Assertion")
    @Order(7)
    @Test
    void timeoutNotExceededWithMethod() {
        // The following assertion invokes a method reference and returns an object.
        String actualGreeting = assertTimeout(ofMinutes(2), AssertionTest::demoGreetings);
        assertEquals("Hello, JUnit 5 World!", actualGreeting);
    }

    @DisplayName("8. Timeout Exceeded Assertion")
    @Order(8)
    @Test
    void timeoutExceeded() {
        // The following assertion fails with an error message similar to:
        // execution exceeded timeout of 10 ms by 91 ms
        assertTimeout(ofMillis(10), () -> {
            // Simulate task that takes more than 10 ms.
            Thread.sleep(100);
        });
    }

    @DisplayName("9. Timeout Exceeded With Preemptive termination")
    @Order(9)
    @Test
    void timeoutExceededWithPreemptiveTermination() {
        // The following assertion fails with an error message similar to:
        // execution timed out after 10 ms
        assertTimeoutPreemptively(ofMillis(10), () -> {
            // Simulate task that takes more than 10 ms.
            new CountDownLatch(1).await();
        });
    }

    /**
     * Demo Greetings
     * @return
     */
    private static String demoGreetings() {
        return "Hello, JUnit 5 World!";
    }

    /**
     * Generate Failure Message
     * @param a
     * @param b
     * @return
     */
    private static String generateFailureMessage(char a, char b) {
        return "Assertion messages can be lazily evaluated -- "
                + "to avoid constructing complex messages unnecessarily." + (a < b);
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
        System.out.println("== Assertion tests Suite Execution Completed...");
    }
}
