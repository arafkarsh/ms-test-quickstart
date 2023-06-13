package test.fusion.water.order.cucumber6.steps.cart;


import io.cucumber.java8.En;
import test.fusion.water.order.cucumber6.utils.BrowserState;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import static org.junit.Assert.assertEquals;

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
