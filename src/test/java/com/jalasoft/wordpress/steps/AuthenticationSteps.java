package com.jalasoft.wordpress.steps;

import api.APIManager;
import framework.CredentialsManager;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class AuthenticationSteps {
    private static final CredentialsManager credentialManager = CredentialsManager.getInstance();
    private static final APIManager apiManager = APIManager.getInstance();
    private static Response response;

    @Given("I send a POST request with a valid username and password")
    public void getUserCredentials() {
//        System.out.println(credentialManager.getUserName("admin"));
//        System.out.println(credentialManager.getPassword("admin"));
//        Map<String, Object> jsonAsMap = new HashMap<>();
//        jsonAsMap.put("username", credentialManager.getUserName("admin"));
//        jsonAsMap.put("password", credentialManager.getPassword("admin"));
//        System.out.println(jsonAsMap);
        String credentials = "{\"username\":\"" + credentialManager.getUserName("admin") + "\",\"password\":\"" + credentialManager.getPassword("admin") + "\"}";
        response = apiManager.post(credentialManager.getTokenEndPoint(), ContentType.JSON, credentials);
    }

    @Then("I get the JWT Token in a JSON response")
    public void getJWTResponse() {
        Assert.assertEquals(response.getStatusCode(), 200, "Correct status code is not returned");
        Assert.assertTrue(response.getStatusLine().contains("200 OK"), "Correct status code and message is not returned");
        Assert.assertNull(response.jsonPath().getString("error"), "Error Message was returned");
        Assert.assertEquals(response.jsonPath().getString("token_type"), "Bearer", "Correct token type is not returned");
        Assert.assertTrue(response.getBody().asString().contains("jwt_token"), "Correct response body is not returned");
    }
}
