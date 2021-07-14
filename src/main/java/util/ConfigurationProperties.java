package util;

import lombok.extern.log4j.Log4j2;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

@Log4j2
public class ConfigurationProperties {

    private static Properties properties;

    private ConfigurationProperties() {

    }

    public static void setProperties(String propertyPath) {
        try (FileInputStream fileInputStream = new FileInputStream(propertyPath)) {
            properties = new Properties();
            properties.load(fileInputStream);
        } catch (IOException exception) {
            log.error(exception.getStackTrace());
        }
    }

    public static String getProperty(String key) {
        return properties.getProperty(key);
    }
}