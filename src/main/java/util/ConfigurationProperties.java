package util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigurationProperties {

    protected static FileInputStream fileInputStream;
    protected static Properties PROPERTIES;
    public static String propertyFile;

    protected static void setProperties() {
        try {
            fileInputStream = new FileInputStream(propertyFile);
            PROPERTIES = new Properties();
            PROPERTIES.load(fileInputStream);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fileInputStream != null)
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
    }

    public static String getProperty(String key) {
        return PROPERTIES.getProperty(key);
    }

    public ConfigurationProperties(String propertyFilePath) {
        propertyFile = propertyFilePath;
        setProperties();
    }
}
