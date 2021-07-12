package model;

import lombok.Builder;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

@Builder
public class SqlConnection {

    public static Connection getConnection() throws SQLException, ClassNotFoundException {
        Properties properties = new Properties();
        try {
            InputStream input = ClassLoader.getSystemClassLoader().getResourceAsStream("sql.properties");
            properties.load(input);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        Class.forName(properties.getProperty("driver"));
        return DriverManager.getConnection(properties.getProperty("database_url"), properties.getProperty("user"),
                properties.getProperty("password"));
    }
}