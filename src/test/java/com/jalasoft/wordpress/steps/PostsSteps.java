package com.jalasoft.wordpress.steps;

import api.APIManager;
import api.methods.APIAuthMethods;
import framework.CredentialsManager;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.parsing.Parser;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;

import java.io.File;

public class PostsSteps {
    private static final CredentialsManager credentialsManager = CredentialsManager.getInstance();
    private static final APIManager apiManager = APIManager.getInstance();
    private static Response response;
    private static RequestSpecification requestSpecification;

    public PostsSteps()  {}

    @Given("an admin user is logged in with a valid JSON Web Token")
    public void setCredentials() {
        String jwtToken = APIAuthMethods.getJsonWebToken(credentialsManager.getUserName("admin"), credentialsManager.getPassword("admin"));
        requestSpecification = APIAuthMethods.setBearerToken(jwtToken);
        System.out.println("My token is " + jwtToken);
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

    @When("I send a POST request to the WordPress API")
    public void createPost() {
        RestAssured.defaultParser = Parser.JSON;
        File jsonBody = new File("src/test/resources/json/Posts/NewPost.json");
        response = apiManager.post(credentialsManager.getPostsEndPoint(), ContentType.JSON, JsonPath.from(jsonBody).get());
//        response = requestSpecification.post(credentialsManager.getPostsEndPoint(), ContentType.JSON, jsonBody);
//        response = RestAssured.given().header("Authorization", "Bearer " + APIAuthMethods.getJsonWebToken(credentialsManager.getUserName("admin"), credentialsManager.getPassword("admin")))
//                .contentType(ContentType.JSON)
//                .body(JsonPath.from(jsonBody).get(), ObjectMapperType.GSON)
//                .when()
//                .post(credentialsManager.getPostsEndPoint());
//        System.out.println(response.getBody().asString());
    }

    @Then("I should receive a new Post response")
    public void verifyNewPost() {
        Assert.assertEquals(response.getStatusCode(), 201, "Correct status code is not returned");
        Assert.assertTrue(response.getStatusLine().contains("201 Created"), "Correct status code and message is not returned");
    }
}
