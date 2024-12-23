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
package test.fusion.water.order.testng.selenium4.tests;

// Selenium
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

// TestNG
import org.testng.annotations.Test;

// Web Driver
import test.fusion.water.order.webdriver.WebDriverChrome;

import java.util.Set;

/**
 * Selenium Testing
 * 
 * Use Case: Buy Product from Amazon
 * 
 * 1. Browse Amazon Web Site
 * 2. Search for a Product 
 * 3. Go to Product Page
 * 4. Add the Product to Cart
 * 
 * @author arafkarsh
 *
 */

public class WebBrowserTests {

	@Test(priority = 1, description = "Search, Find and Add Product to Cart")
	public void searchFindAddProductToCart() throws InterruptedException {

		WebDriverChrome.initialize();
		WebDriver browser = WebDriverChrome.getChromeDriver(false, 60);
		Actions action = new Actions(browser);
		
		// 1. Browse Amazon Web Site
		browser.get("https://www.amazon.in");
	    System.out.println("Page:1] Browse>   https://www.amazon.in");

		WebElement searchBox = browser.findElement(
				By.xpath("//input[@id='twotabsearchtextbox']"));
		
		String productName = "iPhone 12";
		// String productName = "Pixel 5";

	    System.out.println("Page:1] Search>   {"+productName+"}");
	    // 2.  Search for the Product
		searchBox.sendKeys(productName);

	    searchBox.submit();

		System.out.println("Page:1] Grab Link>   {"+productName+"}");
		// 3. Go to Product Page
		WebElement product = browser.findElement(
		 			By.xpath("//*[@id=\"search\"]/div[1]/div[1]/div/span[1]/div[1]/div[3]/div/div/div/div/div/div/div/div[2]/div/div/div[1]/h2/a"));
	    
	    System.out.println("Page:1] Result>   "+product.getText());
	    // Wait for 3 Seconds before going to Product Page
	    Thread.sleep(3000);
	    product.click();

	    
	    String window1 = browser.getWindowHandle();
	    String window2 = window1;
	    Set<String> windows = browser.getWindowHandles();
	    int x=1;
	    for(String s : windows) {
	    	System.out.println(x+"> "+s);
	    	x++;
	    	window2 = s;
	    }
	    browser.switchTo().window(window2);
	    
	    WebElement productDetails = browser.findElement(
	    		By.xpath("//*[@id=\"title\"]"));
	    
	    System.out.println("Page:2] Details>  "+productDetails.getText());
	    
	    // 4. Add Product to Cart
	    WebElement addToCart = browser.findElement(
	    		By.xpath("//*[@id=\"add-to-cart-button\"]"));
	    System.out.println("Page:2] Add2Cart> "+productDetails.getText());
	    addToCart.click();
	  
	    System.out.println("Done....... !!!");
	    
	    Thread.sleep(60000);
	    browser.quit();
	    System.out.println("Test completed.... !!! ");

	}

}
