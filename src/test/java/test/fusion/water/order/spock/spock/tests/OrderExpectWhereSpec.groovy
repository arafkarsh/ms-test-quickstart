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
package test.fusion.water.order.spock.spock.tests

import spock.lang.Ignore
import spock.lang.IgnoreIf
import spock.lang.Requires
import spock.lang.Specification
import spock.lang.Stepwise
import spock.lang.Unroll

import java.time.Month

import io.fusion.water.order.domainLayer.models.OrderEntity
import io.fusion.water.order.domainLayer.models.Customer
import io.fusion.water.order.utils.Utils;

/**
 * Spock - Order Parametrized Test Suite
 *
 * Using EXPECT / WHERE Clauses
 *
 * @author arafkarsh
 *
 */
@Stepwise
class OrderExpectWhereSpec extends Specification {

    def order
    def counter = 1

    def setupSpec() {
        println("== Order Parametrized Suite Execution Started...")
    }

    def setup() {
        println("${counter}. Create an Empty Order..")
        order = new OrderEntity()
    }

    /**
     * In Example 1.1 the Spock testing framework is treating each phone number as a separate iteration of the test
     * due to the where: block. This results in the test being run once for each phone number. In your test report,
     * you will see the test being reported as executed three times, once for each phone number. This can be beneficial
     * for isolating failures to a specific iteration of the test.
     */
    def "1. Value Source : String Array - 1"() {
        expect:
            def order = new OrderEntity.Builder()
                    .addCustomer(new Customer("UUID", "John", "Doe", phoneNumber))
                    .build()
            order.isCustomerAvailable() == true
            order.getCustomer().getPhoneList().size() == 1
        where:
            phoneNumber << ["0123456777", "0123456888", "0123456999"]
    }

    /**
     * In Example 1.2, you are using Groovy's each loop to iterate over the phone numbers within a single test.
     * This means that the test is executed only once, but it performs the check for each phone number within
     * that single execution. If a failure occurs, it will be harder to identify which iteration (i.e., which
     * phone number) caused the failure, since they are all part of the same test execution.
     *
     * NOT RECOMMENDED
     */
    def "2. Value Source - String Array - 2"() {
        expect:
        ["0123456777", "0123456888", "0123456999"]
                .each { phoneNumber ->
                    order = new OrderEntity.Builder()
                            .addCustomer(
                                    new Customer("UUID", "John", "Doe", phoneNumber))
                            .build()
                    order.isCustomerAvailable() == true
                    order.getCustomer().getPhoneList().size() == 1
                }
    }

    def "3. Value Source - Number Array"() {
        expect:
            Utils.Numbers.isOdd(number)
        where:
            number << [1, 3, 5, -3, 15, Integer.MAX_VALUE]
    }

    def "4. Value Source - Null Source"() {
        expect:
            Utils.Strings.isBlank(input)
        where:
            input << [null]
    }

    def "5. Value Source - Empty Source"() {
        expect:
            Utils.Strings.isBlank(input)
        where:
            input << [""]
    }

    def "6. Value Source - Null & Empty Source"() {
        expect:
            Utils.Strings.isBlank(input)
        where:
            input << [null, ""]
    }

    def "7. Value Source - Null & Empty Source & White spaces"() {
        expect:
            Utils.Strings.isBlank(input)
        where:
            input << [null, "", "  ", "\t", "\n"]
    }

    def "8. Enum Source - 12 Months"() {
        expect:
            monthNumber > 0 && monthNumber < 13
        where:
            month << Month.values()
            monthNumber = month.getValue()
    }

    def "9. Enum Source - 4 Months"() {
        expect:
            month.length(isALeapYear) == 30
        where:
            month << [Month.APRIL, Month.JUNE, Month.SEPTEMBER, Month.NOVEMBER]
            isALeapYear = false
    }

    def "10. Method Source - Phone No. should match the Format"() {
        expect:
            def order = new OrderEntity.Builder()
                    .addCustomer(new Customer("UUID", "John", "Doe", phoneNumber))
                    .build()
            order.isCustomerAvailable() == true
            order.getCustomer().getPhoneList().size() == 1
        where:
            phoneNumber << phoneNumberList()
    }

    def "11. Should Test Order Creation Repeatedly - 3 Times"() {
        expect:
        3.times {
            order = new OrderEntity.Builder()
                    .addCustomer(
                            new Customer("UUID", "John", "Doe", "0123456789"))
                    .build()
            order.isCustomerAvailable() == true
        }
    }

    @Requires({ System.getProperty('os.name').contains('Mac') })
    def "12. Runs only on Mac"() {
        expect:
        2 + 2 == 4
    }

    @IgnoreIf({ System.getProperty('os.name').contains('Windows') })
    def "13. Ignored on Windows"() {
        expect:
        2 + 2 == 4
    }

    @Ignore
    def "14. Ignored test method"() {
        expect:
        2 + 2 == 4
    }

    @Unroll
    def "15. Check #x + #y == #expectedSum"() {
        expect:
        x + y == expectedSum

        where:
        x | y || expectedSum
        1 | 2 || 3
        3 | 4 || 7
        5 | 5 || 10
    }

    private static List<String> phoneNumberList() {
        ["0123456789", "0123456798", "0123456897"]
    }

    def cleanup() {
        println("${counter}. Should Execute After Each Test")
        counter++
    }

    def cleanupSpec() {
        println("== Order Parametrized Suite Execution Completed...")
    }

}