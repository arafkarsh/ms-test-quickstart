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

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
/**
 * @author: Araf Karsh Hamid
 * @version:
 * @date:
 */
public class ConceptExampleTest {

    @BeforeClass
    public void setupAll() {
        System.out.println("@BeforeClass - Concept Examples Suite Execution Started...");
    }

    @BeforeMethod
    public void setup() {
        System.out.println("@BeforeMethod.... ");
    }

    @Test
    public void expectWrongResult() {
        double result = 7 / 3;
        System.out.println("T1 : 7 / 3 = "+result);
        Assert.assertEquals(result, 2.0);
    }

    @Test(expectedExceptions = ArithmeticException.class)
    public void exceptionWhileTesting() {
        double result = 11 / 0;
        System.out.println("T2 : 11 / 0 = "+result);
    }

    @AfterMethod
    public void tearDown() {
        System.out.println("@AfterMethod.... Should Execute After Each Test");
    }

    @AfterClass
    public void tearDownAll() {
        System.out.println("@AfterClass.... Concept Examples Suite Execution Completed...");
    }
}
