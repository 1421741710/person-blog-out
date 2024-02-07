package com.itluo.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itluo.constant.StatusConstant;
import com.itluo.dto.EmailVerificationPageQueryDTO;
import com.itluo.entity.EmailVerification;
import com.itluo.mapper.EmailVerificationMapper;
import com.itluo.result.PageResult;
import com.itluo.service.EmailVerificationService;
import com.itluo.utils.LocalDateTimeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Administrator
 */
@Service
@Slf4j
public class EmailVerificationServiceImpl implements EmailVerificationService {

    @Autowired
    private EmailVerificationMapper emailVerificationMapper;


    /***
     * 判断邮箱是否过于频繁请求验证码
     * @param mailbox
     * @return
     */
    @Override
    public boolean findByEmail(String mailbox) {
        // 可以发送的最大次数
        int maxRequestCount = 5;
        //时差1
        long oneJetLag = 2;
        //时差2
        long twoJetLag = 10;
        // 在数据库中查询 EmailVerification 记录
        EmailVerification emailVerifications = emailVerificationMapper.findByEmail(mailbox);

        // 为记录不存在的情况创建新的 EmailVerification 对象
        EmailVerification emailVerification = new EmailVerification();

        if (emailVerifications == null) {
            // 未找到记录，使用 StatusConstant.DISABLE 插入新记录
            emailVerification = populateEmailVerificationData(emailVerification, mailbox, StatusConstant.DISABLE);
            emailVerificationMapper.insert(emailVerification);
            return false;
        }

        //可以发送5次
        if (emailVerifications.getRequestCount() <= maxRequestCount){
            // 检查时差是否大于或等于 2
            boolean isTimeDifferenceGreaterThan = LocalDateTimeUtils.getLocalDateTime(emailVerifications.getLastRequestTime(), oneJetLag);
            if (isTimeDifferenceGreaterThan){
                emailVerification = populateEmailVerificationData(emailVerifications, mailbox, StatusConstant.DISABLE);
                emailVerificationMapper.update(emailVerification);
                return false;
            }
            return true;
        }

        // 检查时差是否大于或等于 10
        boolean isTimeDifferenceGreaterThan = LocalDateTimeUtils.getLocalDateTime(emailVerifications.getLastRequestTime(), twoJetLag);
        if (isTimeDifferenceGreaterThan) {
            emailVerificationMapper.delete(emailVerifications.getId());
            return false;
        }

        // 时差小于10分钟
        return true;
    }

    /**
     * 分页查询
     * @param emailVerificationPageQueryDTO
     * @return
     */
    @Override
    public PageResult pageQuery(EmailVerificationPageQueryDTO emailVerificationPageQueryDTO) {
        PageHelper.startPage(emailVerificationPageQueryDTO.getPage(),emailVerificationPageQueryDTO.getPageSize());
        Page<EmailVerification> page = emailVerificationMapper.pageQuery(emailVerificationPageQueryDTO);
        return new PageResult(page.getTotal(),page.getResult());
    }


    /**
     * 进行数据的填充
     * @param emailVerification
     * @param mailbox
     * @param status
     * @return
     */
    private EmailVerification populateEmailVerificationData(EmailVerification emailVerification,String mailbox,Long status){
        emailVerification.setEmail(mailbox);
        if (emailVerification.getRequestCount() == null){
            emailVerification.setRequestCount(StatusConstant.ZERO);
        }
        emailVerification.setRequestCount(emailVerification.getRequestCount() + 1);
        emailVerification.setLastRequestTime(LocalDateTimeUtils.getLocalDateTime());
        emailVerification.setStatus(status);
        return emailVerification;
    }
}
