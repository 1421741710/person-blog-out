package com.itluo.config;


import com.itluo.properties.TenOssProperties;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.region.Region;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 配置类，用于创建TenOssProperties对象
 * @author Administrator
 */
@Configuration
@Slf4j
public class OssConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public COSClient uploadFile(TenOssProperties tenOssProperties){
        log.info("开始创建腾讯云文件上传工具类对象：{}",tenOssProperties);
        BasicCOSCredentials cred = new BasicCOSCredentials(tenOssProperties.getSecretId(),tenOssProperties.getSecretKey());
        ClientConfig config = new ClientConfig(new Region(tenOssProperties.getRegion()));
        return new COSClient(cred,config);
    }
}
