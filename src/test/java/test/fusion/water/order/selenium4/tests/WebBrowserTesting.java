package test.fusion.water.order.selenium4.tests;

import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import test.fusion.water.order.junit5.annotations.tests.Critical;
import test.fusion.water.order.junit5.annotations.tests.Functional;
import test.fusion.water.order.junit5.annotations.tools.Selenium4;
import test.fusion.water.order.junit5.annotations.tools.WireMock2;
import test.fusion.water.order.selenium4.driver.WebDriverChrome;

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
@Selenium4()
@Critical()
@Functional()
public class WebBrowserTesting {

	public static void main(String[] args) throws InterruptedException {

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
	      
	    // WebDriverWait w = new WebDriverWait(browser, 5);
	    // w.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//ul")));
	    searchBox.submit();

		System.out.println("Page:1] Grab Link>   {"+productName+"}");
		// 3. Go to Product Page
		WebElement product = browser.findElement(
		 			By.xpath("//*[@id=\"search\"]/div[1]/div[1]/div/span[1]/div[1]/div[3]/div/div/div/div/div/div/div/div[2]/div/div/div[1]/h2/a"));
		//
	    // WebElement product = browser.findElement(
	    // 			By.cssSelector("#search > div.s-desktop-width-max.s-opposite-dir > div > div.s-matching-dir.sg-col-16-of-20.sg-col.sg-col-8-of-12.sg-col-12-of-16 > div > span:nth-child(4) > div.s-main-slot.s-result-list.s-search-results.sg-row > div:nth-child(2) > div > span > div > div > div > div > div:nth-child(2) > div.sg-col.sg-col-4-of-12.sg-col-8-of-16.sg-col-12-of-20 > div > div > div:nth-child(1) > h2 > a")
	    // 			);
	    
	    System.out.println("Page:1] Result>   "+product.getText());
	    // Wait for 3 Seconds before going to Product Page
	    Thread.sleep(3000);
	    product.click();
	    
	    // action.moveToElement(product);
	    // action.click();
	    // action.perform();
	    
	    String window1 = browser.getWindowHandle();
	    String window2 = window1;
	    // System.out.println("0> "+window1);
	    Set<String> windows = browser.getWindowHandles();
	    int x=1;
	    for(String s : windows) {
	    	// System.out.println(x+"> "+s);
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
