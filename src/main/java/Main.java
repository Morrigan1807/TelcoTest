import model.SqlConnection;
import util.ConfigurationProperties;

public class Main {

    public static void main(String[] args) {
        new ConfigurationProperties(args[0]);
        SqlConnection sqlConnection = SqlConnection.builder()
                .user(ConfigurationProperties.getProperty("sql_user"))
                .password(ConfigurationProperties.getProperty("sql_password"))
                .databaseName(ConfigurationProperties.getProperty("sql_database"))
                .build();

    }
}
