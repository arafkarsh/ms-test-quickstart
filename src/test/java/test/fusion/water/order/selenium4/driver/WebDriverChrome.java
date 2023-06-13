package test.fusion.water.order.selenium4.driver;

import java.util.concurrent.TimeUnit;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import io.github.bonigarcia.wdm.WebDriverManager;
/**
 * 
 * @author arafkarsh
 *
 */
public class WebDriverChrome {

	  /**
	   * Returns Chrome Web Driver
	   * @return
	   */
	  public static void initialize() {
		  WebDriverManager.chromedriver().setup();
	  }
	  
	  /**
	   * Return the Chrome Driver
	   * @return
	   */
	  public static WebDriver getChromeDriver(boolean headless, int timeout) {
		  // Optional. If not specified, WebDriver searches the PATH for chromedriver.
		  // System.setProperty("webdriver.chrome.driver", "/path/to/chromedriver");

		  ChromeOptions options = new ChromeOptions();
		  options.setHeadless(headless);
		  options.setPageLoadStrategy(PageLoadStrategy.NORMAL);
		  // This will fix the issue with Chrome v110 and above
		  options.addArguments("--remote-allow-origins=*");

		  // Create Web Driver for Chrome
		  WebDriver driver = new ChromeDriver(options);
		  // driver.manage().window().maximize();
		  driver.manage().timeouts().implicitlyWait(timeout, TimeUnit.SECONDS);
		  return driver; 
	  }
	  
}
