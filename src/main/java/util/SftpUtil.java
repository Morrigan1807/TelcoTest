package util;

import com.jcraft.jsch.*;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import static util.Constant.*;

@Log4j2
public class SftpUtil {

    private SftpUtil() {

    }

    public static List<String> downloadAndGetAllFileNames(String pathFromDirectory, String pathToDirectory) {
        List<String> fileNames = new ArrayList<>();
        JSch jsch = new JSch();
        try {
            ValidationUtil.validatePartialPath(pathFromDirectory);
            ValidationUtil.validatePartialPath(pathToDirectory);
            ValidationUtil.validateHost(ConfigurationProperties.getProperty(SFTP_HOST_PROPERTY));
            ValidationUtil.validatePort(ConfigurationProperties.getProperty(SFTP_PORT_PROPERTY));

            log.info("Trying to connect to the server: ");
            log.info("host: " + ConfigurationProperties.getProperty(SFTP_HOST_PROPERTY));
            log.info("port: " + ConfigurationProperties.getProperty(SFTP_PORT_PROPERTY));
            log.info("user: " + ConfigurationProperties.getProperty(SFTP_USER_PROPERTY));
            log.info("password: " + ConfigurationProperties.getProperty(SFTP_PASSWORD_PROPERTY));

            Session session = jsch.getSession(ConfigurationProperties.getProperty(SFTP_USER_PROPERTY),
                    ConfigurationProperties.getProperty(SFTP_HOST_PROPERTY),
                    Integer.parseInt(ConfigurationProperties.getProperty(SFTP_PORT_PROPERTY)));
            session.setConfig("StrictHostKeyChecking", "no");
            session.setPassword(ConfigurationProperties.getProperty(SFTP_PASSWORD_PROPERTY));
            session.connect();

            Channel channel = session.openChannel("sftp");
            channel.connect();

            log.info("Connection successful. Trying to get files from: " + pathFromDirectory);
            ChannelSftp sftpChannel = (ChannelSftp) channel;
            Vector<ChannelSftp.LsEntry> fileList = sftpChannel.ls(pathFromDirectory);

            sftpChannel.cd(pathFromDirectory);
            log.info(String.format("Found %d files. Trying to download to local directory: %s", fileList.size(), pathToDirectory));
            for (ChannelSftp.LsEntry oListItem : fileList) {
                if (!oListItem.getAttrs().isDir()) {
                    fileNames.add(String.format("%s/%s", pathFromDirectory, oListItem.getFilename()));
                    sftpChannel.get(oListItem.getFilename(), String.format("%s/%s", pathToDirectory, oListItem.getFilename()));
                }
            }
            log.info("Download completed successfully.");
            sftpChannel.exit();
            session.disconnect();
            log.info("Connection successfully closed.");
        } catch (JSchException | SftpException | IOException exception) {
            log.error(exception.getMessage());
        }

        return fileNames;
    }
}