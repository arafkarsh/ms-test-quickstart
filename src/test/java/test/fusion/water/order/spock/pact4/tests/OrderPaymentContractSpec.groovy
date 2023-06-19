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
package test.fusion.water.order.spock.pact4.tests

// Spock
import spock.lang.*
// SpringBoot
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext
// Pact
import au.com.dius.pact.consumer.MockServer
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
// import au.com.dius.pact.consumer.PactTestFor
import au.com.dius.pact.consumer.junit.PactVerification
import au.com.dius.pact.core.model.RequestResponsePact
import au.com.dius.pact.consumer.junit5.PactTestFor
import au.com.dius.pact.core.model.annotations.Pact
import au.com.dius.pact.core.model.annotations.PactFolder

// Custom
import io.fusion.water.order.domainLayer.models.EchoResponseData;
import io.fusion.water.order.domainLayer.models.PaymentDetails;
import io.fusion.water.order.domainLayer.models.PaymentStatus;
import io.fusion.water.order.domainLayer.services.PaymentService;
import io.fusion.water.order.utils.Utils;
import test.fusion.water.order.utils.SampleData;
import io.fusion.water.order.OrderApplication;


@PactFolder('pacts')
@DirtiesContext
@SpringBootTest(classes = OrderApplication.class, webEnvironment = SpringBootTest.WebEnvironment.NONE)
class OrderPaymentContractSpec extends Specification {

    @Autowired
    PaymentService paymentService

    private PaymentDetails pd;
    private PaymentStatus ps;

    def setupSpec() {
        println("== Payment Service SpringBoot/Pact HTTP Tests Started...")
    }

    def setup() {
        println("@BeforeEach: Payment Service is autowired using SpringBoot!")
        pd = SampleData.getPaymentDetails()
        ps = SampleData.getPaymentStatusAccepted(pd.getTransactionId(), pd.getTransactionDate())
    }

    @Pact(provider="PaymentService", consumer="OrderApplication")
    def remoteEcho(PactDslWithProvider builder) {
        println("creating Pact for /remoteEchoGet/")

        def param = new String("Jane")
        def expectedResult = new EchoResponseData("Jane")

        RequestResponsePact rrp = builder
                .given("Given the word service returns a greeting.", "word", param)
                .uponReceiving("word Jane, service returns a greeting.")
                .path("/remoteEcho/Jane")
                .willRespondWith()
                .status(200)
                .body(Utils.toJsonString(expectedResult))
                .toPact()

        println("PACT=$rrp")
        rrp.getInteractions().each { println it }
        return rrp
    }

    @Unroll
    @PactTestFor(providerName = "PaymentService", port="8080")
    @PactVerification(fragment = "remoteEcho")
    def "remoteEchoGet"(MockServer mockServer) {
        println("PACT    |> MockServer|" + mockServer.getUrl())
        def param = new String("Jane")
        def expectedResult = new EchoResponseData("Jane")
        def result = null
        try {
            result = paymentService.remoteEcho(param)
        } catch(Exception e) {
            println("ERROR: "+e.getMessage())
        }
        assert result != null
        assert expectedResult.getWordData() == result.getWordData()
    }

    def cleanupSpec() {
        println("== Payment Service SpringBoot/Pact HTTP Tests Completed...")
    }
}
