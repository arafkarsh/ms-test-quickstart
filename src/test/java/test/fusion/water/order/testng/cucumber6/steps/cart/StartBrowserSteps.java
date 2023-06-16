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
package test.fusion.water.order.testng.cucumber6.steps.cart;

import io.cucumber.java8.En;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import test.fusion.water.order.junit.selenium4.driver.BrowserState;
import test.fusion.water.order.webdriver.WebDriverChrome;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * 
 * @author arafkarsh
 *
 */
public class StartBrowserSteps implements En {

	/**
	 * Search Steps
	 * @param _browser
	 */
	public StartBrowserSteps(BrowserState _browser) {
		
		Given("I start a new browser", () -> {
			_browser.setWebDriver(WebDriverChrome.getChromeDriver(false, 60));
		});

		Given("I am on Amazon search page", () -> {
			_browser.getWebDriver().get("https://www.amazon.in");
		    System.out.println("Page:1] Browse>   https://www.amazon.in");

		});

		When("I do a search for {string}", (String keyword) -> {
			WebElement searchBox = _browser.getWebDriver().findElement(
					By.xpath("//input[@id='twotabsearchtextbox']"));
			
			System.out.println("Page:1] Search>   "+keyword);
			// 2.  Search for the Product
			searchBox.sendKeys(keyword);			  
			searchBox.submit();
		});

		When("I click the search button", () -> {
			// System.out.println("Page:1] Search Results>   ");
		});
		
		Then("I should see title {word}", (String title) -> {
			String x = _browser.getWebDriver().getTitle();
			assertThat(x, containsString(title));
		    _browser.getWebDriver().quit();
		});

	}
}
