Feature: Browser Search
  Background: A chrome tab
    Given A new browser session

  Scenario: Google Search
    When I navigate to "https://google.com"
    And I type "Selenium-Jupiter"
    And I press Enter
    Then I should be shown results including "Selenium"

  Scenario: BlazeDemo
    When I navigate to "https://blazedemo.com/"
    And I click "Find Flights"
    Then I should be shown results including "Paris to Buenos Aires"
