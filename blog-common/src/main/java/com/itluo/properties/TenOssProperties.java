package com.itluo.properties;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author Administrator
 */
@Component
@ConfigurationProperties(prefix = "luo.tencentoss")
@Data
public class TenOssProperties {

    private String endpoint;
    private String bucket;
    private String region;
    private String secretId;
    private String secretKey;
}
