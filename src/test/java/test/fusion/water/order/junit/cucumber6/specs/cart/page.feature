#Author: araf.karsh@metamagic.in
#Keywords Summary :
#Feature: Product Search in Amazon
@tag
Feature: Product Search in Amazon

  Scenario: Open Browser and Load Amazon Page
    Given I start a new browser
    When  I am on Amazon search page
    Then I should see title Amazon