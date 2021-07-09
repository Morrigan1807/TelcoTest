import model.SqlConnection;
import util.ConfigurationProperties;
import util.DataBaseUtil;
import util.SftpUtil;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        new ConfigurationProperties(args[0]);
        SqlConnection sqlConnection = SqlConnection.builder()
                .user(ConfigurationProperties.getProperty("sql_user"))
                .password(ConfigurationProperties.getProperty("sql_password"))
                .databaseName(ConfigurationProperties.getProperty("sql_database"))
                .build();

        List<String> fileNames = SftpUtil.downloadAndGetAllFileNames(ConfigurationProperties.getProperty("sftp_remote_dir"),
                ConfigurationProperties.getProperty("local_dir"));
        fileNames.forEach((elem) -> DataBaseUtil.writeFilenameInDataBase(sqlConnection, elem));

        DataBaseUtil.getAllFromDataBase(sqlConnection).forEach(System.out::println);
    }
}