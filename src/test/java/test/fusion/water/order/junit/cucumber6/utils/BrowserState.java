package test.fusion.water.order.junit.cucumber6.utils;

import org.openqa.selenium.WebDriver;

/**
 * 
 * @author arafkarsh
 *
 */
public class BrowserState {
	
	public WebDriver chromeDriver;

	/**
	 * Create Browser State
	 */
	public BrowserState() {
	}
	
	/**
	 * Returns Chrome Driver
	 * @return
	 */
	public WebDriver getWebDriver() {
		return chromeDriver;
	}
	
	/**
	 * Returns Chrome Driver
	 * @param _webDriver
	 */
	public void setWebDriver(WebDriver _webDriver) {
		chromeDriver = _webDriver;
	}
}
