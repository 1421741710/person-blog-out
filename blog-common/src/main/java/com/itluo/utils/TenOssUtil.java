package com.itluo.utils;

import com.qcloud.cos.COSClient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.ByteArrayInputStream;

/**
 * 腾讯oss
 * @author Administrator
 */
@Data
@AllArgsConstructor
@Slf4j
public class TenOssUtil {
    private String bucket;
    private String region;
    private String secretId;
    private String secretKey;

    @Autowired
    private COSClient cosClient;

    public String upload(byte[] bytes, String objectName){
        cosClient.putObject(bucket,objectName, String.valueOf(new ByteArrayInputStream(bytes)));
        StringBuilder stringBuilder = new StringBuilder("https://");
        stringBuilder.append(bucket)
                .append(".")
                .append("cos.ap-guangzhou.myqcloud.com")
                .append(objectName);
        log.info("文件上传:{}",stringBuilder.toString());
        return stringBuilder.toString();
    }
}
