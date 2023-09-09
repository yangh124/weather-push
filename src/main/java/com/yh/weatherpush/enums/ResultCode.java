package com.yh.weatherpush.enums;

import lombok.Getter;

import javax.servlet.http.HttpServletResponse;

/**
 * 枚举了一些常用code
 *
 * @author yh
 */
@Getter
public enum ResultCode {
    /**
     * 状态code
     */
    SUCCESS(HttpServletResponse.SC_OK, "操作成功"),
    FAILED(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "服务器异常"),
    VALIDATE_FAILED(HttpServletResponse.SC_BAD_REQUEST, "参数检验失败"),
    UNAUTHORIZED(HttpServletResponse.SC_UNAUTHORIZED, "暂未登录或token已经过期"),
    FORBIDDEN(HttpServletResponse.SC_FORBIDDEN, "没有相关权限"),
    BIZ_ERROR(240, "业务异常");

    private final int code;
    private final String message;

    ResultCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

}
