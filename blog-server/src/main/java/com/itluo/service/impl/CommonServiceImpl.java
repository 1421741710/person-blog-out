package com.itluo.service.impl;

import com.itluo.properties.TenOssProperties;
import com.itluo.service.CommonService;
import com.qcloud.cos.COSClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;

/**
 * @author Administrator
 */
@Service
@Slf4j
public class CommonServiceImpl implements CommonService {

    @Autowired
    private COSClient cosClient;

    @Autowired
    private TenOssProperties tenOssProperties;


    /**
     * 上传文件
     * @param file
     * @param objectNames
     * @return
     */
    @Override
    public String uploads(File file, String objectNames) {
        cosClient.putObject(tenOssProperties.getBucket(),objectNames,file);
        StringBuilder stringBuilder = new StringBuilder("https://");
        stringBuilder.append(tenOssProperties.getBucket())
                .append(".")
                .append(tenOssProperties.getEndpoint())
                .append("/")
                .append(objectNames);
        log.info("文件上传:{}",stringBuilder.toString());
        return stringBuilder.toString();
    }
}
