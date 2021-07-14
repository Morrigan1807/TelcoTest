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
            if (args.length == 0) {
                throw new IndexOutOfBoundsException("Command line argument not found.");
            }
            ValidationUtil.validateAbsolutePath(args[0]);
            ConfigurationProperties.setProperties(args[0]);

            log.info("Trying to download files from the specified server.");
            List<String> fileNames = SftpUtil.downloadAndGetAllFileNames(ConfigurationProperties.getProperty(SFTP_REMOTE_DIR_PROPERTY),
                    ConfigurationProperties.getProperty(LOCAL_DIR_PROPERTY));
            log.info("Trying to write filenames in DB.");
            fileNames.forEach(DataBaseUtil::writeFilenameInDataBase);
            log.info("Trying to read data from DB.");
            DataBaseUtil.getAllFromDataBase().forEach(System.out::println);
        } catch (IOException | IndexOutOfBoundsException exception) {
            log.error(exception.getMessage());
        }
    }
}