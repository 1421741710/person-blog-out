package com.itluo.result;

import lombok.Data;

/**
 * @author Administrator
 */
@Data
public class CaptchaResult {

    private byte[] captchaImage;

    private String captchaText;

    public CaptchaResult(byte[] captchaImage, String captchaText) {
        this.captchaImage = captchaImage;
        this.captchaText = captchaText;
    }
}
