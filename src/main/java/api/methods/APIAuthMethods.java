package api.methods;

import api.APIManager;
import framework.CredentialsManager;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import utils.LoggerManager;

public class APIAuthMethods {
    private static final LoggerManager log = LoggerManager.getInstance();
    private static final CredentialsManager credentialsManager = CredentialsManager.getInstance();
    private static final APIManager apiManager = APIManager.getInstance();

    public static String getJsonWebToken(String username, String password) {
        String credentials = "{\"username\":\"" + username + "\",\"password\":\"" + password + "\"}";
        return apiManager.post(credentialsManager.getTokenEndPoint(), ContentType.JSON, credentials).jsonPath().getString("jwt_token");
    }

    public static RequestSpecification setBearerToken(String jwtToken) {
        return RestAssured.given().header("Authorization", "Bearer " + jwtToken);
    }
}
