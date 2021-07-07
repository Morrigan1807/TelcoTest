package util;

import model.SqlConnection;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class DataBaseUtil {

    public static void writeFilenameInDataBase(SqlConnection sqlConnection, String filename) {
        List<String> allData = new ArrayList<>();

        try (Connection connection = sqlConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement("INSERT INTO FILEINFO VALUES (?, ?)")) {
            statement.setString(1, filename);
            statement.setTimestamp(2, Timestamp.valueOf(LocalDateTime.now()));

            statement.executeUpdate();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public static List<String> getAllFromDataBase(SqlConnection sqlConnection) {
        List<String> allData = new ArrayList<>();

        try (Connection connection = sqlConnection.getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet rs = statement.executeQuery("SELECT filename, time_of_creation FROM FILEINFO");

            while (rs.next()) {
                String filename = rs.getString("filename");
                Timestamp timeOfCreation = rs.getTimestamp("time_of_creation");

                allData.add(String.format("file:%s\t||\t date:%s.", filename, timeOfCreation));
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        return allData;
    }
}