package gov.di_ipv_core.utilities;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * reads the properties file configuration.properties
 */
public class ConfigurationReader {
    private static final Logger LOGGER = LoggerFactory.getLogger(ConfigurationReader.class);


    private static Properties properties;
    private static Properties configuration;
    private static String chartName = "";


    static {

        try {
            String path = "configuration.properties";
            FileInputStream input = new FileInputStream(path);
            properties = new Properties();
            properties.load(input);

            input.close();
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    public static void initConfig(){
        chartName = System.getProperty("env") == null ? "staging" : System.getProperty("env");
        String defaultPropertyFile = "properties/default.properties";
        String environmentPropertyFile = "properties/" + chartName + ".properties";
        Properties localProperties = new Properties();
        try {
            InputStream propertyFileInputStream = getClassPathResourceStream(defaultPropertyFile);
            loadProperties(localProperties, getClassPathResourceStream(defaultPropertyFile));
            propertyFileInputStream.close();
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
            throw new RuntimeException("Could not load default properties from defaultPropertyFile", e);
        }

        try {
            InputStream propertyFileInputStream = getClassPathResourceStream(environmentPropertyFile);
            loadProperties(localProperties, getClassPathResourceStream(environmentPropertyFile));
            propertyFileInputStream.close();
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
            throw new RuntimeException("Could not load environment properties from environmentPropertyFile", e);
        }

        configuration = localProperties;
    }

    private static void loadProperties(Properties props, InputStream inputStream) {
        try {
            props.load(inputStream);
        } catch (IOException ioexception) {
            LOGGER.error(ioexception.getMessage());
        }
    }

    public static InputStream getClassPathResourceStream(String classpathResourceLoc) {
        return Thread.currentThread().getContextClassLoader().getResourceAsStream(classpathResourceLoc);
    }


    public static String get(String keyName) {
        return properties.getProperty(keyName);
    }

    public static String getBrowser() {
        return System.getenv("BROWSER") != null ? System.getenv("BROWSER") : "chrome";
    }

    public static String getOrchestratorUrl() {
        return get("ORCHESTRATOR_STUB_URL");
    }

    public static String getIPVCoreStubUrl() {
        String orchestratorStubUrl = "https://user:needs@di-ipv-core-stub.london.cloudapps.digital/";
        if (orchestratorStubUrl == null) {
            throw new IllegalArgumentException("Environment variable IPV_CORE_STUB_URL is not set");
        }
        return orchestratorStubUrl;
    }

    public static String getSampleServiceStagingUrl() {
        return "https://di-auth-stub-relying-party-staging.london.cloudapps.digital/";
    }

    public static String getAuthCodeBucketName() {
        return get("AUTH_CODE_BUCKET_NAME");
    }

    public static String getAuthCodeKeyName() {
        return get("AUTH_CODE_KEY_NAME");
    }

    private static String getEnvironmentVariableOrError(String variable) {
        String value = System.getenv(variable);
        if (value == null) {
            throw new IllegalArgumentException(String.format("Environment variable %s is not set", variable));
        }
        return value;
    }

    public static String getSampleServiceIntegrationUrl() {
        String sampleServiceIntegrationUrl = System.getenv("SAMPLE_SERVICE_INTEGRATION_URL");
        if (sampleServiceIntegrationUrl == null) {
            throw new IllegalArgumentException("Environment variable SAMPLE_SERVICE_STAGING_URL is not set ");
        }
        return sampleServiceIntegrationUrl;
    }



    public static boolean noChromeSandbox() {
        return "true".equalsIgnoreCase(System.getenv("NO_CHROME_SANDBOX"));
    }
}
