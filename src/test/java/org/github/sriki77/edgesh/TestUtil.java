package org.github.sriki77.edgesh;

import org.apache.commons.io.FileUtils;
import org.github.sriki77.edgesh.data.ShellContext;

import java.io.File;
import java.io.IOException;

public final class TestUtil {
    private static File tempDirectory;

    private TestUtil() {
    }

    public static final ShellContext buildContext() {
        return new ShellContext("https://api.enterprise.apigee.com/v1/", "sseshadri@apigee.com", "a999999=A");
    }

    public static File testDataDirectory() {
        createTempDirIfNeeded();
        return tempDirectory;
    }

    private static void createTempDirIfNeeded() {
        if (tempDirectory == null) {
            try {
                setTempDirectory();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private static void setTempDirectory() throws IOException {
        final String tempDirPath = System.getProperty("test.temp.directory", "mr-test-temp-data");
        File tempFolder = null;
        if (tempDirPath == null) {
            tempFolder = FileUtils.getTempDirectory();
        } else {
            tempFolder = new File(tempDirPath);
            FileUtils.forceDeleteOnExit(tempFolder);
        }
        tempFolder.mkdirs();
        tempDirectory = tempFolder;
    }

    public static void deleteTestDataDirectory() {
        FileUtils.deleteQuietly(tempDirectory);
        tempDirectory = null;
    }
}
