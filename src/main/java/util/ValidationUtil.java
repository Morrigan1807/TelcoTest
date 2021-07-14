package util;

import com.jcraft.jsch.SftpException;
import lombok.extern.log4j.Log4j2;

import java.io.File;
import java.io.IOException;

@Log4j2
public class ValidationUtil {

    private static final String ABSOLUTE_PATH_LINUX_REGEX = "(?i)^[a-z]:([\\\\/].+)+$";
    private static final String ABSOLUTE_PATH_WINDOWS_REGEX = "(?i)^[a-z]:([\\\\/][^/\\\\*\"?<>|:]+)+$";
    private static final String PARTIAL_PATH_WINDOWS_REGEX = "(?i)^(/[a-z0-9-_+]+)+/?";
    private static final String PARTIAL_PATH_LINUX_REGEX = "(?i)^(/[^/\\\\0]+)+/?";
    private static final String PORT_REGEX = "^([0-9]{1,4}|[1-5][0-9]{4}|6[0-4][0-9]{3}|65[0-4][0-9]{2}|655[0-2][0-9]|6553[0-5])$";
    private static final String IP_ADDRESS_REGEX = "^(([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])\\.){3}" +
            "([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])$";
    private static final String HOSTNAME_REGEX = "(?i)^(([a-z0-9]|[a-z0-9][a-z0-9\\-]*[a-z0-9])\\.)*" +
            "([a-z0-9]|[a-z0-9][a-z0-9\\-]*[a-z0-9])$";

    private ValidationUtil() {

    }

    public static void validateAbsolutePath(String filepath) throws IOException {
        if (!filepath.matches(ABSOLUTE_PATH_LINUX_REGEX) &&
                !filepath.matches(ABSOLUTE_PATH_WINDOWS_REGEX)) {
            throw new IOException(String.format("Incorrect filepath in: %s", filepath));
        }
        File file = new File(filepath);
        if (!file.exists()) {
            throw new IOException(String.format("File does not exist: %s", filepath));
        }
    }

    public static void validatePartialPath(String path) throws IOException {
        if (!path.matches(PARTIAL_PATH_WINDOWS_REGEX) &&
                !path.matches(PARTIAL_PATH_LINUX_REGEX)) {
            throw new IOException(String.format("Incorrect path in: %s", path));
        }
    }

    public static void validatePort(String port) throws NumberFormatException {
        if (!port.matches(PORT_REGEX)) {
            throw new NumberFormatException(String.format("Incorrect port in: %s", port));
        }
    }

    public static void validateHost(String host) throws SftpException {
        if (!host.matches(IP_ADDRESS_REGEX)
                && !host.matches(HOSTNAME_REGEX)) {
            throw new SftpException(1, String.format("Incorrect host in: %s", host));
        }
    }
}