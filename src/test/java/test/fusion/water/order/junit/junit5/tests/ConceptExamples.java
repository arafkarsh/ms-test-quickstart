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

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import test.fusion.water.order.junit.junit5.annotations.tests.NonFunctional;
import test.fusion.water.order.junit.junit5.annotations.tools.Junit5;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;


/**
 * Concept Test Suite
 * 
 * @author arafkarsh
 *
 */
@Junit5()
@NonFunctional()
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ConceptExamples {

	/**
	 * if the @TestInstance(TestInstance.Lifecycle.PER_CLASS)
	 * is available then the method need not be static
	 */
    @BeforeAll
    public void setupAll() {
        System.out.println("@BeforeAll Concepts Examples Suite Execution Started...");
    }
    
    @BeforeEach
    public void setup() {
        System.out.println("@BeforeEach.... ");
    }
    
    /**
     * Failure is when your Assertions is Wrong
     */
    @Test
    void expectWrongResult() {
        double result = 7 / 3;
        System.out.println("T1 : 7 / 3 = "+result);
        assertEquals(2, result);
    }
    
    /**
     * Error is when your tests didnt go thru.
     */
    @Test
    void exceptionWhileTesting() {
        double result = 11 / 0;
        System.out.println("T2 : 11 / 0 = "+result);
        assertEquals(3, result);
    }
    
    @AfterEach
    public void tearDown() {
        System.out.println("@AferEach.... Should Execute After Each Test");
    }

	/**
	 * if the @TestInstance(TestInstance.Lifecycle.PER_CLASS)
	 * is available then the method need not be static
	 */
    @AfterAll
    public void tearDownAll() {
        System.out.println("@AfterAll.... Concept Examples Suite Execution Completed...");
    }
}
