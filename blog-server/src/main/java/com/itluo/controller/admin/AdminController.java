package com.itluo.controller.admin;

import com.github.pagehelper.Page;
import com.itluo.annotation.CustomPermission;
import com.itluo.annotation.LogAnnotation;
import com.itluo.constant.JwtClaimsConstant;
import com.itluo.constant.MessageConstant;
import com.itluo.constant.RoleConstant;
import com.itluo.context.BaseContext;
import com.itluo.dto.AdminLoginDTO;
import com.itluo.dto.AdminPageQueryDTO;
import com.itluo.dto.AdminUpPwdDTO;
import com.itluo.entity.Admin;
import com.itluo.enumeration.RoleType;
import com.itluo.properties.JwtProperties;
import com.itluo.result.PageResult;
import com.itluo.result.Result;
import com.itluo.service.AdminService;
import com.itluo.utils.JwtUtil;
import com.itluo.vo.AdminLoginVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Administrator
 */
@RestController
@RequestMapping("/admin")
@Slf4j
@Api(tags = "管理员相关接口")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private JwtProperties jwtProperties;

    /**
     * 管理员登录
     * @param adminLoginDTO
     * @param request
     * @return
     */
    @PostMapping("/login")
    @ApiOperation("登录")
    public Result login(@Valid @RequestBody AdminLoginDTO adminLoginDTO, HttpServletRequest request){
        Admin admin = adminService.login(adminLoginDTO,request);
        Map<String,Object> claim = new HashMap<>(3);
        claim.put(JwtClaimsConstant.EMP_ID,admin.getId());
        claim.put(JwtClaimsConstant.EMP_ROLE,admin.getRole());
        String token = JwtUtil.createJWT(
                jwtProperties.getAdminSecretKey(),
                jwtProperties.getAdminTtl(),
                claim);
        AdminLoginVO adminLoginVO = AdminLoginVO.builder()
                .id(admin.getId())
                .username(admin.getUsername())
                .token(token)
                .role(admin.getRole())
                .build();
        return Result.success(MessageConstant.LOGIN,adminLoginVO);
    }

    /**
     * 修改密码
     * @param adminUpPwdDTO
     * @return
     */
    @PatchMapping("/updatePwd")
    @ApiOperation("修改密码")
    @LogAnnotation(module = "管理员",operator = "修改密码")
    public Result<String> updatePwd(@Valid @RequestBody AdminUpPwdDTO adminUpPwdDTO){
        adminService.updatePwd(adminUpPwdDTO);
        return Result.success(MessageConstant.OPERATE);
    }

    /**
     * 分页查询
     * @param adminPageQueryDTO
     * @return
     */
    @GetMapping("/findByAdminPageQuery")
    @ApiOperation("分页查询")
    @CustomPermission(RoleType.SUPER_ADMINISTRATOR)
    public Result<PageResult> pageQuery(AdminPageQueryDTO adminPageQueryDTO){
        PageResult pageResult = adminService.pageQuery(adminPageQueryDTO);
        return Result.success(MessageConstant.OPERATE,pageResult);
    }

    /**
     * 修改状态
     * @param id
     * @return
     */
    @PutMapping("/updateStatus/{id}")
    @ApiOperation("修改状态")
    @LogAnnotation(module = "管理员",operator = "修改状态")
    @CustomPermission(RoleType.SUPER_ADMINISTRATOR)
    public Result<String> updateStatus(@PathVariable Long id){
        adminService.updateStatus(id);
        return Result.success(MessageConstant.OPERATE);
    }

    /**
     * 修改权限
     * @param id
     * @return
     */
    @PutMapping("/updateRole/{id}")
    @ApiOperation("修改权限")
    @LogAnnotation(module = "管理员",operator = "修改权限")
    @CustomPermission(RoleType.SUPER_ADMINISTRATOR)
    public Result<String> updateRole(@PathVariable Long id){
        adminService.updateRole(id);
        return Result.success(MessageConstant.OPERATE);
    }



    /**
     * 修改普通管理员密码
     * @param adminUpPwdDTO
     * @return
     */
    @PatchMapping("/updateAdminPwd")
    @ApiOperation("修改普通管理员密码")
    @LogAnnotation(module = "管理员",operator = "修改普通管理员密码")
    @CustomPermission(RoleType.SUPER_ADMINISTRATOR)
    public Result<String> updateAdminPwd(@RequestBody AdminUpPwdDTO adminUpPwdDTO){
        adminService.updatePwd(adminUpPwdDTO);
        return Result.success(MessageConstant.OPERATE);
    }

    /**
     * 添加管理员
     * @param adminLoginDTO
     * @return
     */
    @PostMapping("/insert")
    @ApiOperation("添加管理员")
    @LogAnnotation(module = "管理员",operator = "添加管理员")
    @CustomPermission(RoleType.SUPER_ADMINISTRATOR)
    public Result<String> insert(@Valid @RequestBody AdminLoginDTO adminLoginDTO){
        adminService.insert(adminLoginDTO);
        return Result.success(MessageConstant.OPERATE);
    }

}
