package test.fusion.water.order.spock.spock

import spock.lang.Specification
import spock.lang.Unroll

import io.fusion.water.order.domainLayer.models.OrderEntity
import io.fusion.water.order.domainLayer.models.Customer



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

    @Unroll("Should Create Customer in order")
    def "shouldCreateCustomer"() {
        given: "A new customer"
        Customer c = new Customer("UUID", "John", "Doe", "0123456789")

        when: "Adding customer to the order"
        order = new OrderEntity.Builder()
                .addCustomer(c)
                .build()

        then: "Customer should be available in order"
        order.isCustomerAvailable()
    }

    @Unroll("Should Not Create Customer When First Name is Null")
    def "shouldThrowRuntimeExceptionWhenFirstNameIsNull"() {
        when: "Adding customer with null first name"
        order = new OrderEntity.Builder()
                .addCustomer(new Customer("UUID", null, "Doe", "0123456789"))
                .build()

        then: "An exception is thrown"
        thrown(RuntimeException)
    }

    // Similarly, other test cases can be written...

    def cleanup() {
        println("${counter}. Should Execute After Each Test")
        counter++
    }

    def cleanupSpec() {
        println("== Order tests Suite Execution Completed...")
    }
}

