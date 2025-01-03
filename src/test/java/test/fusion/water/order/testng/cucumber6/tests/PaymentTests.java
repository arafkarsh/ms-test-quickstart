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

package test.fusion.water.order.testng.cucumber6.tests;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * 
 * @author arafkarsh
 *
 */
@Test
@CucumberOptions(
		monochrome = true,
		plugin = {"pretty", 
				"html:target/cucumber-report/cucumber.html",
                "json:target/cucumber-report/cucumber.json",
                "junit:target/cucumber-report/cucumber.xml"},
		features = {"src/test/java/test/fusion/water/order/testng/cucumber6/specs/payment/"},
		glue = {"test.fusion.water.order.testng.cucumber6.steps.payment"}
)
// @Listeners(TestTimeListener.class)
public class PaymentTests extends AbstractTestNGCucumberTests {

	@BeforeClass
	public static void setupAll() {
		// setup
	}
}
