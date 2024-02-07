package com.itluo.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itluo.constant.MessageConstant;
import com.itluo.constant.RoleConstant;
import com.itluo.constant.StatusConstant;
import com.itluo.context.BaseContext;
import com.itluo.dto.AdminLoginDTO;
import com.itluo.dto.AdminPageQueryDTO;
import com.itluo.dto.AdminUpPwdDTO;
import com.itluo.entity.Admin;
import com.itluo.entity.Process;
import com.itluo.exception.AccountLockedException;
import com.itluo.exception.AccountNotFoundException;
import com.itluo.exception.PasswordErrorException;
import com.itluo.mapper.AdminMapper;
import com.itluo.mapper.ProcessMapper;
import com.itluo.result.PageResult;
import com.itluo.service.AdminService;
import com.itluo.utils.IpUtils;
import com.itluo.utils.LocalDateTimeUtils;
import com.itluo.websocket.WebSocketServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Administrator
 */
@Service
@Slf4j
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminMapper adminMapper;

    /**
     * 登录
     * @param adminLoginDTO
     * @param request
     * @return
     */
    @Override
    public Admin login(AdminLoginDTO adminLoginDTO, HttpServletRequest request) {
        String username = adminLoginDTO.getUsername();
        String password = adminLoginDTO.getPassword();
        Admin admin = adminMapper.findByAdminName(username);
        validate(password,admin);
        String ipAddr = IpUtils.getIpAddr(request);
        LocalDateTime time = LocalDateTimeUtils.getLocalDateTime();
        admin.setAdminIp(ipAddr);
        admin.setLoginTime(time);
        adminMapper.update(admin);
        return admin;
    }

    /**
     * 验证
     * @param password
     * @param admin
     */
    private void validate(String password,Admin admin){
        if (admin == null){
            throw new AccountNotFoundException(MessageConstant.ACCOUNT_NOT_FOUND);
        }
        if (admin.getStatus().equals(StatusConstant.ENABLE)){
            throw new AccountLockedException(MessageConstant.ACCOUNT_LOCKED);
        }
        password = DigestUtils.md5DigestAsHex(password.getBytes());
        if (!password.equals(admin.getPassword())){
            throw new PasswordErrorException(MessageConstant.PASSWORD_ERROR);
        }
    }

    /**
     * 修改密码
     * @param adminUpPwdDTO
     */
    @Override
    public void updatePwd(AdminUpPwdDTO adminUpPwdDTO) {
        String username = adminUpPwdDTO.getUsername();
        String password = adminUpPwdDTO.getPassword();
        String newPassword = adminUpPwdDTO.getNewPassword();
        Admin byAdminName = adminMapper.findByAdminName(username);
        validate(password,byAdminName);
        newPassword = DigestUtils.md5DigestAsHex(newPassword.getBytes());
        byAdminName.setPassword(newPassword);
        adminMapper.update(byAdminName);
    }

    /**
     * 分页查询
     * @param adminPageQueryDTO
     * @return
     */
    @Override
    public PageResult pageQuery(AdminPageQueryDTO adminPageQueryDTO) {
        PageHelper.startPage(adminPageQueryDTO.getPage(),adminPageQueryDTO.getPageSize());
        Page<Admin> page = adminMapper.pageQuery(adminPageQueryDTO);
        return new PageResult(page.getTotal(),page.getResult());
    }

    /**
     * 修改状态
     */
    @Override
    public void updateStatus(Long id) {
        Admin byAdminId = verify(id);
        Long status = byAdminId.getStatus().equals(StatusConstant.ENABLE) ? StatusConstant.DISABLE : StatusConstant.ENABLE;
        Admin admin = Admin.builder()
                .id(byAdminId.getId())
                .status(status)
                .build();
        adminMapper.update(admin);
    }

    private Admin verify(Long id){
        Admin byAdminId = adminMapper.findByAdminId(id);
        if (byAdminId == null){
            throw new AccountNotFoundException(MessageConstant.ACCOUNT_NOT_FOUND);
        }
        return byAdminId;
    }


    /**
     * 添加用户
     * @param adminLoginDTO
     */
    @Override
    public void insert(AdminLoginDTO adminLoginDTO) {
        String username = adminLoginDTO.getUsername();
        String password = adminLoginDTO.getPassword();
        Admin byAdminName = adminMapper.findByAdminName(username);
        if (byAdminName != null){
            throw new AccountNotFoundException(MessageConstant.ACCOUNT_NOT_FOUND_USER);
        }
        password = DigestUtils.md5DigestAsHex(password.getBytes());
        Admin admin = Admin.builder()
                .username(username)
                .password(password)
                .role(StatusConstant.DISABLE)
                .status(StatusConstant.DISABLE)
                .build();
        adminMapper.insert(admin);
    }

    /**
     * 修改权限
     * @param id
     */
    @Override
    public void updateRole(Long id) {
        Admin verify = verify(id);
        Long role = verify.getRole().equals(StatusConstant.ENABLE) ? StatusConstant.DISABLE : StatusConstant.ENABLE;
        Admin admin = Admin.builder()
                .id(id)
                .role(role)
                .build();
        adminMapper.update(admin);
    }


}
