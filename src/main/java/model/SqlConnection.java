package model;

import lombok.Builder;
import lombok.Getter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Builder
public class SqlConnection {

    private static final String mySqlDriver = "org.gjt.mm.mysql.Driver";

    static {
        try {
            Class.forName(mySqlDriver);
        } catch (ClassNotFoundException exception) {
            exception.printStackTrace();
        }
    }

    private final String databaseName;
    private final String user;
    private final String password;

    public Connection getConnection() throws SQLException {
        String databaseUrl = String.format("jdbc:mysql://localhost/%s?useSSL=false", databaseName);
        return DriverManager.getConnection(databaseUrl, user, password);
    }
}