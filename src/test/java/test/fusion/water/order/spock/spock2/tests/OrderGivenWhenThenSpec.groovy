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
package test.fusion.water.order.spock.spock2.tests

import spock.lang.Specification

import io.fusion.water.order.domain.models.OrderEntity
import io.fusion.water.order.domain.models.Customer
import spock.lang.Stepwise

/**
 * Spock - Order Specs Suite
 *
 * USING GIVEN / WHEN / THEN
 *
 * or Setup / Action / Result
 *
 * @author arafkarsh
 *
 */
@Stepwise
class OrderGivenWhenThenSpec extends Specification {

    OrderEntity order

    def setupSpec() {
        println "== Order tests Suite Execution Started..."
    }

    def setup() {
        println "Create an Empty Order.."
        order = new OrderEntity()
    }

    def "1. Should Create Customer if Customer is Available"() {
        given: "A New Customer"
            Customer c = new Customer("UUID", "John", "Doe", "0123456789")

        when: "an order is created with the customer"
            order = new OrderEntity.Builder().addCustomer(c).build()

        then: "the order has a customer"
            order.isCustomerAvailable() == true
    }

    def "2. Should Throw RuntimeException When FirstName Is Null"() {
        given: "A Customer with Null First Name"
            Customer c = new Customer("UUID", null, "Doe", "0123456789")

        when: "Adding a Customer with Null First Name"
            order = new OrderEntity.Builder()
                    .addCustomer(c)
                    .build()
        then: "An Exception is thrown"
            thrown RuntimeException
    }

    def "3. Should Throw RuntimeException When LastName Is Null"() {
        given: "A Customer with Null Last Name"
            Customer c = new Customer("UUID", "John", null, "0123456789")

        when: "Adding a Customer with Null Last Name"
            order = new OrderEntity.Builder().addCustomer(c).build()

        then: "An Exception is thrown"
            thrown RuntimeException
    }

    def "4. Should Throw RuntimeException When PhoneNumber Is Null"() {
        given: "A Customer with Null Phone Number"
            Customer c = new Customer("UUID", "John", "Doe", null)

        when: "Adding a Customer with Null Phone Number"
            order = new OrderEntity.Builder().addCustomer(c).build()

        then: "An Exception is thrown"
            thrown RuntimeException
    }

    /**
     * Data Driven Test
     * @param a
     * @param b
     * @param max
     * @return
     */
    def "5. Maximum of two numbers"(int a, int b, int max) {
        given: "two numbers"
        // defined by the where block

        when: "Math.max is called"
            def result = Math.max(a, b)

        then: "the result is the maximum of the two numbers"
            result == max

        where:
        a | b || max
        1 | 3 || 3
        7 | 4 || 7
        0 | 0 || 0
    }

    /**
     * Conditional test execution based on the iteration
     * Spock allows you to control the execution of your test depending on the iteration of data-driven testing
     * @return
     */
    def "6. only runs on certain iterations <=8"() {
        given: "A Set of Numbers"
        // defined by the where block
        when:
            println x

        then:
            1 == 1

        where: "only run when x <= 3"
        x << [1, 3, 4, 7, 9, 10, 12, 13, 15, 19].findAll { it <= 8 }
    }

    def cleanup() {
        println "Should Execute After Each Test"
    }

    def cleanupSpec() {
        println "== Order tests Suite Execution Completed..."
    }
}