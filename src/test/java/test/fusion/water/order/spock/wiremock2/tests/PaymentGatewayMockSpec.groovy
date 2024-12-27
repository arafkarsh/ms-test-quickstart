package test.fusion.water.order.spock.wiremock2.tests

// Spock
import spock.lang.*
// WireMock
import com.github.tomakehurst.wiremock.WireMockServer
import com.github.tomakehurst.wiremock.client.WireMock
import static com.github.tomakehurst.wiremock.client.WireMock.*
// Custom
import io.fusion.water.order.adapters.service.PaymentServiceImpl;
import io.fusion.water.order.domain.models.EchoData;
import io.fusion.water.order.domain.models.EchoResponseData;
import io.fusion.water.order.domain.models.PaymentDetails;
import io.fusion.water.order.domain.models.PaymentStatus;
import io.fusion.water.order.utils.Utils;
import test.fusion.water.order.utils.SampleData;
import io.fusion.water.order.adapters.external.ExternalGateWay;


/**
 * WireMock with Spock 2
 *
 * @author arafkarsh
 *
 */
class PaymentGatewayMockSpec extends Specification {

    @Shared
    WireMockServer wireMockServer
    @Shared
    String host = "127.0.0.1"
    @Shared
    int port = 8081
    @Shared
    PaymentServiceImpl paymentService
    static int counter = 1

    def setupSpec() {
        println("== Payment Service WireMock HTTP Tests Started...")
        wireMockServer = new WireMockServer(port)
        wireMockServer.start()
        WireMock.configureFor(host, port)
        println("${counter}] WireMock Server Started.. on " + wireMockServer.baseUrl())
    }

    def setup() {
        ExternalGateWay gw = new ExternalGateWay(host, port)
        paymentService = new PaymentServiceImpl(gw)
    }

    def "1. Payment Service HTTP: Remote Echo"() {
        given: "The Remote Echo Data available along with expected Result"
            EchoData param = new EchoData("John")
            EchoResponseData expectedResult = new EchoResponseData("John")

            stubFor(post("/remoteEcho")
                    .withRequestBody(equalToJson(Utils.toJsonString(param)))
                    .willReturn(okJson(Utils.toJsonString(expectedResult))))

        when: "The remote call is made"
            EchoResponseData response = paymentService.remoteEcho(param)

        then: "The response should not be null and Response must be same as expected Result"
            response != null
            response.getWordData() == expectedResult.getWordData()

        and:
            verify(postRequestedFor(urlPathEqualTo("/remoteEcho"))
                    .withRequestBody(equalToJson(Utils.toJsonString(param)))
                    .withHeader("Content-Type", equalTo("application/json")))

    }

    def "2. Payment Service HTTP: Accepted"() {
        given: "The payment details and Payment Status available"
            PaymentDetails pd = SampleData.getPaymentDetails()
            PaymentStatus ps = SampleData.getPaymentStatusAccepted(pd.getTransactionId(), pd.getTransactionDate())

            stubFor(post("/payment")
                    .withRequestBody(equalToJson(Utils.toJsonString(pd)))
                    .willReturn(okJson(Utils.toJsonString(ps))))

        when: "Payment Process is invoked"
            PaymentStatus payStatus = paymentService.processPaymentsExternal(pd)

        then: "Payment status must NOT be NULL and payment status equals Accepted"
            payStatus != null
            payStatus.getPayStatus() == "Accepted"

        and:
            verify(postRequestedFor(urlPathEqualTo("/payment"))
                    .withRequestBody(equalToJson(Utils.toJsonString(pd)))
                    .withHeader("Content-Type", equalTo("application/json")))

    }

    def "3. Payment Service HTTP: Declined"() {
        given: "The payment details and Payment Status available"
            PaymentDetails pd = SampleData.getPaymentDetails()
            PaymentStatus ps = SampleData.getPaymentStatusDeclined(pd.getTransactionId(), pd.getTransactionDate())

            stubFor(post("/payment")
                    .withRequestBody(equalToJson(Utils.toJsonString(pd)))
                    .willReturn(okJson(Utils.toJsonString(ps))))

        when: "Payment Process is invoked"
            PaymentStatus payStatus = paymentService.processPaymentsExternal(pd)

        then: "Payment status must NOT be NULL and payment status equals Declined"
            payStatus != null
            payStatus.getPayStatus() == "Declined"

        and:
            verify(postRequestedFor(urlPathEqualTo("/payment"))
                    .withRequestBody(equalToJson(Utils.toJsonString(pd)))
                    .withHeader("Content-Type", equalTo("application/json")))

    }

    def cleanupSpec() {
        wireMockServer.stop()
        println("== Payment Service WireMock HTTP Tests Completed...")
    }
}