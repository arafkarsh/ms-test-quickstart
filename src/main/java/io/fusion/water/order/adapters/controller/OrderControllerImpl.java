/**
 * (C) Copyright 2021 Araf Karsh Hamid 
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

package io.fusion.water.order.adapters.controller;

import static java.lang.invoke.MethodHandles.lookup;
import static org.slf4j.LoggerFactory.getLogger;

import java.time.LocalDateTime;

import org.slf4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.annotation.RequestScope;

import io.fusion.water.order.domainLayer.models.OrderEntity;
import io.fusion.water.order.domainLayer.services.OrderService;
import io.fusion.water.order.utils.Utils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.fusion.water.order.domainLayer.services.OrderController;

/**
 * Order Web Service
 *
 * @author arafkarsh
 *
 */
@RestController
@RequestMapping(value = "/api/v1/order")
@RequestScope
@Tag(name = "Order", description = "Order Service")
public class OrderControllerImpl implements OrderController {

	// Set Logger -> Lookup will automatically determine the class name.
	private static final Logger log = getLogger(lookup().lookupClass());

	@Autowired
	OrderService orderService;

	/**
	 * Get Order - Follows REST Guidelines for URI
	 * By Order ID
	 */
	@Operation(summary = "Fetch Order based on Order ID")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200",
					description = "Fetch Order details based on Order ID",
					content = {@Content(mediaType = "application/json")}),
			@ApiResponse(responseCode = "404",
					description = "Order ID NOT Available",
					content = @Content)
	})
	@Override
	@GetMapping("/{orderId}/")
	public ResponseEntity<OrderEntity> getOrderById(@PathVariable("orderId") String _id) {
		OrderEntity orderEntity = null;
		try  {
			orderEntity = orderService.getOrderById(_id);
		} catch (Exception e) {
			return new ResponseEntity<OrderEntity>(orderEntity, HttpStatus.BAD_REQUEST);
		}
		return ResponseEntity.ok(orderEntity);
	}

	/**
	 * Create Order - REST Guidelines says No Verbs to be used in the URI
	 * "/api/order/create" is part of the POST URI for ease of use.
	 */
	@Operation(summary = "Create Order")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200",
					description = "Create Order with Order Items and shipping address.",
					content = {@Content(mediaType = "application/json")}),
			@ApiResponse(responseCode = "500",
					description = "Unable to save Order",
					content = @Content)
	})
	@Override
	@PostMapping("/")
	public ResponseEntity<OrderEntity> saveOrder(@RequestBody OrderEntity _order) {
		System.out.println(LocalDateTime.now()+"|Order="+Utils.toJsonString(_order));
		OrderEntity orderEntity = null;
		// Create HttpHeaders object and add a custom header
		// HttpHeaders are used like this to Demo a RestAssured test case.
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "jwt-1");
		headers.add("X-CSRF-TOKEN", "csrf-token");
		// The above headers are not meant for production usage.
		try  {
			orderEntity = orderService.processOrder(_order);
		} catch (Exception e) {
			System.out.println(LocalDateTime.now()+"|Error="+e.getMessage());
			return new ResponseEntity<OrderEntity>(orderEntity, HttpStatus.BAD_REQUEST);
		}
		System.out.println(LocalDateTime.now()+"|Order Saved.");
		return new ResponseEntity<OrderEntity>(orderEntity, headers, HttpStatus.OK);
	}

	/**
	 * Cancel Order - REST Guidelines says No Verbs to be used in the URI
	 * By Order
	 * "/api/order/cancel" is part of the PUT URI for ease of use.
	 */
	@Operation(summary = "Cancel Order based on Order")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200",
					description = "Cancel Order based on Order Details",
					content = {@Content(mediaType = "application/json")}),
			@ApiResponse(responseCode = "500",
					description = "Unable to cancel Order",
					content = @Content)
	})
	@Override
	@DeleteMapping("/cancel")
	public ResponseEntity<OrderEntity> cancelOrder(@RequestBody  OrderEntity _order) {
		System.out.println(LocalDateTime.now()+"|Order="+Utils.toJsonString(_order));
		OrderEntity orderEntity = null;
		try  {
			orderEntity = orderService.cancelOrder(_order);
		} catch (Exception e) {
			System.out.println(LocalDateTime.now()+"|Error="+e.getMessage());
			return new ResponseEntity<OrderEntity>(orderEntity, HttpStatus.BAD_REQUEST);
		}
		System.out.println(LocalDateTime.now()+"|Order Cancelled.");
		return ResponseEntity.ok(orderEntity);
	}

	/**
	 * Cancel Order - REST Guidelines says No Verbs to be used in the URI
	 * By Order ID
	 * "/api/order/cancel/{orderId}/" is part of the PUT URI for ease of use.
	 */
	@Operation(summary = "Cancel Order based on Order Id")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200",
					description = "Cancel Order based on Order Id.",
					content = {@Content(mediaType = "application/json")}),
			@ApiResponse(responseCode = "500",
					description = "Unable to cancel Order",
					content = @Content)
	})
	@Override
	@DeleteMapping("/cancel/{orderId}/")
	public ResponseEntity<OrderEntity> cancelOrder(
			@PathVariable("orderId") String _id) {
		System.out.println(LocalDateTime.now()+"|Order="+Utils.toJsonString(_id));
		OrderEntity orderEntity = null;
		try  {
			orderEntity = orderService.cancelOrder(_id);
		} catch (Exception e) {
			System.out.println(LocalDateTime.now()+"|Error="+e.getMessage());
			return new ResponseEntity<OrderEntity>(orderEntity, HttpStatus.BAD_REQUEST);
		}
		System.out.println(LocalDateTime.now()+"|Order Cancelled.");
		return ResponseEntity.ok(orderEntity);
	}

	/**
	 * Update Order Status
	 *
	 * @param _order
	 * @return
	 */
	@Operation(summary = "Update Order Status based on Order Id")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200",
					description = "Update Order Status based on Order Id.",
					content = {@Content(mediaType = "application/json")}),
			@ApiResponse(responseCode = "500",
					description = "Unable to Update Order Status",
					content = @Content)
	})
	@PutMapping("/{orderId}/status/{statusId}/")
	public ResponseEntity<OrderEntity> updateOrderStatus(
			@PathVariable("orderId") String _id,
			@PathVariable("statusId") String _status) {
		System.out.println(LocalDateTime.now()+"|Order="+_id+"|Status="+_status);
		OrderEntity orderEntity = null;
		try  {
			orderEntity = orderService.updateOrderStatus(_id, _status);
		} catch (Exception e) {
			System.out.println(LocalDateTime.now()+"|Error="+e.getMessage());
			return new ResponseEntity<OrderEntity>(orderEntity, HttpStatus.BAD_REQUEST);
		}
		System.out.println(LocalDateTime.now()+"|Order Updated.");
		return ResponseEntity.ok(orderEntity);
	}

}
