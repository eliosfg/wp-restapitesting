package com.jalasoft.wordpress.steps;

import framework.CredentialsManager;
import io.cucumber.java.en.Given;

public class PostsSteps {
    private static final CredentialsManager credentialsManager = CredentialsManager.getInstance();

    public PostsSteps()  {}

    @Given("I am a test")
    public void test() {
        System.out.println(credentialsManager.getBaseURL());
        System.out.println(credentialsManager.getBasePath());
        System.out.println(credentialsManager.getEnvId());
        System.out.println(credentialsManager.getTokenEndPoint());
        System.out.println(credentialsManager.getPostsEndPoint());
        System.out.println(credentialsManager.getPostsByIdEndpoint());
        System.out.println(credentialsManager.getUserName("admin"));
        System.out.println(credentialsManager.getPassword("admin"));
    }
}
