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

import spock.lang.Specification
import spock.lang.Stepwise
import java.time.Month

import io.fusion.water.order.domainLayer.models.OrderEntity
import io.fusion.water.order.domainLayer.models.Customer
import io.fusion.water.order.utils.Utils;

/**
 * Spock - Order Parametrized Test Suite
 *
 * @author arafkarsh
 *
 */
@Stepwise
class OrderParametrizedSpec extends Specification {

    def order
    def counter = 1

    def setupSpec() {
        println("== Order Parametrized Suite Execution Started...")
    }

    def setup() {
        println("${counter}. Create an Empty Order..")
        order = new OrderEntity()
    }

    def "1.1 Value Source : String Array"() {
        expect:
        def order = new OrderEntity.Builder()
                .addCustomer(new Customer("UUID", "John", "Doe", phoneNumber))
                .build()
        order.isCustomerAvailable() == true
        order.getCustomer().getPhoneList().size() == 1

        where:
        phoneNumber << ["0123456777", "0123456888", "0123456999"]
    }

    def "1.2 Value Source - Number Array"() {
        expect:
        Utils.Numbers.isOdd(number)

        where:
        number << [1, 3, 5, -3, 15, Integer.MAX_VALUE]
    }

    def "1.3 Value Source - Null Source"() {
        expect:
        Utils.Strings.isBlank(input)

        where:
        input << [null]
    }

    def "1.4 Value Source - Empty Source"() {
        expect:
        Utils.Strings.isBlank(input)

        where:
        input << [""]
    }

    def "1.5 Value Source - Null & Empty Source"() {
        expect:
        Utils.Strings.isBlank(input)

        where:
        input << [null, ""]
    }

    def "1.6 Value Source - Null & Empty Source & White spaces"() {
        expect:
        Utils.Strings.isBlank(input)

        where:
        input << [null, "", "  ", "\t", "\n"]
    }

    def "2.1 Enum Source - 12 Months"() {
        expect:
        monthNumber > 0 && monthNumber < 13

        where:
        month << Month.values()
        monthNumber = month.getValue()
    }

    def "2.2 Enum Source - 4 Months"() {
        expect:
        month.length(isALeapYear) == 30

        where:
        month << [Month.APRIL, Month.JUNE, Month.SEPTEMBER, Month.NOVEMBER]
        isALeapYear = false
    }

    def "2.3 Method Source Case - Phone No. should match the Format"() {
        expect:
        def order = new OrderEntity.Builder()
                .addCustomer(new Customer("UUID", "John", "Doe", phoneNumber))
                .build()
        order.isCustomerAvailable() == true
        order.getCustomer().getPhoneList().size() == 1

        where:
        phoneNumber << phoneNumberList()
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