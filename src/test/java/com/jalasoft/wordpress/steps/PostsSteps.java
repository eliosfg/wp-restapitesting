package com.jalasoft.wordpress.steps;

import api.APIManager;
import api.methods.APIAuthMethods;
import framework.CredentialsManager;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;

public class PostsSteps {
    private static final CredentialsManager credentialsManager = CredentialsManager.getInstance();
    private static final APIManager apiManager = APIManager.getInstance();
    private static Response response;
    private static RequestSpecification requestSpecification;

    public PostsSteps()  {}

    @Given("I have a valid JWT for the WordPress API")
    public void setup() {
        String jwtToken = APIAuthMethods.getJsonWebToken(credentialsManager.getUserName("admin"), credentialsManager.getPassword("admin"));
        requestSpecification = APIAuthMethods.setBearerToken(jwtToken);
//        response = RestAssured
//                    .given()
//                    .header("Authorization", "Bearer " + jwtToken)
//                    .get(credentialsManager.getPostsEndPoint());
    }

    @When("I make a GET request to the WordPress API")
    public void getAllPosts() {
        response = requestSpecification.get(credentialsManager.getPostsEndPoint());
//        System.out.println(response.getBody().asString());
    }

    @Then("I should receive a list of Posts")
    public void verifyPosts() {
        Assert.assertEquals(response.getStatusCode(), 200, "Correct status code is not returned");
        Assert.assertTrue(response.getStatusLine().contains("200 OK"), "Correct status code and message is not returned");
//        Assert.assertNull(response.getBody().asString().contains("error"), "Error Message was returned");
    }

    @When("I send a GET Post by Id request to the WordPress API")
    public void getPostById() {
        response = requestSpecification.get(String.format(credentialsManager.getPostsByIdEndpoint(), 1));
    }

    @Then("I should receive a single Post")
    public void verifyPost() {
        Assert.assertEquals(response.getStatusCode(), 200, "Correct status code is not returned");
        Assert.assertTrue(response.getStatusLine().contains("200 OK"), "Correct status code and message is not returned");
    }
}
