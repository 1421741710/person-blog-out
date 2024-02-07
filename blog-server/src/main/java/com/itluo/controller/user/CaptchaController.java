package com.itluo.controller.user;

import com.itluo.constant.CaptchaConstants;
import com.itluo.result.CaptchaResult;
import com.itluo.service.CaptchaService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;


/**
 * @author Administrator
 */
@RestController
@RequestMapping("/captcha")
@Api(tags = "登录验证码相关接口")
@Slf4j
public class CaptchaController {

    @Autowired
    private CaptchaService captchaService;

    /**
     * 生成验证码
     * @return
     */
    @GetMapping("/captcha.jpg")
    @ApiOperation("生成验证码")
    public ResponseEntity<byte[]> getCaptcha(HttpSession session) {
        byte[] captcha = captchaService.generateCaptcha(session);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);

        return ResponseEntity.ok()
                .headers(headers)
                .body(captcha);
    }

}
