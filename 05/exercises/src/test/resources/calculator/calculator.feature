Feature: Basic Maths
  Background: A calculator
    Given a calculator I just turned on

  Scenario: Adding
    When I add 5 and 8
    Then the result is 13

  Scenario: Subtracting
      When I subtract 5 from 10
      Then the result is 5

  Scenario Outline: Multiple additions
    When I add <a> and <b>
    Then the result is <c>

    Examples: Single Digits
    |a|b|c|
    |2|5|7|
    |3|2|5|