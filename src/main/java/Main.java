import lombok.extern.log4j.Log4j2;
import util.ConfigurationProperties;
import util.DataBaseUtil;
import util.SftpUtil;
import util.ValidationUtil;

import java.io.IOException;
import java.util.List;

import static util.Constant.LOCAL_DIR_PROPERTY;
import static util.Constant.SFTP_REMOTE_DIR_PROPERTY;

@Log4j2
public class Main {

    public static void main(String[] args) {
        try {
            ValidationUtil.validateAbsolutePath(args[0]);
            ConfigurationProperties.setProperties(args[0]);
           
            List<String> fileNames = SftpUtil.downloadAndGetAllFileNames(ConfigurationProperties.getProperty(SFTP_REMOTE_DIR_PROPERTY),
                    ConfigurationProperties.getProperty(LOCAL_DIR_PROPERTY));
            fileNames.forEach(DataBaseUtil::writeFilenameInDataBase);

            DataBaseUtil.getAllFromDataBase().forEach(System.out::println);
        } catch (IOException exception) {
            log.error(exception.getMessage());
        }
    }
}