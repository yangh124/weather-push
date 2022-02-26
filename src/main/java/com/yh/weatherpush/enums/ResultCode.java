package com.yh.weatherpush.enums;

/**
 * 枚举了一些常用code
 * 
 * @author yh
 */
public enum ResultCode {
    /**
     * 状态code
     */
    SUCCESS(200, "操作成功"), FAILED(500, "服务器异常"),
    VALIDATE_FAILED(404, "参数检验失败"),
    UNAUTHORIZED(401, "暂未登录或token已经过期"),
    FORBIDDEN(403, "没有相关权限");

    private final long code;
    private final String message;

    ResultCode(long code, String message) {
        this.code = code;
        this.message = message;
    }

    public long getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
