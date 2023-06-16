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

package test.fusion.water.order.testng.cucumber6.steps.payment;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

/**
 * Payment Criteria Test
 * 
 * @author arafkarsh
 *
 */
public class PaymentSteps {
	
	@Given("The request is authenticated")
	public void the_request_is_authenticated() {
		System.out.println("Valid Request");
	}

	@When("Input contains login as {string}, order as {string}, and payment in {string}")
	public void input_contains_login_as_order_as_and_payment_in(
			String loginId, String orderId, String payment) {
		System.out.println("User Payment Details");
	}

	@Then("The System Validates the credit card {int} and the expirydate {string} and the cardname {string} and the cvv {int} Must NOT be Null")
	public void the_system_validates_the_credit_card_and_the_expirydate_and_the_cardname_and_the_cvv_must_not_be_null(
			Integer creditcard, String expiryDate, String cardHolderName, Integer cvv) {
		System.out.println("Credit Card Details validated");
	}

}
