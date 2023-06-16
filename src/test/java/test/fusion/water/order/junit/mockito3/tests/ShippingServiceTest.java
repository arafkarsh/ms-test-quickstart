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

package test.fusion.water.order.junit.mockito3.tests;

import org.mockito.ArgumentCaptor;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.atMost;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.AdditionalMatchers.or;

import static org.slf4j.LoggerFactory.getLogger;

import static java.lang.invoke.MethodHandles.lookup;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.extension.ExtendWith;

import org.slf4j.Logger;

import io.fusion.water.order.adapters.service.DeliveryCityServiceImpl;
import io.fusion.water.order.adapters.service.ShippingServiceImpl;
import io.fusion.water.order.domainLayer.models.DeliveryCity;
import test.fusion.water.order.junit.junit5.annotations.tests.Critical;
import test.fusion.water.order.junit.junit5.annotations.tests.Functional;
import test.fusion.water.order.junit.junit5.annotations.tools.Mockito3;
import test.fusion.water.order.junit.junit5.extensions.TestTimeExtension;
import test.fusion.water.order.junit.mockito3.utils.DeliveryCityAnswer;

/**
 * Shipping Service Test
 * 
 * @author arafkarsh
 *
 */

@Mockito3()
@Critical()
@Functional()
@TestMethodOrder(OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(TestTimeExtension.class)
@ExtendWith(MockitoExtension.class)
public class ShippingServiceTest {

	static final Logger log = getLogger(lookup().lookupClass());

	private int counter = 1;
	
	@Mock
	private DeliveryCityServiceImpl deliveryCityService;
	
	private DeliveryCity bengaluru;
	private DeliveryCity kochi;
	private DeliveryCity chennai;
	
	@InjectMocks
	private ShippingServiceImpl shippingService;
	
	/**
	 * if the @TestInstance(TestInstance.Lifecycle.PER_CLASS)
	 * is available then the method need not be static
	 */
    @BeforeAll
    public void setupAll() {
        System.out.println("== Shipping Service Mock Suite Execution Started...");
    }
    
    @BeforeEach
    public void setup() {
        System.out.println(counter+". Create DeliveryCity...");
        bengaluru 	= new DeliveryCity("Bengaluru", "", "India", "00000");
        kochi 		= new DeliveryCity("Kochi", "", "India", "00000");
        chennai 	= new DeliveryCity("Chennai", "", "India", "00000");

    }
    
	@Test
	@DisplayName("1. Mock > Test Delivery City Bengaluru")
	@Order(1)
	public void testDeliveryCity() {
		// Set the Shipping Service with Bengaluru City
		when(deliveryCityService.getDeliveryCity("Bengaluru", "", "India")).thenReturn(bengaluru);
		
		// Check the Delivery City in Shipping Service
		DeliveryCity city = shippingService.getCity("Bengaluru", "", "India");
		
		// Then Check the City
		assertEquals(bengaluru.getCityName(), city.getCityName());
	}
	
	@Test
	@DisplayName("2. Mock > Test Delivery Cities Bengaluru, Chennai, Kochi")
	@Order(2)
	public void testDeliveryCities() {
		// Set the Shipping Service with multiple Cities
		when(deliveryCityService.getDeliveryCity("Bengaluru", "", "India")).thenReturn(bengaluru);
		when(deliveryCityService.getDeliveryCity("Chennai", "", "India")).thenReturn(chennai);
		when(deliveryCityService.getDeliveryCity("Kochi", "", "India")).thenReturn(kochi);

		ArrayList<String> cities = new ArrayList<String>();
		cities.add("Bengaluru");
		cities.add("Chennai");
		cities.add("Kochi");
		
		// Check the Delivery City in Shipping Service
		// Arguments (numbers) to the Stub (DeliveryCityService) must be same
		ArrayList<DeliveryCity> cityList = shippingService.getCities(cities, "", "India");
		
		// Then Check the City
		assertEquals(3, cityList.size());
	}
	
	@Test
	@DisplayName("3. Mock > InOrder > Test Delivery Cities Bengaluru, Chennai, Kochi")
	@Order(3)
	public void testDeliveryCitiesInOrder() {
		// Set the Shipping Service with multiple Cities
		InOrder inorder = inOrder(deliveryCityService); 
		when(deliveryCityService.getDeliveryCity("Bengaluru", "", "India")).thenReturn(bengaluru);
		when(deliveryCityService.getDeliveryCity("Chennai", "", "India")).thenReturn(chennai);
		when(deliveryCityService.getDeliveryCity("Kochi", "", "India")).thenReturn(kochi);

		// Change the Order of the Cities and the test will fail
		ArrayList<String> cities = new ArrayList<String>();
		cities.add("Bengaluru");
		cities.add("Chennai");
		cities.add("Kochi");
		
		// Check the Delivery City in Shipping Service
		// Arguments (numbers) to the Stub (DeliveryCityService) must be same
		ArrayList<DeliveryCity> cityList = shippingService.getCities(cities, "", "India");
		
		// Then Check the City
		assertEquals(3, cityList.size());
		inorder.verify(deliveryCityService).getDeliveryCity("Bengaluru", "", "India");
		inorder.verify(deliveryCityService).getDeliveryCity("Chennai", "", "India");
		inorder.verify(deliveryCityService).getDeliveryCity("Kochi", "", "India");

	}
	
	@Test
	@DisplayName("4. Mock > Arg Matcher > Test Delivery City Kochi - AnyString()")
	@Order(4)
	public void testDeliveryCityAnyString() {
		// Set the Shipping Service with Kochi City
		when(deliveryCityService.getDeliveryCity(
				anyString(), anyString(), anyString())).thenReturn(kochi);
		
		// Check the Delivery City in Shipping Service
		DeliveryCity city = shippingService.getCity("Kochi", "", "Japan");
		
		// Then Check the City
		assertEquals(kochi.getCityName(), city.getCityName());
	}
	
	@Test
	@DisplayName("5. Mock > Arg Matcher > Test Delivery City Kochi - eq('Kochi') ")
	@Order(5)
	public void testDeliveryCityEQString() {
		// Set the Shipping Service with Kochi City
		when(deliveryCityService.getDeliveryCity(
				eq("Kochi"), anyString(), anyString())).thenReturn(kochi);
		
		// Check the Delivery City in Shipping Service
		DeliveryCity city = shippingService.getCity("Kochi", "", "Japan");
		
		// Then Check the City
		assertEquals(kochi.getCityName(), city.getCityName());
	}
	
	@Test
	@DisplayName("6. Mock > Arg Matcher > Test Delivery City Kochi - OR Condition ")
	@Order(6)
	public void testDeliveryCityOR() {
		// Set the Shipping Service with Kochi City
		when(deliveryCityService.getDeliveryCity( 
				or(eq("Kochi"), eq("Cochin")), anyString(), eq("India")))
		.thenReturn(kochi);
		
		// Check the Delivery City in Shipping Service
		DeliveryCity city = shippingService.getCity("Cochin", "", "India");
		
		// Then Check the City
		assertEquals(kochi.getCityName(), city.getCityName());
	}
	
	@Test
	@DisplayName("7. Mock > Counts > Test Delivery Cities Bengaluru")
	@Order(7)
	public void testDeliveryCitiesCount() {
		// Set the Shipping Service with multiple Cities
		when(deliveryCityService.getDeliveryCity(anyString(), anyString(), anyString()))
		.thenReturn(bengaluru);

		// Change the Order of the Cities and the test will fail
		ArrayList<String> cities = new ArrayList<String>();
		cities.add("Bengaluru");
		cities.add("Chennai");
		cities.add("Kochi");
		
		// Check the Delivery City in Shipping Service
		// Arguments (numbers) to the Stub (DeliveryCityService) must be same
		ArrayList<DeliveryCity> cityList = shippingService.getCities(cities, "", "India");
		
		// Then Check the City
		assertEquals(3, cityList.size());
		// Verification will Fail if the number of calls are NOT EQUAL TO 3
		// In this Example 3 calls where made by the Shipping Service, as an 
		// Array of 3 cities were passed to Shipping Service
		verify(deliveryCityService, times(3)).getDeliveryCity(anyString(), anyString(), anyString());

	}
	
	@Test
	@DisplayName("8. Mock > Counts > Test Delivery Cities Bengaluru atLeast(), atMost()")
	@Order(8)
	public void testDeliveryCitiesCount2() {
		// Set the Shipping Service with multiple Cities
		when(deliveryCityService.getDeliveryCity(anyString(), anyString(), anyString()))
		.thenReturn(bengaluru);

		// Change the Order of the Cities and the test will fail
		ArrayList<String> cities = new ArrayList<String>();
		cities.add("Bengaluru");
		cities.add("Chennai");
		cities.add("Kochi");
		
		// Check the Delivery City in Shipping Service
		// Arguments (numbers) to the Stub (DeliveryCityService) must be same
		ArrayList<DeliveryCity> cityList = shippingService.getCities(cities, "", "India");
		
		// Then Check the City
		assertEquals(3, cityList.size());
		// Verification will Fail if the number of calls are NOT EQUAL TO 3
		// In this Example 3 calls where made by the Shipping Service, as an 
		// Array of 3 cities were passed to Shipping Service
		verify(deliveryCityService, atLeast(1)).getDeliveryCity(anyString(), anyString(), anyString());
		verify(deliveryCityService, atMost(3)).getDeliveryCity(anyString(), anyString(), anyString());

	}
	
	@Test
	@DisplayName("9. Mock > Answer > Test Delivery Cities ")
	@Order(9)
	public void testDeliveryCitiesCustomAnswer() {
		// Set the Shipping Service with multiple Cities
		when(deliveryCityService.getDeliveryCity(anyString(), anyString(), anyString()))
		.thenAnswer(new DeliveryCityAnswer());
		
		// Change the Order of the Cities and the test will fail
		ArrayList<String> cities = new ArrayList<String>();
		cities.add("Bengaluru");
		cities.add("Chennai");
		cities.add("Kochi");
		
		// Check the Delivery City in Shipping Service
		ArrayList<DeliveryCity> cityList = shippingService.getCities(cities, "", "India");

		assertEquals(3, cityList.size());
		for(String cityName : cities) {
			assertTrue(cityList.stream()
					.anyMatch(city -> cityName.equalsIgnoreCase(city.getCityName()))
			);
		}
	}
	
	@Test
	@DisplayName("10. Mock > Captor > Test Delivery Cities ")
	@Order(10)
	public void testDeliveryCitiesCaptor() {
		ArgumentCaptor<String> city = ArgumentCaptor.forClass(String.class);
		ArgumentCaptor<String> state = ArgumentCaptor.forClass(String.class);
		ArgumentCaptor<String> country = ArgumentCaptor.forClass(String.class);

		
		// Set the Shipping Service with multiple Cities
		when(deliveryCityService.getDeliveryCity(city.capture(), state.capture(), country.capture()))
		.thenAnswer(new DeliveryCityAnswer());
		
		// Change the Order of the Cities and the test will fail
		ArrayList<String> cities = new ArrayList<String>();
		cities.add("Bengaluru");
		cities.add("Chennai");
		cities.add("Kochi");

		// Check the Delivery City in Shipping Service
		ArrayList<DeliveryCity> cityList = shippingService.getCities(cities, "", "India");

		assertEquals(3, cityList.size());
		for(String cityName : cities) {
			assertTrue(cityList.stream()
					.anyMatch(cityObj -> city.getValue().equalsIgnoreCase(cityObj.getCityName()))
			);
		}
	}
	
	@Test
	@DisplayName("11. Mock > Throw Exception > Test Delivery Cities ")
	@Order(11)
	public void testDeliveryCitiesThrowException() {
		// Set the Shipping Service with multiple Cities
		when(deliveryCityService.getDeliveryCity(anyString(), anyString(), anyString()))
		.thenThrow(new RuntimeException("Data Not Available"));
		
		// Change the Order of the Cities and the test will fail
		ArrayList<String> cities = new ArrayList<String>();
		cities.add("Bengaluru");
		cities.add("Chennai");
		cities.add("Kochi");
		boolean failed = false;
		ArrayList<DeliveryCity> cityList = null;
		try {
			// Check the Delivery City in Shipping Service
			cityList = shippingService.getCities(cities, "", "India");
		} catch (Exception e) {
			failed = true;
			System.out.println("Test 11 > "+e.getMessage());
			// e.printStackTrace();
		}
		// Add the variant where Exception is tested implicitly
		// Verify
		assertTrue(failed);
	}
	

	
	// @Test
	@DisplayName("20. Mock > No Calls > Test Delivery Cities Bengaluru ")
	@Order(20)
	public void testDeliveryCitiesNoCalls() {
		// No Expectations 

		// Change the Order of the Cities and the test will fail
		ArrayList<String> cities = new ArrayList<String>();
		cities.add("Bengaluru");
		cities.add("Chennai");
		cities.add("Kochi");
		
		// Check the Delivery City in Shipping Service
		try {
			ArrayList<DeliveryCity> cityList = shippingService.getCities(cities, null, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		verifyNoMoreInteractions(deliveryCityService);
	}
	
    @AfterEach
    public void tearDown() {
        counter++;
    }
    
	/**
	 * if the @TestInstance(TestInstance.Lifecycle.PER_CLASS)
	 * is available then the method need not be static
	 */
    @AfterAll
    public void tearDownAll() {
        System.out.println("== Shipping Service Mock Suite Execution Completed...");
    }
	
}
