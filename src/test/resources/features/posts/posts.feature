Feature: Posts CRUD

  @Posts
  Background: Given a user is logged in
    Given an admin user is logged in with a valid JSON Web Token

  @Posts
  Scenario: Get all Posts from WordPress API
#    Given an admin user is logged in with a valid JSON Web Token
    When I make a GET request to the WordPress API
    Then I should receive a list of Posts

  @Posts
  Scenario: Get a single Post from WordPress API
    When I send a GET Post by Id request to the WordPress API
    Then I should receive a single Post

  @Posts
  Scenario: Create a new Post in Wordpress
    When I send a POST request to the WordPress API
    Then I should receive a new Post response