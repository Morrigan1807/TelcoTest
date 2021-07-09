package util;

import model.SqlConnection;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class DataBaseUtil {

    private static final String INSERT_FILEINFO = "INSERT INTO FILEINFO(filename, time_of_creation) VALUES (?, ?)";
    private static final String SELECT_ALL_FILEINFO = "SELECT filename, time_of_creation FROM FILEINFO";

    public static void writeFilenameInDataBase(SqlConnection sqlConnection, String filename) {
        try (Connection connection = sqlConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT_FILEINFO)) {
            statement.setString(1, filename);
            statement.setTimestamp(2, Timestamp.valueOf(LocalDateTime.now()));

            statement.executeUpdate();
        } catch (SQLException | ClassNotFoundException exception) {
            exception.printStackTrace();
        }
    }

    public static List<String> getAllFromDataBase(SqlConnection sqlConnection) {
        List<String> allData = new ArrayList<>();

        try (Connection connection = sqlConnection.getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet rs = statement.executeQuery(SELECT_ALL_FILEINFO);

            while (rs.next()) {
                String filename = rs.getString("filename");
                Timestamp timeOfCreation = rs.getTimestamp("time_of_creation");

                allData.add(String.format("file: %s\t||\tdate: %s.", filename, timeOfCreation));
            }
        } catch (SQLException | ClassNotFoundException exception) {
            exception.printStackTrace();
        }

        return allData;
    }
}