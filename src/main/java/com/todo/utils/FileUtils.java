package com.todo.utils;

import com.todo.utils.dataReader.PropertyReader;
import com.todo.utils.logs.LogsManager;

import java.io.File;
import java.io.IOException;

import static org.apache.commons.io.FileUtils.*;

public class FileUtils {

    private static final String USER_DIR = PropertyReader.getProperty("user.dir") + File.separator;

    private FileUtils() {
        // Prevent instantiation
    }

    // Renaming a file
    public static void renameFile(String oldName, String newName) {
        try {
            // Get the target file and its directory
            var targetFile = new File(oldName);
            String targetDirectory = targetFile.getParentFile().getAbsolutePath();
            // Create the new file object with the desired name
            File newFile = new File(targetDirectory + File.separator + newName);
            // Rename the file by copying and deleting the original
            if (!targetFile.getPath().equals(newFile.getPath())) {
                copyFile(targetFile, newFile);
                deleteQuietly(targetFile);
                LogsManager.info("Target File Path: \"" + oldName + "\", file was renamed to \"" + newName + "\".");
            } else {
                LogsManager.info(("Target File Path: \"" + oldName + "\", already has the desired name \"" + newName + "\"."));
            }
        } catch (IOException e) {
            LogsManager.error(e.getMessage());
        }
    }

    // Creating Directory
    public static void createDirectory(String path) {
        try {
            File file = new File(USER_DIR + path);
            if (!file.exists()) {
                file.mkdirs();
                LogsManager.info("Directory created: " + path);
            }
        } catch (Exception e) {
            LogsManager.error("Failed to create directory: " + path, e.getMessage());
        }
    }

    // Force delete file
    public static void forceFileDelete(File file) {
        try {
            forceDelete(file);
            LogsManager.info("File deleted: " + file.getAbsolutePath());
        } catch (IOException e) {
            LogsManager.error("Failed to delete file: " + file.getAbsolutePath(), e.getMessage());
        }
    }

    // Cleaning Directory
    public static void cleanDirectory(File file) {
        try {
            deleteQuietly(file);
        } catch (Exception e) {
            LogsManager.error("Failed to clean directory: " + file.getAbsolutePath(), e.getMessage());
        }
    }

    // Check if the file exists
    public static boolean isFileExists(String fileName) {
        String filePath = USER_DIR + "/src/test/resources/downloads/";
        File file = new File(filePath + fileName);
        return file.exists();
    }
}
