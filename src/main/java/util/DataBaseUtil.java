package util;

import lombok.extern.log4j.Log4j2;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Log4j2
public class DataBaseUtil {

    private static final String INSERT_FILEINFO = "INSERT INTO FILEINFO(filename, time_of_creation) VALUES (?, ?)";
    private static final String SELECT_ALL_FILEINFO = "SELECT filename, time_of_creation FROM FILEINFO";

    private DataBaseUtil() {

    }

    public static void writeFilenameInDataBase(String filename) {
        try (Connection connection = SqlConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT_FILEINFO)) {
            log.info("Connection to DB successful.");
            statement.setString(1, filename);
            statement.setTimestamp(2, Timestamp.valueOf(LocalDateTime.now()));

            statement.executeUpdate();
            log.info("Query executed successfully.");
        } catch (SQLException | ClassNotFoundException exception) {
            log.error(exception.getStackTrace());
        }
    }

    public static List<String> getAllFromDataBase() {
        List<String> allData = new ArrayList<>();

        try (Connection connection = SqlConnection.getConnection();
             Statement statement = connection.createStatement()) {
            log.info("Connection to DB successful.");

            ResultSet rs = statement.executeQuery(SELECT_ALL_FILEINFO);
            while (rs.next()) {
                String filename = rs.getString("filename");
                Timestamp timeOfCreation = rs.getTimestamp("time_of_creation");

                allData.add(String.format("file: %s\t||\tdate: %s.", filename, timeOfCreation));
            }
            log.info("Query executed successfully.");
        } catch (SQLException | ClassNotFoundException exception) {
            log.error(exception.getStackTrace());
        }

        return allData;
    }
}