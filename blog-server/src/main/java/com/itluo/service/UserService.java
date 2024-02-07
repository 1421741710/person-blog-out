package com.itluo.service;

import com.itluo.dto.*;
import com.itluo.entity.User;
import com.itluo.result.PageResult;
import com.itluo.vo.UserSelectVO;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Administrator
 */
public interface UserService {
    /**
     * 分页查询
     * @param userPageQueryDTO
     * @return
     */
    PageResult pageQuery(UserPageQueryDTO userPageQueryDTO);

    /**
     * 修改信息
     * @param userUpDTO
     */
    void update(UserUpDTO userUpDTO);

    /**
     * 修改状态
     * @param id
     */
    void updateStatus(Long id);

    /**
     * 登录
     * @param userLoginDTO
     * @param request
     * @return
     */
    UserSelectVO login(UserLoginDTO userLoginDTO, HttpServletRequest request);

    /**
     * 修改密码
     * @param userUpPwdDTO
     */
    void updatePwd(UserUpPwdDTO userUpPwdDTO);

    /**
     * 注册
     * @param userRegisterDTO
     */
    void register(UserRegisterDTO userRegisterDTO);

    /**
     * 修改邮箱
     * @param userMailboxDTO
     */
    void updateMailbox(UserMailboxDTO userMailboxDTO);
}
