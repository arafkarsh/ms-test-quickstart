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
package test.fusion.water.order.spock.selenium4.tests

// Spock
import spock.lang.*
// Selenium
import org.openqa.selenium.By
import org.openqa.selenium.WebElement
import org.openqa.selenium.interactions.Actions
import org.openqa.selenium.WebDriver
// Custom
import test.fusion.water.order.webdriver.WebDriverChrome;


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
class WebBrowserTesting extends Specification {

    def "Search Amazon for Product and Add to Cart"() {
        given: "Chrome WebDriver and Actions are initialized"
            WebDriverChrome.initialize()
            WebDriver browser = WebDriverChrome.getChromeDriver(false, 60)
            Actions action = new Actions(browser)

        and: "The Amazon website is browsed"
            browser.get("https://www.amazon.in")
            println("Page:1] Browse>   https://www.amazon.in")

        when: "A product name is entered into the search box"
            WebElement searchBox = browser.findElement(By.xpath("//input[@id='twotabsearchtextbox']"))
            String productName = "iPhone 12"
            println("Page:1] Search>   {"+productName+"}")
            searchBox.sendKeys(productName)
            searchBox.submit()

        and: "The product is selected"
            println("Page:1] Grab Link>   {"+productName+"}")
            WebElement product = browser.findElement(By.xpath("//*[@id=\"search\"]/div[1]/div[1]/div/span[1]/div[1]/div[3]/div/div/div/div/div/div/div/div[2]/div/div/div[1]/h2/a"))
            println("Page:1] Result>   "+product.getText())
            sleep(3000)
            product.click()
            String window1 = browser.getWindowHandle()
            String window2 = window1
            Set<String> windows = browser.getWindowHandles()
            int x=1
            for(String s : windows) {
                x++
                window2 = s
            }
            browser.switchTo().window(window2)

        then: "The product is added to the cart"
            WebElement productDetails = browser.findElement(By.xpath("//*[@id=\"title\"]"))
            println("Page:2] Details>  "+productDetails.getText())
            WebElement addToCart = browser.findElement(By.xpath("//*[@id=\"add-to-cart-button\"]"))
            println("Page:2] Add2Cart> "+productDetails.getText())
            addToCart.click()
            println("Done....... !!!")

        and: "The browser is closed"
            sleep(60000)
            browser.quit()
            println("Test completed.... !!! ")
    }
}