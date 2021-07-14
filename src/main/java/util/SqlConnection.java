package util;

import lombok.Builder;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import static util.Constant.*;

@Builder
@Log4j2
public class SqlConnection {

    private SqlConnection() {

    }

    public static Connection getConnection() throws SQLException, ClassNotFoundException {
        Properties properties = new Properties();
        try (InputStream input = ClassLoader.getSystemClassLoader().getResourceAsStream(SQL_PROPERTIES)) {
            properties.load(input);
        } catch (IOException exception) {
            log.error(exception.getStackTrace());
        }
        Class.forName(properties.getProperty(SQL_DRIVER_PROPERTY));
        return DriverManager.getConnection(properties.getProperty(SQL_URL_PROPERTY), properties.getProperty(SQL_USER_PROPERTY),
                properties.getProperty(SQL_PASSWORD_PROPERTY));
    }
}