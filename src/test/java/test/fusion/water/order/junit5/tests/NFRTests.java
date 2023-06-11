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

package test.fusion.water.order.junit5.tests;


import static java.lang.invoke.MethodHandles.lookup;
import static org.slf4j.LoggerFactory.getLogger;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.slf4j.Logger;

import test.fusion.water.order.junit5.annotations.tests.NonFunctional;
import test.fusion.water.order.junit5.annotations.tests.PerformanceLoad;
import test.fusion.water.order.junit5.annotations.tests.PerformanceStress;
import test.fusion.water.order.junit5.annotations.tests.Security;
import test.fusion.water.order.junit5.annotations.tests.Usability;
import test.fusion.water.order.junit5.annotations.tools.Junit5;
/**
 * 
 * @author arafkarsh
 *
 */
@Junit5()
@NonFunctional()
@TestMethodOrder(OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class NFRTests {

	static final Logger log = getLogger(lookup().lookupClass());

    @Test
    @PerformanceLoad()
    @DisplayName("1. NFR Test (Performance/Load)")
    @Order(1)
    void testOne() {
        log.debug("NFR Test 1 (Performance/Load)");
    }

    @Test
    @PerformanceStress()
    @DisplayName("2. NFR Test (Performance/Stress)")
    @Order(2)
    void testTwo() {
        log.debug("NFR Test 2 (Performance/Stress)");
    }

    @Test
    @Security
    @DisplayName("3. NFR Test (Security)")
    @Order(3)
    void testThree() {
        log.debug("NFR Test 3 (Security)");
    }

    @Test
    @Usability()
    @DisplayName("4. NFR Test (Usability)")
    @Order(4)
    void testFour() {
        log.debug("NFR Test 4 (Usability)");
    }
}
