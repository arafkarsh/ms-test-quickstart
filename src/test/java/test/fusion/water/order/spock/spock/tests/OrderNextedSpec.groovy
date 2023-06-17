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

import io.fusion.water.order.domainLayer.models.OrderEntity
import io.fusion.water.order.domainLayer.models.Customer

/**
 * Spock - Order Nested Suite
 *
 * @author arafkarsh
 *
 */
class OrderNextedSpec extends Specification {

    OrderEntity order

    def setupSpec() {
        println "== Order tests Suite Execution Started..."
    }

    def setup() {
        println "Create an Empty Order.."
        order = new OrderEntity()
    }

    def "1. Should Create Customer"() {
        given: "a customer"
            Customer c = new Customer("UUID", "John", "Doe", "0123456789")
        when: "an order is created with the customer"
            order = new OrderEntity.Builder().addCustomer(c).build()
        then: "the order has a customer"
            order.isCustomerAvailable() == true
    }

    def "2. Should Throw RuntimeException When FirstName Is Null"() {
        when:
            order = new OrderEntity.Builder()
                .addCustomer(new Customer("UUID", null, "Doe", "0123456789"))
                .build()

        then:
            thrown RuntimeException
    }

    def "3. Should Throw RuntimeException When LastName Is Null"() {
        when:
            order = new OrderEntity.Builder()
                .addCustomer(new Customer("UUID", "John", null, "0123456789"))
                .build()

        then:
            thrown RuntimeException
    }

    def "4. Should Throw RuntimeException When PhoneNumber Is Null"() {
        when:
            order = new OrderEntity.Builder()
                .addCustomer(new Customer("UUID", "John", "Doe", null))
                .build()

        then:
            thrown RuntimeException
    }

    def "5. Should Test Order Creation Repeatedly - 3 Times"() {
        expect:
            3.times {
                order = new OrderEntity.Builder()
                    .addCustomer(
                            new Customer("UUID", "John", "Doe", "0123456789"))
                    .build()
                order.isCustomerAvailable() == true
            }
    }

    def "6. Should Test PhoneNumber Format Using Value Source"() {
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

    def cleanup() {
        println "Should Execute After Each Test"
    }

    def cleanupSpec() {
        println "== Order tests Suite Execution Completed..."
    }
}