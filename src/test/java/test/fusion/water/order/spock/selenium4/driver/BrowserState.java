package test.fusion.water.order.spock.selenium4.driver;

import org.openqa.selenium.WebDriver;

/**
 * 
 * @author arafkarsh
 *
 */
public class BrowserState {
	
	public WebDriver chromeDriver;

	/**
	 * Returns Chrome Driver
	 * @return
	 */
	public WebDriver getWebDriver() {
		return chromeDriver;
	}
	
	/**
	 * Returns Chrome Driver
	 * @param webDriver
	 */
	public void setWebDriver(WebDriver webDriver) {
		chromeDriver = webDriver;
	}
}
