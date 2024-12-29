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
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assumptions.assumeTrue;
import static org.junit.jupiter.api.Assumptions.assumingThat;
// Custom
import test.fusion.water.order.junit.junit5.annotations.tests.Critical;
import test.fusion.water.order.junit.junit5.annotations.tests.Functional;
import test.fusion.water.order.junit.junit5.annotations.tools.Junit5;
import test.fusion.water.order.junit.junit5.extensions.TestTimeExtension;
import test.fusion.water.order.utils.Calculator;
/**
 * ms-test-quickstart / AssumptionTest
 *
 * Source: https://junit.org/junit5/docs/current/user-guide/#writing-tests-assumptions
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
class AssumptionTest {

    private Calculator calculator;

    private int counter = 1;

    /**
     * if the @TestInstance(TestInstance.Lifecycle.PER_CLASS)
     * is available then the method need not be static
     */
    @BeforeAll
    public void setupAll() {
        System.out.println("== Assumption tests Suite Execution Started...");
    }

    @BeforeEach
    public void setup() {
        System.out.println(counter+". Create Calculator.");
        calculator = new Calculator();
    }

    @DisplayName("1. Assumption Test - Assume True CI == ENV")
    @Order(1)
    @Test
    void testOnlyOnCiServer() {
        assumeTrue("CI".equals(System.getenv("ENV")));
        // remainder of test
        assertTrue(true);
    }

    @DisplayName("2. Assumption Test - Assume True DEV == ENV")
    @Order(2)
    @Test
    void testOnlyOnDeveloperWorkstation() {
        assumeTrue("DEV".equals(System.getenv("ENV")),
                () -> "Aborting test: not on developer workstation");
        // remainder of test
        assertTrue(true);
    }

    @DisplayName("3. Assumption Test - Assume True CI == ENV")
    @Order(3)
    @Test
    void testInAllEnvironments() {
        assumingThat("CI".equals(System.getenv("ENV")),
                () -> {
                    // perform these assertions only on the CI server
                    assertEquals(2, calculator.divide(4, 2));
                });

        // perform these assertions in all environments
        assertEquals(42, calculator.multiply(6, 7));
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
        System.out.println("== Assumption tests Suite Execution Completed...");
    }
}
