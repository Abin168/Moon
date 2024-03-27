package com.moon.poi.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

@Slf4j
public class FileUtil {

    /**
     * copy file
     *
     * @param srcPath
     * @param destPath
     */
    public static void copyFile(String srcPath, String destPath) {
        try (InputStream in = new BufferedInputStream(Files.newInputStream(Paths.get(srcPath)));
             OutputStream out = new BufferedOutputStream(Files.newOutputStream(Paths.get(destPath)))) {
            byte[] buffer = new byte[1024];
            int length;
            while ((length = in.read(buffer)) > 0) {
                out.write(buffer, 0, length);
            }
            out.flush();
        } catch (IOException e) {
            log.error("FileUtil copyFile error srcPath={}, destPath={}", srcPath, destPath, e);
        }
    }

    /**
     * delete file
     *
     * @param fileName
     * @return
     */
    public static boolean deleteFile(String fileName) {
        if (StringUtils.isEmpty(fileName)) {
            return false;
        }
        File file = new File(fileName);
        if (file.isFile() && file.exists()) {
            return file.delete();
        } else {
            return false;
        }
    }
}
