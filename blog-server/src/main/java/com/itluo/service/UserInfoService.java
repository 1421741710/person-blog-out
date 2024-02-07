package com.itluo.service;

import com.itluo.dto.UserInfoPageQueryDTO;
import com.itluo.dto.UserInfoUpDTO;
import com.itluo.result.PageResult;
import com.itluo.vo.UserInfoVO;

/**
 * @author Administrator
 */
public interface UserInfoService {

    /**
     * 分页查询
     * @param userInfoPageQueryDTO
     * @return
     */
    PageResult pageQuery(UserInfoPageQueryDTO userInfoPageQueryDTO);

    /**
     * 修改用户信息
     * @param userInfoUpDTO
     */
    void update(UserInfoUpDTO userInfoUpDTO);

    /**
     * 获取用户信息
     * @return
     */
    UserInfoVO findByUserInfo();

    /**
     * 用户修改用户信息
     * @param userInfoUpDTO
     */
    void updateUserInfo(UserInfoUpDTO userInfoUpDTO);
}
