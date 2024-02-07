package com.itluo.service;

import com.itluo.dto.EmailVerificationPageQueryDTO;
import com.itluo.result.PageResult;

/**
 * @author Administrator
 */
public interface EmailVerificationService {

    /**
     * 判断邮箱是否过于频繁请求验证码
     * @param mailbox
     * @return
     */
    boolean findByEmail(String mailbox);

    /**
     * 分页查询
     * @param emailVerificationPageQueryDTO
     * @return
     */
    PageResult pageQuery(EmailVerificationPageQueryDTO emailVerificationPageQueryDTO);
}
