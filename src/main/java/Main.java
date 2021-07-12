import util.ConfigurationProperties;
import util.DataBaseUtil;
import util.SftpUtil;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        ConfigurationProperties.setProperties(args[0]);

        List<String> fileNames = SftpUtil.downloadAndGetAllFileNames(ConfigurationProperties.getProperty("sftp_remote_dir"),
                ConfigurationProperties.getProperty("local_dir"));
        fileNames.forEach(DataBaseUtil::writeFilenameInDataBase);

        DataBaseUtil.getAllFromDataBase().forEach(System.out::println);
    }
}