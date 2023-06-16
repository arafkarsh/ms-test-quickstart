package test.fusion.water.order.junit.selenium4.tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.*;
// import org.junit.Test;
import org.junit.jupiter.api.Test;

import java.io.File;

/**
 * @author: Araf Karsh Hamid
 * @version:
 * @date:
 */
public class GettingStarted {

    @Test
    public void testGoogleSearch() throws InterruptedException {

        // Optional. If not specified, WebDriver searches the PATH for chromedriver.
        // System.setProperty("webdriver.chrome.driver", "/path/to/chromedriver");

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        WebDriver driver = new ChromeDriver(options);
        driver.get("http://www.google.com/");

        Thread.sleep(5000);  // Let the user actually see something!

        WebElement searchBox = driver.findElement(By.name("q"));
        searchBox.sendKeys("Araf Karsh");
        searchBox.submit();

        Thread.sleep(31000);  // Let the user actually see something!
        driver.quit();

    }
}
