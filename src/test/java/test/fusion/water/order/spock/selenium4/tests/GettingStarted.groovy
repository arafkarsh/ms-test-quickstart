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

import io.fusion.water.order.domainLayer.models.OrderEntity
import spock.lang.*
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions

/**
 * @author: Araf Karsh Hamid
 * @version:
 * @date:
 */
class GettingStarted extends Specification {

    def setupSpec() {
        println "== Order tests Suite Execution Started..."
    }

    def setup() {
        println "== Execute before each Test Case"
    }

    def "1. Test Google Search"() {
        given: "Chrome options and driver are set"
            ChromeOptions options = new ChromeOptions()
            options.addArguments("--remote-allow-origins=*")
            WebDriver driver = new ChromeDriver(options)

        when: "We navigate to google"
            driver.get("http://www.google.com/")
            sleep(5000) // Let the user actually see something!

        then: "We do a search"
            WebElement searchBox = driver.findElement(By.name("q"))
            searchBox.sendKeys("Araf Karsh")
            searchBox.submit()
            sleep(31000) // Let the user actually see something!

        and: "We close the browser"
            driver.quit()
    }

    def cleanup() {
        println "Should Execute After Each Test"
    }

    def cleanupSpec() {
        println "== Order tests Suite Execution Completed..."
    }
}
