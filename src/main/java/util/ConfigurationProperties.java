package util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigurationProperties {

    private static Properties properties;
    private static String propertyFile;

    protected static void setProperties() {
        try (FileInputStream fileInputStream = new FileInputStream(propertyFile)) {
            properties = new Properties();
            properties.load(fileInputStream);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public static String getProperty(String key) {
        return properties.getProperty(key);
    }

    public ConfigurationProperties(String propertyPath) {
        propertyFile = propertyPath;
        setProperties(); //TODO fix
    }
}