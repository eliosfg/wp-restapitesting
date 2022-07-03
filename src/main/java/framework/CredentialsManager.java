package framework;

import utils.LoggerManager;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class CredentialsManager {
    private Properties properties;
    private static final LoggerManager log = LoggerManager.getInstance();
    private static final String envFilePath = System.getProperty("user.dir") + File.separator + "environments.properties";
    private static final String usersFilePath = System.getProperty("user.dir") + File.separator + "users.properties";
    private static final String apiFilePath = System.getProperty("user.dir") + File.separator + "api.properties";
    private static CredentialsManager instance;
    private String envId;

    private CredentialsManager() {
        initialize();
    }

    private void initialize() {
        log.info("Initializing Credentials Manager");
        String wpEnvironmentId = System.getProperty("envId");

        if ((wpEnvironmentId == null) || (wpEnvironmentId.isEmpty())) {
            envId = "QA01";
        } else {
            envId = wpEnvironmentId;
        }

        properties = new Properties();
        Properties envProperties = new Properties();
        Properties usersProperties = new Properties();
        Properties apiProperties = new Properties();

        try {
            envProperties.load(new FileInputStream(envFilePath));
            usersProperties.load(new FileInputStream(usersFilePath));
            apiProperties.load(new FileInputStream(apiFilePath));
        } catch (IOException e) {
            log.error("Error loading properties files");
        }

        properties.putAll(envProperties);
        properties.putAll(usersProperties);
        properties.putAll(apiProperties);
    }

    public static CredentialsManager getInstance() {
        if (instance == null) {
            instance = new CredentialsManager();
        }
        return instance;
    }

    public String getEnvironmentSettings(String setting) {
        return (String) getInstance().properties.get(setting);
    }

    public String getEnvId() {
        return envId;
    }

    public String getBaseURL() {
        return getEnvironmentSettings("envId.baseURL".replace("envId", getEnvId().toLowerCase()));
    }

    public String getBasePath() {
        return getEnvironmentSettings("api.basePath");
    }

    public String getTokenEndPoint() {
        return getEnvironmentSettings("api.endpoint.token");
    }

    public String getPostsEndPoint() {
        return getEnvironmentSettings("api.endpoint.posts");
    }

    public String getPostsByIdEndpoint() {
        return getEnvironmentSettings("api.endpoint.postsById");
    }

    public String getUserName(String userType) {
        return getEnvironmentSettings("userType.username".replace("userType", userType));
    }

    public String getPassword(String userType) {
        return getEnvironmentSettings("userType.password".replace("userType", userType));
    }
}