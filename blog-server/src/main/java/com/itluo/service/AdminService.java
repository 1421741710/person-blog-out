package com.itluo.service;

import com.itluo.dto.AdminLoginDTO;
import com.itluo.dto.AdminPageQueryDTO;
import com.itluo.dto.AdminUpPwdDTO;
import com.itluo.entity.Admin;
import com.itluo.result.PageResult;

import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Administrator
 */
public interface AdminService {

    /**
     * 登录
     * @param adminLoginDTO
     * @param request
     * @return
     */
    Admin login(AdminLoginDTO adminLoginDTO, HttpServletRequest request);

    /**
     * 修改密码
     * @param adminUpPwdDTO
     */
    void updatePwd(AdminUpPwdDTO adminUpPwdDTO);

    /**
     * 分页查询
     * @param adminPageQueryDTO
     * @return
     */
    PageResult pageQuery(AdminPageQueryDTO adminPageQueryDTO);

    /**
     * 修改状态
     * @param id
     */
    void updateStatus(Long id);

    /**
     * 添加用户
     * @param adminLoginDTO
     */
    void insert(AdminLoginDTO adminLoginDTO);

    /**
     * 修改权限
     * @param id
     */
    void updateRole(Long id);
}
