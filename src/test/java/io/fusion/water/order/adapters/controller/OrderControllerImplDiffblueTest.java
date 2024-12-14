package io.fusion.water.order.adapters.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.fusion.water.order.domainLayer.models.Customer;
import io.fusion.water.order.domainLayer.models.OrderEntity;
import io.fusion.water.order.domainLayer.models.OrderStatus;
import io.fusion.water.order.domainLayer.models.PaymentDetails;
import io.fusion.water.order.domainLayer.models.PaymentStatus;
import io.fusion.water.order.domainLayer.models.PaymentType;
import io.fusion.water.order.domainLayer.models.ShippingAddress;
import io.fusion.water.order.domainLayer.services.OrderService;

import java.time.LocalDate;

import org.junit.jupiter.api.Disabled;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {OrderControllerImpl.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class OrderControllerImplDiffblueTest {
    @Autowired
    private OrderControllerImpl orderControllerImpl;

    @MockBean
    private OrderService orderService;

    /**
     * Test {@link OrderControllerImpl#getOrderById(String)}.
     * <p>
     * Method under test: {@link OrderControllerImpl#getOrderById(String)}
     */
    @Test
    @DisplayName("Test getOrderById(String)")
    void testGetOrderById() {
        System.out.println("Arrange & Act: Getting Order By ID");
        // Arrange and Act
        ResponseEntity<OrderEntity> actualOrderById = (new OrderControllerImpl()).getOrderById(" id");
        System.out.println("Assert: Checking the Order Entity Returned =  "+actualOrderById);
        // Assert
        HttpStatusCode statusCode = actualOrderById.getStatusCode();
        assertTrue(statusCode instanceof HttpStatus);
        assertNull(actualOrderById.getBody());
        assertEquals(400, actualOrderById.getStatusCodeValue());
        assertEquals(HttpStatus.BAD_REQUEST, statusCode);
        assertFalse(actualOrderById.hasBody());
        assertTrue(actualOrderById.getHeaders().isEmpty());
        // System.out.println("Test Result: Passed: Order ID = "+actualOrderById.getBody().getOrderId());
    }

    /**
     * Test {@link OrderControllerImpl#saveOrder(OrderEntity)}.
     * <p>
     * Method under test: {@link OrderControllerImpl#saveOrder(OrderEntity)}
     */
    @Test
    @DisplayName("Test saveOrder(OrderEntity)")
    @Disabled("TODO: Complete this test")
    void testSaveOrder() throws Exception {
        // TODO: Diffblue Cover was only able to create a partial test for this method:
        //   Reason: Missing beans when creating Spring context.
        //   Failed to create Spring context due to missing beans
        //   in the current Spring profile:
        //   - io.fusion.water.order.adapters.controller.OrderControllerImpl
        //   when running class:
        //   package io.fusion.water.order.adapters.controller;
        //   @org.springframework.test.context.ContextConfiguration(classes = {io.fusion.water.order.adapters.controller.OrderControllerImpl.class})
        //   @org.junit.runner.RunWith(value = org.springframework.test.context.junit4.SpringRunner.class) // if JUnit 4
        //   @org.junit.jupiter.api.extension.ExtendWith(value = org.springframework.test.context.junit.jupiter.SpringExtension.class) // if JUnit 5
        //   public class DiffblueFakeClass128 {
        //     @org.springframework.beans.factory.annotation.Autowired io.fusion.water.order.adapters.controller.OrderControllerImpl orderControllerImpl;
        //     @org.springframework.boot.test.mock.mockito.MockBean io.fusion.water.order.domainLayer.services.OrderService orderService;
        //     @org.junit.Test // if JUnit 4
        //     @org.junit.jupiter.api.Test // if JUnit 5
        //     public void testSpringContextLoads() {}
        //   }
        //   See https://diff.blue/R027 to resolve this issue.

        // Arrange
        MockHttpServletRequestBuilder contentTypeResult = MockMvcRequestBuilders.post("/api/v1/order/")
                .contentType(MediaType.APPLICATION_JSON);

        ObjectMapper objectMapper = new ObjectMapper();
        MockHttpServletRequestBuilder requestBuilder = contentTypeResult
                .content(objectMapper.writeValueAsString(new OrderEntity()));

        // Act
        MockMvcBuilders.standaloneSetup(orderControllerImpl).build().perform(requestBuilder);
    }

    /**
     * Test {@link OrderControllerImpl#saveOrder(OrderEntity)}.
     * <ul>
     *   <li>Then calls {@link OrderEntity#getCustomer()}.</li>
     * </ul>
     * <p>
     * Method under test: {@link OrderControllerImpl#saveOrder(OrderEntity)}
     */
    @Test
    @DisplayName("Test saveOrder(OrderEntity); then calls getCustomer()")
    void testSaveOrder_thenCallsGetCustomer() {
        //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
        //   Run dcover create --keep-partial-tests to gain insights into why
        //   a non-Spring test was created.

        // Arrange
        OrderControllerImpl orderControllerImpl = new OrderControllerImpl();
        OrderEntity _order = mock(OrderEntity.class);
        when(_order.getCustomer()).thenReturn(new Customer("42", "Jane", "Doe", "6625550144"));
        when(_order.getOrderStatus()).thenReturn(OrderStatus.INITIATED);
        when(_order.getPaymentDetails()).thenReturn(new PaymentDetails());
        when(_order.getPaymentStatus()).thenReturn(new PaymentStatus());
        when(_order.getPaymentType()).thenReturn(PaymentType.CREDIT_CARD);
        when(_order.getShippingAddress())
                .thenReturn(new ShippingAddress(" sn", " aline2", " city", " state", " lm", "GB", " z Code"));
        when(_order.getOrderId()).thenReturn("42");
        when(_order.getOrderDate()).thenReturn(LocalDate.of(1970, 1, 1).atStartOfDay());
        when(_order.getOrderItems()).thenReturn(null);

        // Act
        ResponseEntity<OrderEntity> actualSaveOrderResult = orderControllerImpl.saveOrder(_order);

        // Assert
        verify(_order).getCustomer();
        verify(_order).getOrderDate();
        verify(_order).getOrderId();
        verify(_order).getOrderItems();
        verify(_order).getOrderStatus();
        verify(_order).getPaymentDetails();
        verify(_order).getPaymentStatus();
        verify(_order).getPaymentType();
        verify(_order).getShippingAddress();
        HttpStatusCode statusCode = actualSaveOrderResult.getStatusCode();
        assertTrue(statusCode instanceof HttpStatus);
        assertNull(actualSaveOrderResult.getBody());
        assertEquals(400, actualSaveOrderResult.getStatusCodeValue());
        assertEquals(HttpStatus.BAD_REQUEST, statusCode);
        assertFalse(actualSaveOrderResult.hasBody());
        assertTrue(actualSaveOrderResult.getHeaders().isEmpty());
    }

    /**
     * Test {@link OrderControllerImpl#saveOrder(OrderEntity)}.
     * <ul>
     *   <li>When {@code null}.</li>
     * </ul>
     * <p>
     * Method under test: {@link OrderControllerImpl#saveOrder(OrderEntity)}
     */
    @Test
    @DisplayName("Test saveOrder(OrderEntity); when 'null'")
    void testSaveOrder_whenNull() {
        //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
        //   Run dcover create --keep-partial-tests to gain insights into why
        //   a non-Spring test was created.

        // Arrange and Act
        ResponseEntity<OrderEntity> actualSaveOrderResult = (new OrderControllerImpl()).saveOrder(null);

        // Assert
        HttpStatusCode statusCode = actualSaveOrderResult.getStatusCode();
        assertTrue(statusCode instanceof HttpStatus);
        assertNull(actualSaveOrderResult.getBody());
        assertEquals(400, actualSaveOrderResult.getStatusCodeValue());
        assertEquals(HttpStatus.BAD_REQUEST, statusCode);
        assertFalse(actualSaveOrderResult.hasBody());
        assertTrue(actualSaveOrderResult.getHeaders().isEmpty());
    }

    /**
     * Test {@link OrderControllerImpl#saveOrder(OrderEntity)}.
     * <ul>
     *   <li>When {@link OrderEntity} (default constructor).</li>
     * </ul>
     * <p>
     * Method under test: {@link OrderControllerImpl#saveOrder(OrderEntity)}
     */
    @Test
    @DisplayName("Test saveOrder(OrderEntity); when OrderEntity (default constructor)")
    void testSaveOrder_whenOrderEntity() {
        //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
        //   Run dcover create --keep-partial-tests to gain insights into why
        //   a non-Spring test was created.

        // Arrange
        OrderControllerImpl orderControllerImpl = new OrderControllerImpl();

        // Act
        ResponseEntity<OrderEntity> actualSaveOrderResult = orderControllerImpl.saveOrder(new OrderEntity());

        // Assert
        HttpStatusCode statusCode = actualSaveOrderResult.getStatusCode();
        assertTrue(statusCode instanceof HttpStatus);
        assertNull(actualSaveOrderResult.getBody());
        assertEquals(400, actualSaveOrderResult.getStatusCodeValue());
        assertEquals(HttpStatus.BAD_REQUEST, statusCode);
        assertFalse(actualSaveOrderResult.hasBody());
        assertTrue(actualSaveOrderResult.getHeaders().isEmpty());
    }

    /**
     * Test {@link OrderControllerImpl#getOrderById(String)}.
     * <p>
     * Method under test: {@link OrderControllerImpl#getOrderById(String)}
     */
    @Test
    @DisplayName("Test getOrderById(String)")
    @Disabled("TODO: Complete this test")
    void testGetOrderById2() throws Exception {
        // TODO: Diffblue Cover was only able to create a partial test for this method:
        //   Reason: Missing beans when creating Spring context.
        //   Failed to create Spring context due to missing beans
        //   in the current Spring profile:
        //   - io.fusion.water.order.adapters.controller.OrderControllerImpl
        //   when running class:
        //   package io.fusion.water.order.adapters.controller;
        //   @org.springframework.test.context.ContextConfiguration(classes = {io.fusion.water.order.adapters.controller.OrderControllerImpl.class})
        //   @org.junit.runner.RunWith(value = org.springframework.test.context.junit4.SpringRunner.class) // if JUnit 4
        //   @org.junit.jupiter.api.extension.ExtendWith(value = org.springframework.test.context.junit.jupiter.SpringExtension.class) // if JUnit 5
        //   public class DiffblueFakeClass84 {
        //     @org.springframework.beans.factory.annotation.Autowired io.fusion.water.order.adapters.controller.OrderControllerImpl orderControllerImpl;
        //     @org.springframework.boot.test.mock.mockito.MockBean io.fusion.water.order.domainLayer.services.OrderService orderService;
        //     @org.junit.Test // if JUnit 4
        //     @org.junit.jupiter.api.Test // if JUnit 5
        //     public void testSpringContextLoads() {}
        //   }
        //   See https://diff.blue/R027 to resolve this issue.

        // Arrange
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v1/order/{orderId}/", "42");

        // Act
        MockMvcBuilders.standaloneSetup(orderControllerImpl).build().perform(requestBuilder);
    }

    /**
     * Test {@link OrderControllerImpl#cancelOrder(OrderEntity)} with
     * {@code _order}.
     * <p>
     * Method under test: {@link OrderControllerImpl#cancelOrder(OrderEntity)}
     */
    @Test
    @DisplayName("Test cancelOrder(OrderEntity) with '_order'")
    @Disabled("TODO: Complete this test")
    void testCancelOrderWithOrder() throws Exception {
        // TODO: Diffblue Cover was only able to create a partial test for this method:
        //   Reason: Missing beans when creating Spring context.
        //   Failed to create Spring context due to missing beans
        //   in the current Spring profile:
        //   - io.fusion.water.order.adapters.controller.OrderControllerImpl
        //   when running class:
        //   package io.fusion.water.order.adapters.controller;
        //   @org.springframework.test.context.ContextConfiguration(classes = {io.fusion.water.order.adapters.controller.OrderControllerImpl.class})
        //   @org.junit.runner.RunWith(value = org.springframework.test.context.junit4.SpringRunner.class) // if JUnit 4
        //   @org.junit.jupiter.api.extension.ExtendWith(value = org.springframework.test.context.junit.jupiter.SpringExtension.class) // if JUnit 5
        //   public class DiffblueFakeClass2 {
        //     @org.springframework.beans.factory.annotation.Autowired io.fusion.water.order.adapters.controller.OrderControllerImpl orderControllerImpl;
        //     @org.springframework.boot.test.mock.mockito.MockBean io.fusion.water.order.domainLayer.services.OrderService orderService;
        //     @org.junit.Test // if JUnit 4
        //     @org.junit.jupiter.api.Test // if JUnit 5
        //     public void testSpringContextLoads() {}
        //   }
        //   See https://diff.blue/R027 to resolve this issue.

        // Arrange
        MockHttpServletRequestBuilder contentTypeResult = MockMvcRequestBuilders.delete("/api/v1/order/cancel")
                .contentType(MediaType.APPLICATION_JSON);

        ObjectMapper objectMapper = new ObjectMapper();
        MockHttpServletRequestBuilder requestBuilder = contentTypeResult
                .content(objectMapper.writeValueAsString(new OrderEntity()));

        // Act
        MockMvcBuilders.standaloneSetup(orderControllerImpl).build().perform(requestBuilder);
    }

    /**
     * Test {@link OrderControllerImpl#cancelOrder(String)} with {@code _id}.
     * <p>
     * Method under test: {@link OrderControllerImpl#cancelOrder(String)}
     */
    @Test
    @DisplayName("Test cancelOrder(String) with '_id'")
    @Disabled("TODO: Complete this test")
    void testCancelOrderWithId() throws Exception {
        // TODO: Diffblue Cover was only able to create a partial test for this method:
        //   Reason: Missing beans when creating Spring context.
        //   Failed to create Spring context due to missing beans
        //   in the current Spring profile:
        //   - io.fusion.water.order.adapters.controller.OrderControllerImpl
        //   when running class:
        //   package io.fusion.water.order.adapters.controller;
        //   @org.springframework.test.context.ContextConfiguration(classes = {io.fusion.water.order.adapters.controller.OrderControllerImpl.class})
        //   @org.junit.runner.RunWith(value = org.springframework.test.context.junit4.SpringRunner.class) // if JUnit 4
        //   @org.junit.jupiter.api.extension.ExtendWith(value = org.springframework.test.context.junit.jupiter.SpringExtension.class) // if JUnit 5
        //   public class DiffblueFakeClass40 {
        //     @org.springframework.beans.factory.annotation.Autowired io.fusion.water.order.adapters.controller.OrderControllerImpl orderControllerImpl;
        //     @org.springframework.boot.test.mock.mockito.MockBean io.fusion.water.order.domainLayer.services.OrderService orderService;
        //     @org.junit.Test // if JUnit 4
        //     @org.junit.jupiter.api.Test // if JUnit 5
        //     public void testSpringContextLoads() {}
        //   }
        //   See https://diff.blue/R027 to resolve this issue.

        // Arrange
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/api/v1/order/cancel/{orderId}/",
                "42");

        // Act
        MockMvcBuilders.standaloneSetup(orderControllerImpl).build().perform(requestBuilder);
    }

    /**
     * Test {@link OrderControllerImpl#cancelOrder(String)} with {@code _id}.
     * <ul>
     *   <li>When {@code id}.</li>
     * </ul>
     * <p>
     * Method under test: {@link OrderControllerImpl#cancelOrder(String)}
     */
    @Test
    @DisplayName("Test cancelOrder(String) with '_id'; when 'id'")
    void testCancelOrderWithId_whenId() {
        //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
        //   Run dcover create --keep-partial-tests to gain insights into why
        //   a non-Spring test was created.

        // Arrange and Act
        ResponseEntity<OrderEntity> actualCancelOrderResult = (new OrderControllerImpl()).cancelOrder(" id");

        // Assert
        HttpStatusCode statusCode = actualCancelOrderResult.getStatusCode();
        assertTrue(statusCode instanceof HttpStatus);
        assertNull(actualCancelOrderResult.getBody());
        assertEquals(400, actualCancelOrderResult.getStatusCodeValue());
        assertEquals(HttpStatus.BAD_REQUEST, statusCode);
        assertFalse(actualCancelOrderResult.hasBody());
        assertTrue(actualCancelOrderResult.getHeaders().isEmpty());
    }

    /**
     * Test {@link OrderControllerImpl#cancelOrder(String)} with {@code _id}.
     * <ul>
     *   <li>When {@code null}.</li>
     * </ul>
     * <p>
     * Method under test: {@link OrderControllerImpl#cancelOrder(String)}
     */
    @Test
    @DisplayName("Test cancelOrder(String) with '_id'; when 'null'")
    void testCancelOrderWithId_whenNull() {
        //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
        //   Run dcover create --keep-partial-tests to gain insights into why
        //   a non-Spring test was created.

        // Arrange and Act
        ResponseEntity<OrderEntity> actualCancelOrderResult = (new OrderControllerImpl()).cancelOrder((String) null);

        // Assert
        HttpStatusCode statusCode = actualCancelOrderResult.getStatusCode();
        assertTrue(statusCode instanceof HttpStatus);
        assertNull(actualCancelOrderResult.getBody());
        assertEquals(400, actualCancelOrderResult.getStatusCodeValue());
        assertEquals(HttpStatus.BAD_REQUEST, statusCode);
        assertFalse(actualCancelOrderResult.hasBody());
        assertTrue(actualCancelOrderResult.getHeaders().isEmpty());
    }

    /**
     * Test {@link OrderControllerImpl#cancelOrder(OrderEntity)} with
     * {@code _order}.
     * <ul>
     *   <li>Then calls {@link OrderEntity#getCustomer()}.</li>
     * </ul>
     * <p>
     * Method under test: {@link OrderControllerImpl#cancelOrder(OrderEntity)}
     */
    @Test
    @DisplayName("Test cancelOrder(OrderEntity) with '_order'; then calls getCustomer()")
    void testCancelOrderWithOrder_thenCallsGetCustomer() {
        //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
        //   Run dcover create --keep-partial-tests to gain insights into why
        //   a non-Spring test was created.

        // Arrange
        OrderControllerImpl orderControllerImpl = new OrderControllerImpl();
        OrderEntity _order = mock(OrderEntity.class);
        when(_order.getCustomer()).thenReturn(new Customer("42", "Jane", "Doe", "6625550144"));
        when(_order.getOrderStatus()).thenReturn(OrderStatus.INITIATED);
        when(_order.getPaymentDetails()).thenReturn(new PaymentDetails());
        when(_order.getPaymentStatus()).thenReturn(new PaymentStatus());
        when(_order.getPaymentType()).thenReturn(PaymentType.CREDIT_CARD);
        when(_order.getShippingAddress())
                .thenReturn(new ShippingAddress(" sn", " aline2", " city", " state", " lm", "GB", " z Code"));
        when(_order.getOrderId()).thenReturn("42");
        when(_order.getOrderDate()).thenReturn(LocalDate.of(1970, 1, 1).atStartOfDay());
        when(_order.getOrderItems()).thenReturn(null);

        // Act
        ResponseEntity<OrderEntity> actualCancelOrderResult = orderControllerImpl.cancelOrder(_order);

        // Assert
        verify(_order).getCustomer();
        verify(_order).getOrderDate();
        verify(_order).getOrderId();
        verify(_order).getOrderItems();
        verify(_order).getOrderStatus();
        verify(_order).getPaymentDetails();
        verify(_order).getPaymentStatus();
        verify(_order).getPaymentType();
        verify(_order).getShippingAddress();
        HttpStatusCode statusCode = actualCancelOrderResult.getStatusCode();
        assertTrue(statusCode instanceof HttpStatus);
        assertNull(actualCancelOrderResult.getBody());
        assertEquals(400, actualCancelOrderResult.getStatusCodeValue());
        assertEquals(HttpStatus.BAD_REQUEST, statusCode);
        assertFalse(actualCancelOrderResult.hasBody());
        assertTrue(actualCancelOrderResult.getHeaders().isEmpty());
    }

    /**
     * Test {@link OrderControllerImpl#cancelOrder(OrderEntity)} with
     * {@code _order}.
     * <ul>
     *   <li>When {@code null}.</li>
     * </ul>
     * <p>
     * Method under test: {@link OrderControllerImpl#cancelOrder(OrderEntity)}
     */
    @Test
    @DisplayName("Test cancelOrder(OrderEntity) with '_order'; when 'null'")
    void testCancelOrderWithOrder_whenNull() {
        //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
        //   Run dcover create --keep-partial-tests to gain insights into why
        //   a non-Spring test was created.

        // Arrange and Act
        ResponseEntity<OrderEntity> actualCancelOrderResult = (new OrderControllerImpl()).cancelOrder((OrderEntity) null);

        // Assert
        HttpStatusCode statusCode = actualCancelOrderResult.getStatusCode();
        assertTrue(statusCode instanceof HttpStatus);
        assertNull(actualCancelOrderResult.getBody());
        assertEquals(400, actualCancelOrderResult.getStatusCodeValue());
        assertEquals(HttpStatus.BAD_REQUEST, statusCode);
        assertFalse(actualCancelOrderResult.hasBody());
        assertTrue(actualCancelOrderResult.getHeaders().isEmpty());
    }

    /**
     * Test {@link OrderControllerImpl#cancelOrder(OrderEntity)} with
     * {@code _order}.
     * <ul>
     *   <li>When {@link OrderEntity} (default constructor).</li>
     * </ul>
     * <p>
     * Method under test: {@link OrderControllerImpl#cancelOrder(OrderEntity)}
     */
    @Test
    @DisplayName("Test cancelOrder(OrderEntity) with '_order'; when OrderEntity (default constructor)")
    void testCancelOrderWithOrder_whenOrderEntity() {
        //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
        //   Run dcover create --keep-partial-tests to gain insights into why
        //   a non-Spring test was created.

        // Arrange
        OrderControllerImpl orderControllerImpl = new OrderControllerImpl();

        // Act
        ResponseEntity<OrderEntity> actualCancelOrderResult = orderControllerImpl.cancelOrder(new OrderEntity());

        // Assert
        HttpStatusCode statusCode = actualCancelOrderResult.getStatusCode();
        assertTrue(statusCode instanceof HttpStatus);
        assertNull(actualCancelOrderResult.getBody());
        assertEquals(400, actualCancelOrderResult.getStatusCodeValue());
        assertEquals(HttpStatus.BAD_REQUEST, statusCode);
        assertFalse(actualCancelOrderResult.hasBody());
        assertTrue(actualCancelOrderResult.getHeaders().isEmpty());
    }

    /**
     * Test {@link OrderControllerImpl#updateOrderStatus(String, String)}.
     * <p>
     * Method under test:
     * {@link OrderControllerImpl#updateOrderStatus(String, String)}
     */
    @Test
    @DisplayName("Test updateOrderStatus(String, String)")
    void testUpdateOrderStatus() {
        //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
        //   Run dcover create --keep-partial-tests to gain insights into why
        //   a non-Spring test was created.

        // Arrange and Act
        ResponseEntity<OrderEntity> actualUpdateOrderStatusResult = (new OrderControllerImpl()).updateOrderStatus(" id",
                " status");

        // Assert
        HttpStatusCode statusCode = actualUpdateOrderStatusResult.getStatusCode();
        assertTrue(statusCode instanceof HttpStatus);
        assertNull(actualUpdateOrderStatusResult.getBody());
        assertEquals(400, actualUpdateOrderStatusResult.getStatusCodeValue());
        assertEquals(HttpStatus.BAD_REQUEST, statusCode);
        assertFalse(actualUpdateOrderStatusResult.hasBody());
        assertTrue(actualUpdateOrderStatusResult.getHeaders().isEmpty());
    }

    /**
     * Test {@link OrderControllerImpl#updateOrderStatus(String, String)}.
     * <p>
     * Method under test:
     * {@link OrderControllerImpl#updateOrderStatus(String, String)}
     */
    @Test
    @DisplayName("Test updateOrderStatus(String, String)")
    @Disabled("TODO: Complete this test")
    void testUpdateOrderStatus2() throws Exception {
        // TODO: Diffblue Cover was only able to create a partial test for this method:
        //   Reason: Missing beans when creating Spring context.
        //   Failed to create Spring context due to missing beans
        //   in the current Spring profile:
        //   - io.fusion.water.order.adapters.controller.OrderControllerImpl
        //   when running class:
        //   package io.fusion.water.order.adapters.controller;
        //   @org.springframework.test.context.ContextConfiguration(classes = {io.fusion.water.order.adapters.controller.OrderControllerImpl.class})
        //   @org.junit.runner.RunWith(value = org.springframework.test.context.junit4.SpringRunner.class) // if JUnit 4
        //   @org.junit.jupiter.api.extension.ExtendWith(value = org.springframework.test.context.junit.jupiter.SpringExtension.class) // if JUnit 5
        //   public class DiffblueFakeClass166 {
        //     @org.springframework.beans.factory.annotation.Autowired io.fusion.water.order.adapters.controller.OrderControllerImpl orderControllerImpl;
        //     @org.springframework.boot.test.mock.mockito.MockBean io.fusion.water.order.domainLayer.services.OrderService orderService;
        //     @org.junit.Test // if JUnit 4
        //     @org.junit.jupiter.api.Test // if JUnit 5
        //     public void testSpringContextLoads() {}
        //   }
        //   See https://diff.blue/R027 to resolve this issue.

        // Arrange
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .put("/api/v1/order/{orderId}/status/{statusId}/", "42", "42");

        // Act
        MockMvcBuilders.standaloneSetup(orderControllerImpl).build().perform(requestBuilder);
    }
}
