package com.yh.weatherpush.exception;

import com.yh.weatherpush.enums.ResultCode;
import lombok.Getter;

/**
 * @author yh
 */
@Getter
public class ApiException extends RuntimeException {
    private ResultCode errorCode;

    public ApiException(ResultCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public ApiException(String message) {
        super(message);
    }

    public ApiException(Throwable cause) {
        super(cause);
    }

    public ApiException(String message, Throwable cause) {
        super(message, cause);
    }

}
