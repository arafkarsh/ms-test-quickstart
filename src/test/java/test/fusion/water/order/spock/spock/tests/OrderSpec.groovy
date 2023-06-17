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
 * Spock - Order Test Suite
 *
 * @author arafkarsh
 *
 */
class OrderSpec extends Specification {

    private OrderEntity order
    private int counter = 1

    def setupSpec() {
        println("== Order tests Suite Execution Started...")
    }

    def setup() {
        println("${counter}. Create an Empty Order..")
        order = new OrderEntity()
    }

    def "1. Should Create Customer"() {
        given: "A new customer"
        Customer c = new Customer("UUID", "John", "Doe", "0123456789")

        when: "Adding customer to the order"
        order = new OrderEntity.Builder()
                .addCustomer(c)
                .build()

        then: "Customer should be available in order"
        order.isCustomerAvailable()
    }

    def "2. Should Fail To Create Customer"() {
        given: "A new customer"
        Customer c = new Customer("UUID", "John", "Doe", "0123456789")

        when: "Adding customer to the order"
        order = new OrderEntity.Builder()
                .addCustomer(c)
                .build()

        then: "Customer should be available in order"
        order.isCustomerAvailable()
    }

    def "3. Should Not Create Customer When First Name is Null"() {
        when: "Adding customer with null first name"
        order = new OrderEntity.Builder()
                .addCustomer(
                        new Customer("UUID", null, "Doe", "0123456789"))
                .build()

        then: "An exception is thrown"
        thrown(RuntimeException)
    }

    def cleanup() {
        println("${counter}. Should Execute After Each Test")
        counter++
    }

    def cleanupSpec() {
        println("== Order tests Suite Execution Completed...")
    }
}

