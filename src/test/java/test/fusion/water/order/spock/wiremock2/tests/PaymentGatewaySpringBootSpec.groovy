package test.fusion.water.order.spock.wiremock2.tests


// Spock
import spock.lang.*
import org.spockframework.spring.EnableSharedInjection

// WireMock
import com.github.tomakehurst.wiremock.WireMockServer
import com.github.tomakehurst.wiremock.client.WireMock
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import static com.github.tomakehurst.wiremock.client.WireMock.*
// Custom
import io.fusion.water.order.OrderApplication;
import io.fusion.water.order.domain.models.EchoData;
import io.fusion.water.order.domain.models.EchoResponseData;
import io.fusion.water.order.domain.models.PaymentDetails;
import io.fusion.water.order.domain.models.PaymentStatus;
import io.fusion.water.order.domain.services.PaymentService;
import io.fusion.water.order.server.ServiceConfiguration;
import io.fusion.water.order.utils.Utils;
import test.fusion.water.order.utils.SampleData;

/**
 * Spring Boot 2.7.2 / Spock 2 / WireMock 2 Integration
 *
 * The following Application Class is defined as the package structure
 * for the Application and package structure for test packages are
 * completely different
 *
 * @SpringBootTest(classes={io.fusion.water.order.OrderService.class})
 *
 * @author arafkarsh
 *
 */
@EnableSharedInjection
@SpringBootTest(classes = OrderApplication.class)
class PaymentGatewaySpringBootSpec extends Specification {

    @Autowired
    PaymentService paymentService

    @Shared
    @Autowired
    ServiceConfiguration serviceConfig

    @Shared
    WireMockServer wireMockServer
    static int counter = 1

    def setupSpec() {
        println("== Payment Service SpringBoot/WireMock HTTP Tests Started...")
        println("Host:${serviceConfig.getRemoteHost()}:${serviceConfig.getRemotePort()}")
        wireMockServer = new WireMockServer(serviceConfig.getRemotePort())
        wireMockServer.start()
        WireMock.configureFor(serviceConfig.getRemoteHost(), serviceConfig.getRemotePort())
        println("${counter}] WireMock Server Started.. on " + wireMockServer.baseUrl())
    }

    def setup() {
        println("@BeforeEach: Payment Service is autowired using SpringBoot!")
    }

    def "1. Spring Boot Testing Autowired Payment Service"() {
        given:
            String param = "World"
            String expectedResult = "Hello ${param}"
            String result

        when:
            result = paymentService.echo("World")
            println("@Test: Spring Boot test ${result}")

        then:
            result == expectedResult
    }

    def "2. Payment Service HTTP: Remote Echo"() {
        given:
            EchoData param = new EchoData("John")
            EchoResponseData expectedResult = new EchoResponseData("John")

            stubFor(post("/remoteEcho")
                    .withRequestBody(equalToJson(Utils.toJsonString(param)))
                    .willReturn(okJson(Utils.toJsonString(expectedResult))))

        when:
            EchoResponseData response = paymentService.remoteEcho(param)

        then:
            response != null
            response.getWordData() == expectedResult.getWordData()

        and:
            verify(postRequestedFor(urlPathEqualTo("/remoteEcho"))
                    .withRequestBody(equalToJson(Utils.toJsonString(param)))
                    .withHeader("Content-Type", equalTo("application/json")))

    }

    def "3. Payment Service HTTP: Accepted"() {
        given:
            PaymentDetails pd = SampleData.getPaymentDetails()
            PaymentStatus ps = SampleData.getPaymentStatusAccepted(pd.getTransactionId(), pd.getTransactionDate())

            stubFor(post("/payment")
                    .withRequestBody(equalToJson(Utils.toJsonString(pd)))
                    .willReturn(okJson(Utils.toJsonString(ps))))

        when:
            PaymentStatus payStatus = paymentService.processPaymentsExternal(pd)

        then:
            payStatus != null
            payStatus.getPaymentStatus() == "Accepted"

        and:
            verify(postRequestedFor(urlPathEqualTo("/payment"))
                    .withRequestBody(equalToJson(Utils.toJsonString(pd)))
                    .withHeader("Content-Type", equalTo("application/json")))
    }

    def "4. Payment Service HTTP: Declined"() {
        given:
            PaymentDetails pd = SampleData.getPaymentDetails()
            PaymentStatus ps = SampleData.getPaymentStatusDeclined(pd.getTransactionId(), pd.getTransactionDate())

            stubFor(post("/payment")
                    .withRequestBody(equalToJson(Utils.toJsonString(pd)))
                    .willReturn(okJson(Utils.toJsonString(ps))))

        when:
            PaymentStatus payStatus = paymentService.processPaymentsExternal(pd)

        then:
            payStatus != null
            payStatus.getPaymentStatus() == "Declined"

        and:
            verify(postRequestedFor(urlPathEqualTo("/payment"))
                    .withRequestBody(equalToJson(Utils.toJsonString(pd)))
                    .withHeader("Content-Type", equalTo("application/json")))
    }

    def cleanupSpec() {
        wireMockServer.stop()
        println("== Payment Service SpringBoot/WireMock HTTP Tests Completed...")
    }
}
