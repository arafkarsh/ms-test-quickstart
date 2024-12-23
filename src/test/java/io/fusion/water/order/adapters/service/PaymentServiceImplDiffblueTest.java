package io.fusion.water.order.adapters.service;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import io.fusion.water.order.domain.models.EchoData;
import io.fusion.water.order.domain.models.EchoResponseData;
import io.fusion.water.order.domain.models.PaymentDetails;
import io.fusion.water.order.domain.models.PaymentStatus;
import io.fusion.water.order.domain.models.PaymentType;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {PaymentServiceImpl.class})
@ExtendWith(SpringExtension.class)
class PaymentServiceImplDiffblueTest {
    @Autowired
    private PaymentServiceImpl paymentServiceImpl;

    /**
     * Method under test: {@link PaymentServiceImpl#echo(String)}
     */
    @Test
    void testEcho() {
        //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

        // Arrange, Act and Assert
        assertEquals("Hello  word", (new PaymentServiceImpl()).echo(" word"));
    }

    /**
     * Method under test: {@link PaymentServiceImpl#remoteEcho(EchoData)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testRemoteEcho() {
        // TODO: Diffblue Cover was only able to create a partial test for this method:
        //   Reason: Missing Spring properties.
        //   Failed to create Spring context due to unresolvable @Value
        //   properties: private java.util.ArrayList io.fusion.water.order.server.ServiceConfiguration.appPropertyList
        //   Please check that at least one of the property files is provided
        //   and contains required variables:
        //   - application-test.properties (file missing)
        //   See https://diff.blue/R033 to resolve this issue.

        // Arrange
        EchoData word = new EchoData("Hello World!");

        // Act
        EchoResponseData actualRemoteEchoResult = this.paymentServiceImpl.remoteEcho(word);

        // Assert
        assertThat(actualRemoteEchoResult).isNotNull();
    }

    /**
     * Method under test: {@link PaymentServiceImpl#remoteEcho(String)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testRemoteEcho2() {
        // TODO: Diffblue Cover was only able to create a partial test for this method:
        //   Reason: Missing Spring properties.
        //   Failed to create Spring context due to unresolvable @Value
        //   properties: private java.util.ArrayList io.fusion.water.order.server.ServiceConfiguration.appPropertyList
        //   Please check that at least one of the property files is provided
        //   and contains required variables:
        //   - application-test.properties (file missing)
        //   See https://diff.blue/R033 to resolve this issue.

        // Arrange
        String word = "Hello world!";

        // Act
        EchoResponseData actualRemoteEchoResult = this.paymentServiceImpl.remoteEcho(word);

        // Assert
        assertThat(actualRemoteEchoResult).isNotNull();
    }

    /**
     * Method under test: {@link PaymentServiceImpl#processPayments(PaymentDetails)}
     */
    @Test
    void testProcessPayments() {
        //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

        // Arrange
        PaymentServiceImpl paymentService = new PaymentServiceImpl();

        // Act
        PaymentStatus actualProcessPaymentsResult = paymentService.processPayments(new PaymentDetails());

        // Assert
        assertEquals("Accepted", actualProcessPaymentsResult.getPaymentStatus());
        assertEquals("Ref-uuid", actualProcessPaymentsResult.getPaymentReference());
        assertNull(actualProcessPaymentsResult.getTransactionId());
        assertNull(actualProcessPaymentsResult.getTransactionDate());
        assertEquals(PaymentType.CREDIT_CARD, actualProcessPaymentsResult.getPaymentType());
    }

    /**
     * Method under test:
     * {@link PaymentServiceImpl#processPaymentsExternal(PaymentDetails)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testProcessPaymentsExternal() {
        // TODO: Diffblue Cover was only able to create a partial test for this method:
        //   Reason: Missing Spring properties.
        //   Failed to create Spring context due to unresolvable @Value
        //   properties: private java.util.ArrayList io.fusion.water.order.server.ServiceConfiguration.appPropertyList
        //   Please check that at least one of the property files is provided
        //   and contains required variables:
        //   - application-test.properties (file missing)
        //   See https://diff.blue/R033 to resolve this issue.

        // Arrange
        // TODO: Populate arranged inputs
        PaymentDetails paymentDetails = null;

        // Act
        PaymentStatus actualProcessPaymentsExternalResult = this.paymentServiceImpl
                .processPaymentsExternal(paymentDetails);

        // Assert
        // TODO: Add assertions on result
        assertThat(actualProcessPaymentsExternalResult).isNotNull();
    }

    /**
     * Method under test:
     * {@link PaymentServiceImpl#processPaymentsDefault(PaymentDetails)}
     */
    @Test
    void testProcessPaymentsDefault() {
        //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

        // Arrange
        PaymentServiceImpl paymentService = new PaymentServiceImpl();

        // Act
        PaymentStatus actualProcessPaymentsDefaultResult = paymentService.processPaymentsDefault(new PaymentDetails());

        // Assert
        assertEquals("Accepted", actualProcessPaymentsDefaultResult.getPaymentStatus());
        assertEquals("Ref-uuid", actualProcessPaymentsDefaultResult.getPaymentReference());
        assertNull(actualProcessPaymentsDefaultResult.getTransactionId());
        assertNull(actualProcessPaymentsDefaultResult.getTransactionDate());
        assertEquals(PaymentType.CREDIT_CARD, actualProcessPaymentsDefaultResult.getPaymentType());
    }
}
