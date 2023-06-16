package test.fusion.water.order.spock.spock.tests

import spock.lang.Specification
import spock.lang.Unroll

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

    def "shouldCreateCustomer"() {
        given: "a customer"
            Customer c = new Customer("UUID", "John", "Doe", "0123456789")
        when: "an order is created with the customer"
            order = new OrderEntity.Builder().addCustomer(c).build()
        then: "the order has a customer"
            order.isCustomerAvailable() == true
    }

    def "shouldThrowRuntimeExceptionWhenFirstNameIsNull"() {
        when:
            order = new OrderEntity.Builder()
                .addCustomer(new Customer("UUID", null, "Doe", "0123456789"))
                .build()

        then:
            thrown RuntimeException
    }

    def "shouldThrowRuntimeExceptionWhenLastNameIsNull"() {
        when:
        order = new OrderEntity.Builder()
                .addCustomer(new Customer("UUID", "John", null, "0123456789"))
                .build()

        then:
        thrown RuntimeException
    }

    def "shouldThrowRuntimeExceptionWhenPhoneNumberIsNull"() {
        when:
        order = new OrderEntity.Builder()
                .addCustomer(new Customer("UUID", "John", "Doe", null))
                .build()

        then:
        thrown RuntimeException
    }

    def "shouldTestOrderCreationRepeatedly"() {
        expect:
        3.times {
            order = new OrderEntity.Builder()
                    .addCustomer(new Customer("UUID", "John", "Doe", "0123456789"))
                    .build()
            order.isCustomerAvailable() == true
        }
    }

    def "shouldTestPhoneNumberFormatUsingValueSource"() {
        expect:
        ["0123456777", "0123456888", "0123456999"].each { phoneNumber ->
            order = new OrderEntity.Builder()
                    .addCustomer(new Customer("UUID", "John", "Doe", phoneNumber))
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