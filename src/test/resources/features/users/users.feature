Feature: Users

  @Test
  Scenario: JSON Web Token Authentication
    Given I send a POST request with a valid username and password
    Then I get the JWT Token in a JSON response