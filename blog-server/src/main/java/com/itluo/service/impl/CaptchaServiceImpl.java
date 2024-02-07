package com.itluo.service.impl;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.itluo.constant.CaptchaConstants;
import com.itluo.result.CaptchaResult;
import com.itluo.service.CaptchaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.servlet.http.HttpSession;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * @author Administrator
 */
@Service
@Slf4j
public class CaptchaServiceImpl implements CaptchaService {

    @Autowired
    private DefaultKaptcha producer;

    @Override
    public byte[] generateCaptcha(HttpSession session) {
        String text = producer.createText();

        // 将验证码文本存入session
        session.setAttribute(CaptchaConstants.CAPTCHA_SESSION_KEY, text);

        BufferedImage image = producer.createImage(text);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            ImageIO.write(image, "jpg", outputStream);
        } catch (IOException e) {
            throw new RuntimeException("Failed to write captcha image.", e);
        }

        return outputStream.toByteArray();
    }
}
