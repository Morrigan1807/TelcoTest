package model;

import lombok.Builder;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Builder
public class SqlConnection {

    private static final String mySqlDriver = "org.gjt.mm.mysql.Driver";
    private final String databaseName;
    private final String user;
    private final String password;

    static {
        try {
            Class.forName(mySqlDriver);
        } catch (ClassNotFoundException exception) {
            exception.printStackTrace();
        }
    }

    public Connection getConnection() throws SQLException {
        String databaseUrl = String.format("jdbc:mysql://localhost/%s", databaseName);
        return DriverManager.getConnection(databaseUrl, user, password);
    }
}