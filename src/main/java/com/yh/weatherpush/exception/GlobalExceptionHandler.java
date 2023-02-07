package com.yh.weatherpush.exception;

import com.yh.weatherpush.dto.Result;
import com.yh.weatherpush.enums.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理
 *
 * @author yh
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = ApiException.class)
    public Result handle(ApiException e) {
        if (e.getErrorCode() != null) {
            return Result.failed(e.getErrorCode());
        }
        log.error(e.getMessage(), e);
        return Result.failed(e.getMessage());
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public Result handleValidException(MethodArgumentNotValidException e) {
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

    @ExceptionHandler(value = BindException.class)
    public Result handleValidException(BindException e) {
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

    @ExceptionHandler(value = Exception.class)
    public Result handleException(Exception e) {
        log.error(e.getMessage(), e);
        return Result.failed(ResultCode.FAILED);
    }
}
