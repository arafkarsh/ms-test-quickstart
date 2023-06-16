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

import java.util.Set;

/**
 * 
 * @author arafkarsh
 *
 */
public class SearchResultSteps implements En {
	
	/**
	 * Result Steps
	 * 
	 * @param _browser
	 */
	public SearchResultSteps(BrowserState _browser) {

		Then("the first title should be {string}", (String title) -> {

		    // 3. Go to Product Page
			WebElement product = _browser.getWebDriver().findElement(
					By.xpath("//*[@id=\"search\"]/div[1]/div[1]/div/span[1]/div[1]/div[3]/div/div/div/div/div/div/div/div[2]/div/div/div[1]/h2/a"));

			System.out.println("Page:1] Result>   "+product.getText());
		    // Wait for 3 Seconds before going to Product Page
		    Thread.sleep(3000);
		    product.click();

		    String window1 = _browser.getWebDriver().getWindowHandle();
		    String window2 = window1;
		    Set<String> windows = _browser.getWebDriver().getWindowHandles();
		    for(String s : windows) {
		    	window2 = s;
		    }
		    _browser.getWebDriver().switchTo().window(window2);

		    WebElement productDetails = _browser.getWebDriver()
		    		.findElement(By.xpath("//*[@id=\"title\"]"));
		    
		    System.out.println("Page:2] Details>  "+productDetails.getText());

			// 4. Add Product to Cart
			WebElement addToCart = _browser.getWebDriver().findElement(
					By.xpath("//*[@id=\"add-to-cart-button\"]"));
			System.out.println("Page:2] Add2Cart> "+productDetails.getText());
			addToCart.click();

		    System.out.println("Done....... !!!");
		    Thread.sleep(11000);
		    _browser.getWebDriver().quit();
		});
	}
}
