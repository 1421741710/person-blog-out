package com.itluo.service;

import javax.servlet.http.HttpSession;

/**
 * @author Administrator
 */
public interface CaptchaService {

    /**
     * 获取验证码
     * @param session
     * @return
     */
    byte[] generateCaptcha(HttpSession session);
}
