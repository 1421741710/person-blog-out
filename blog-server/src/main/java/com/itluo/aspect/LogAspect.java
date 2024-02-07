package com.itluo.aspect;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.itluo.annotation.LogAnnotation;
import com.itluo.context.BaseContext;
import com.itluo.entity.Admin;
import com.itluo.entity.Log;
import com.itluo.json.JacksonObjectMapper;
import com.itluo.mapper.AdminMapper;
import com.itluo.mapper.LogMapper;
import com.itluo.utils.HttpContextUtils;
import com.itluo.utils.IpUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

/**
 * 切面 定义了通知和切点的关系
 * @author Administrator
 */
@Component
@Aspect
@Slf4j
public class LogAspect {

    @Autowired
    private AdminMapper adminMapper;

    @Autowired
    private LogMapper logMapper;

    @Pointcut("@annotation(com.itluo.annotation.LogAnnotation)")
    public void pt(){}

    /**
     * 环绕通知
     */
    @Around(("pt()"))
    public Object log(ProceedingJoinPoint joinPoint) throws Throwable {
        long beginTime = System.currentTimeMillis();
        //执行方法
        Object result = joinPoint.proceed();
        //执行时长(毫秒)
        long time = System.currentTimeMillis() - beginTime;
        //保存日志
        recordLog(joinPoint,time);
        return result;
    }

    private void recordLog(ProceedingJoinPoint joinPoint,long time) throws JsonProcessingException {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        LogAnnotation logAnnotation = method.getAnnotation(LogAnnotation.class);
        Long id = BaseContext.getCurrentId();
        Admin byAdminId = adminMapper.findByAdminId(id);
        //请求的方法名
        String className = joinPoint.getTarget().getClass().getName();
        String methodName = signature.getName();
        StringBuilder stringBuilder = new StringBuilder().append(className + "." + methodName + "()");
        //请求的参数
        JacksonObjectMapper objectMapper = new JacksonObjectMapper();
        Object[] args = joinPoint.getArgs();
        String params = objectMapper.writeValueAsString(args);
        //获取reqyest 设置IP地址
        HttpServletRequest request = HttpContextUtils.getHttpServletRequest();
        Log logs = Log.builder()
                .adminName(byAdminId.getUsername())
                .module(logAnnotation.module())
                .operator(logAnnotation.operator())
                .className(stringBuilder.toString())
                .params(params)
                .ip(IpUtils.getIpAddr(request))
                .executionTime(time)
                .build();
        log.info("操作记录:{}",logs.toString());
        logMapper.insertLog(logs);
    }

}
