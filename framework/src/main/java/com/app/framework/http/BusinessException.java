package com.app.framework.http;

public class BusinessException extends RuntimeException {

    private Integer code;
    private String message;

    /**
     * 业务逻辑错误异常，不一定使用此类，用户可以根据自己的需求自定义
     *
     * @param code    错误码
     * @param message 错误信息
     */
    public BusinessException(Integer code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
