Feature: Posts

  @CRUD
  Scenario: Get all Posts from WordPress API
    Given I have a valid JWT for the WordPress API
    When I make a GET request to the WordPress API
    Then I should receive a list of Posts

  @CRUD
  Scenario: Get a single Post from WordPress API
    When I send a GET Post by Id request to the WordPress API
    Then I should receive a single Post