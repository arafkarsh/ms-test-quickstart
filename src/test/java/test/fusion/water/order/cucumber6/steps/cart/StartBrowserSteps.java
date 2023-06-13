package test.fusion.water.order.cucumber6.steps.cart;

import static org.junit.Assert.assertEquals;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.containsString;

import io.cucumber.java8.En;
import test.fusion.water.order.cucumber6.utils.BrowserState;
import test.fusion.water.order.selenium4.driver.WebDriverChrome;

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
