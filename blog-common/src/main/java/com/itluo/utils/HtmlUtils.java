package com.itluo.utils;

import com.itluo.constant.MessageConstant;
import com.itluo.constant.StatusConstant;
import com.itluo.exception.BaseException;
import com.itluo.exception.EmailAlreadyExistsException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

/**
 * @author Administrator
 */
@Slf4j
public class HtmlUtils {

    /**
     * 读取邮件模板
     * @param templatePath
     * @return
     */
    public static String loadAndModifyVerificationTemplate(String templatePath, String code,Long type) {
        try {
            String typeName = null;
            if (type.equals(StatusConstant.ENABLE)) {
                typeName = "注册";
            }else if (type.equals(StatusConstant.DISABLE)){
                typeName = "修改邮箱";
            }else if (type.equals(StatusConstant.THREE)){
                typeName = "修改密码";
            }else{
                throw new EmailAlreadyExistsException(MessageConstant.UNKNOWN_ERROR);
            }
            // 读取HTML文件内容
            Resource resource = new ClassPathResource(templatePath);
            InputStream inputStream = resource.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));

            StringBuilder templateContent = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                templateContent.append(line).append("\n");
            }

            // 替换占位符为实际邮箱和验证码
            String modifiedTemplate = templateContent.toString().replace("{code}", code);
            modifiedTemplate = modifiedTemplate.replace("{typeName}", typeName);

            return modifiedTemplate;
        } catch (IOException e) {
            log.error("Failed to load and modify HTML template", e);
            throw new BaseException("邮件发送失败!");
        }
    }


}
