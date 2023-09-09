package com.yh.weatherpush.exception;

import cn.dev33.satoken.exception.*;
import com.yh.weatherpush.dto.Result;
import com.yh.weatherpush.enums.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletResponse;

/**
 * 全局异常处理
 *
 * @author yh
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 业务异常
     */
    @ExceptionHandler(value = ApiException.class)
    public Result handle(ApiException e, HttpServletResponse response) {
        response.setStatus(ResultCode.BIZ_ERROR.getCode());
        if (e.getErrorCode() != null) {
            return Result.failed(e.getErrorCode());
        }
        log.error(e.getMessage(), e);
        return Result.failed(ResultCode.BIZ_ERROR, e.getMessage());
    }

    /**
     * 参数校验异常
     */
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public Result handleValidException(MethodArgumentNotValidException e, HttpServletResponse response) {
        response.setStatus(ResultCode.VALIDATE_FAILED.getCode());
        BindingResult bindingResult = e.getBindingResult();
        String message = null;
        if (bindingResult.hasErrors()) {
            FieldError fieldError = bindingResult.getFieldError();
            if (fieldError != null) {
                message = fieldError.getDefaultMessage();
            }
        }
        log.warn(e.getMessage(), e);
        return Result.validateFailed(message);
    }

    /**
     * 参数校验异常
     */
    @ExceptionHandler(value = BindException.class)
    public Result handleValidException(BindException e, HttpServletResponse response) {
        response.setStatus(ResultCode.VALIDATE_FAILED.getCode());
        BindingResult bindingResult = e.getBindingResult();
        String message = null;
        if (bindingResult.hasErrors()) {
            FieldError fieldError = bindingResult.getFieldError();
            if (fieldError != null) {
                message = fieldError.getField() + fieldError.getDefaultMessage();
            }
        }
        log.warn(e.getMessage(), e);
        return Result.validateFailed(message);
    }

    // 拦截：未登录异常
    @ExceptionHandler(NotLoginException.class)
    public Result handlerException(NotLoginException e, HttpServletResponse response) {
        response.setStatus(ResultCode.UNAUTHORIZED.getCode());
        // 打印堆栈，以供调试
        log.error(e.getMessage(), e);
        // 返回给前端
        return Result.unauthorized(e.getMessage());
    }

    // 拦截：缺少权限异常
    @ExceptionHandler(NotPermissionException.class)
    public Result handlerException(NotPermissionException e, HttpServletResponse response) {
        response.setStatus(ResultCode.FORBIDDEN.getCode());
        log.error(e.getMessage(), e);
        return Result.forbidden("缺少权限：" + e.getPermission());
    }

    // 拦截：缺少角色异常
    @ExceptionHandler(NotRoleException.class)
    public Result handlerException(NotRoleException e, HttpServletResponse response) {
        response.setStatus(ResultCode.FORBIDDEN.getCode());
        log.error(e.getMessage(), e);
        return Result.forbidden("缺少角色：" + e.getRole());
    }

    // 拦截：二级认证校验失败异常
    @ExceptionHandler(NotSafeException.class)
    public Result handlerException(NotSafeException e, HttpServletResponse response) {
        response.setStatus(ResultCode.FORBIDDEN.getCode());
        log.error(e.getMessage(), e);
        return Result.forbidden("二级认证校验失败：" + e.getService());
    }

    // 拦截：服务封禁异常
    @ExceptionHandler(DisableServiceException.class)
    public Result handlerException(DisableServiceException e, HttpServletResponse response) {
        response.setStatus(ResultCode.FORBIDDEN.getCode());
        log.error(e.getMessage(), e);
        return Result.failed("当前账号 " + e.getService() + " 服务已被封禁 (level=" + e.getLevel() + ")：" + e.getDisableTime() + "秒后解封");
    }

    // 拦截：Http Basic 校验失败异常
    @ExceptionHandler(NotBasicAuthException.class)
    public Result handlerException(NotBasicAuthException e, HttpServletResponse response) {
        response.setStatus(ResultCode.FORBIDDEN.getCode());
        log.error(e.getMessage(), e);
        return Result.failed(e.getMessage());
    }

    /**
     * 其他异常
     */
    @ExceptionHandler(value = Exception.class)
    public Result handleException(Exception e, HttpServletResponse response) {
        response.setStatus(ResultCode.FAILED.getCode());
        log.error(e.getMessage(), e);
        return Result.failed(ResultCode.FAILED);
    }
}
