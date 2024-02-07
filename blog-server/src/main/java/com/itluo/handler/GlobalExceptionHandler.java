package com.itluo.handler;

import com.itluo.constant.MessageConstant;
import com.itluo.exception.BaseException;
import com.itluo.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

/**
 * 全局异常处理器，处理项目中抛出的业务异常
 * @author Administrator
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 捕获业务异常
     * @param ex
     * @return
     */
    @ExceptionHandler
    public Result exceptionHandler(BaseException ex){
        log.error("异常信息：{}", ex.getMessage());
        return Result.error(ex.getMessage());
    }

    /**
     * 处理SQL异常
     * @param ex
     * @return
     */
    @ExceptionHandler
    public Result exceptionHandler(SQLIntegrityConstraintViolationException ex){
        //Duplicate entry 'zhangsan' for key 'employee.idx_username'
        String message = ex.getMessage();
        String str = "Duplicate entry";
        if(message.contains(str)){
            String[] split = message.split(" ");
            String username = split[2];
            String msg = username + MessageConstant.ALREADY_EXISTS;
            return Result.error(msg);
        }else{
            return Result.error(MessageConstant.UNKNOWN_ERROR);
        }
    }

    /**
     * 处理参数校验异常（JSR-303/JSR-349数据校验）
     * @param ex MethodArgumentNotValidException
     * @return 结果对象
     */
    @ExceptionHandler(value = {MethodArgumentNotValidException.class, BindException.class})
    public Result handleValidationExceptions(MethodArgumentNotValidException ex) {
        BindingResult bindingResult = ex.getBindingResult();
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();

        // 封装错误信息到结果对象
        String msg = null;
        for (FieldError fieldError : fieldErrors) {
            msg = fieldError.getDefaultMessage();
        }

        log.error("参数校验异常：{}", msg);
        return Result.error(msg);
    }


}
