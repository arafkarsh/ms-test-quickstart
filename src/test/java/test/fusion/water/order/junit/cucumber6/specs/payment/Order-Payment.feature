#Author: araf.karsh@metamagic.in
#Keywords Summary :
#Feature: Order Payments

@tag
Feature: Order Payments
	As a Consumer
  I want to make the payent 
  So that I can buy products

  Scenario: Save Payment
    Given The request is authenticated
    When Input contains login as "userloginid", order as "orderid", and payment in "payment details"
    Then The System Validates the credit card 123456789 and the expirydate "Expiry Date" and the cardname "John Doe" and the cvv 123 Must NOT be Null
