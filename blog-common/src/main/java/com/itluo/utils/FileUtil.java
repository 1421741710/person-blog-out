package com.itluo.utils;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author Administrator
 */
public class FileUtil {

    public static File convertAndDeleteOnExit(MultipartFile multipartFile) throws IOException {
        // 创建一个临时文件
        File tempFile = File.createTempFile("temp-", null);
        try (InputStream inputStream = multipartFile.getInputStream();
             FileOutputStream fileOutputStream = new FileOutputStream(tempFile)) {

            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                fileOutputStream.write(buffer, 0, bytesRead);
            }

            // 添加退出时删除文件的钩子
            tempFile.deleteOnExit();

            return tempFile;
        }
    }
}
