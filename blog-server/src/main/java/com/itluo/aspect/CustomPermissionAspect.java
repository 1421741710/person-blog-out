package com.itluo.aspect;

import com.itluo.annotation.CustomPermission;
import com.itluo.constant.MessageConstant;
import com.itluo.constant.RoleConstant;
import com.itluo.context.BaseContext;
import com.itluo.entity.Admin;
import com.itluo.enumeration.RoleType;
import com.itluo.exception.AccountNotFoundException;
import com.itluo.exception.InsufficientPermissionsException;
import com.itluo.mapper.AdminMapper;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @author Administrator
 */
@Component
@Aspect
@Slf4j
public class CustomPermissionAspect {

    @Autowired
    private AdminMapper adminMapper;

    @Pointcut("@annotation(com.itluo.annotation.CustomPermission)")
    public void customPermissionAround(){}

    @Around(value= "customPermissionAround()")
    public Object checkAuth(ProceedingJoinPoint joinPoint) throws Throwable {
        // 从切面织入点处通过反射机制获取织入点处的方法
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        // 获取切入点所在的方法
        Method method = signature.getMethod();
        // 获取注解操作
        CustomPermission auth = method.getAnnotation(CustomPermission.class);
        RoleType value = auth.value();
        Long id = BaseContext.getCurrentId();
        Long currentRole = BaseContext.getCurrentRole();
        Admin byAdminId = adminMapper.findByAdminId(id);
        if (byAdminId == null){
            throw new AccountNotFoundException(MessageConstant.ACCOUNT_NOT_FOUND);
        }
        if (!currentRole.equals(value.getRole()) || !currentRole.equals(byAdminId.getRole())){
            throw new InsufficientPermissionsException(MessageConstant.ROLE);
        }
        return joinPoint.proceed();
    }

}
