package util;

import com.jcraft.jsch.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class SftpUtil {

    public static List<String> downloadAndGetAllFileNames(String pathFromDirectory, String pathToDirectory) {
        List<String> fileNames = new ArrayList<>();
        JSch jsch = new JSch();
        try {
            Session session = jsch.getSession(ConfigurationProperties.getProperty("sftp_user"),
                    ConfigurationProperties.getProperty("sftp_host"),
                    Integer.parseInt(ConfigurationProperties.getProperty("sftp_port")));
            session.setConfig("StrictHostKeyChecking", "no");
            session.setPassword(ConfigurationProperties.getProperty("sftp_password"));
            session.connect();

            Channel channel = session.openChannel("sftp");
            channel.connect();
            ChannelSftp sftpChannel = (ChannelSftp) channel;
            Vector<ChannelSftp.LsEntry> fileList = sftpChannel.ls(pathFromDirectory);

            sftpChannel.cd(pathFromDirectory);
            for (ChannelSftp.LsEntry oListItem : fileList) {
                if (!oListItem.getAttrs().isDir()) {
                    fileNames.add(String.format("%s/%s", pathFromDirectory, oListItem.getFilename()));
                    sftpChannel.get(oListItem.getFilename(), String.format("%s/%s", pathToDirectory, oListItem.getFilename()));
                }
            }
            sftpChannel.exit();
            session.disconnect();
        } catch (JSchException | SftpException e) {
            e.printStackTrace();
        }

        return fileNames;
    }
}