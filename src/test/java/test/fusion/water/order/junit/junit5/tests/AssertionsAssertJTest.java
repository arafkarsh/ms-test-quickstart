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
// Custom
import test.fusion.water.order.junit.junit5.annotations.tests.Critical;
import test.fusion.water.order.junit.junit5.annotations.tests.Functional;
import test.fusion.water.order.junit.junit5.annotations.tools.Junit5;
import test.fusion.water.order.junit.junit5.extensions.TestTimeExtension;
// Java
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
// AssertJ Assertions
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
/**
 * ms-test-quickstart / AssertionsAssertJTest
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
class AssertionsAssertJTest {

    private int counter = 1;

    /**
     * if the @TestInstance(TestInstance.Lifecycle.PER_CLASS)
     * is available then the method need not be static
     */
    @BeforeAll
    public void setupAll() {
        System.out.println("== AssertJ Assertions tests Suite Execution Started...");
    }

    @BeforeEach
    public void setup() {
        System.out.println(counter+". Setup Mock Objects if required.");
    }

    @DisplayName("1. Basic Assertions")
    @Order(1)
    @Test
    void basicAssertions() {
        int result = 5 + 3;

        assertThat(result)
                .isEqualTo(8)
                .isNotEqualTo(9)
                .isGreaterThan(7)
                .isLessThan(10);
    }

    @DisplayName("2. String Assertions")
    @Order(2)
    @Test
    void stringAssertions() {
        String message = "Welcome to AssertJ with JUnit 5";

        assertThat(message)
                .isNotEmpty()
                .contains("AssertJ")
                .startsWith("Welcome")
                .endsWith("JUnit 5")
                .doesNotContain("Hamcrest");
    }

    @DisplayName("3. Date Assertions")
    @Order(3)
    @Test
    void dateAssertion() {
        LocalDate today = LocalDate.now();
        LocalDate tomorrow = today.plusDays(1);

        assertThat(today).isBefore(tomorrow);
        assertThat(tomorrow).isAfter(today);
    }

    @DisplayName("4. Collection Assertions")
    @Order(4)
    @Test
    void collectionAssertions() {
        List<String> fruits = List.of("Apple", "Banana", "Cherry");

        assertThat(fruits)
                .isNotEmpty()
                .contains("Banana")
                .containsExactly("Apple", "Banana", "Cherry")
                .doesNotContain("Orange")
                .containsAnyOf("Grape", "Cherry");
    }

    @DisplayName("5. Map Assertions")
    @Order(5)
    @Test
    void mapAssertions() {
        Map<String, Integer> scores = Map.of("Alice", 90, "Bob", 85, "Charlie", 95);

        assertThat(scores)
                .isNotEmpty()
                .containsKey("Alice")
                .containsEntry("Bob", 85)
                .doesNotContainKey("Eve")
                .containsValues(90, 95);
    }

    @DisplayName("6. Stream Assertions")
    @Order(6)
    @Test
    void streamAssertions() {
        Stream<String> stream = Stream.of("one", "two", "three");

        assertThat(stream)
                .isNotEmpty()
                .contains("two", "three")
                .doesNotContain("four");
    }

    @DisplayName("7. Combined Assertions")
    @Order(7)
    @Test
    void combinedAssertions() {
        String message = "Hello, AssertJ!";
        assertThat(message)
                .isNotNull()
                .startsWith("Hello")
                .contains("AssertJ")
                .endsWith("!")
                .doesNotContain("JUnit 4");
    }

    @DisplayName("8. Exception Assertions")
    @Order(8)
    @Test
    void exceptionAssertions() {
        assertThatThrownBy(() -> {
            throw new IllegalArgumentException("Invalid argument");
        })
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Invalid argument")
                .hasMessageContaining("Invalid");
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
        System.out.println("== AssertJ Assertions tests Suite Execution Completed...");
    }
}
