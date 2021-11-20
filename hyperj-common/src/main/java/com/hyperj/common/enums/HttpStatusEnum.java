package com.hyperj.common.enums;

import com.hyperj.common.exception.BaseExceptionInterface;

public enum HttpStatusEnum implements BaseExceptionInterface {

    SUCCESS(200,"操作成功"),
    BODY_NOT_MATCH(400,"请求数据格式不服"),
    SC_UNAUTHORIZED(401,"无效的令牌!"),
    NOT_FOUND(404, "未找到该资源!"),
    NOT_ALLOW(405,"请求方式不支持!"),
    INTERNAL_SERVER_ERROR(500, "服务器内部错误!"),
    SERVER_BUSY(503,"服务器正忙，请稍后再试!")
    ;

    private Number resultCode;

    private String resultMsg;

    HttpStatusEnum(Number resultCode, String resultMsg) {
        this.resultCode = resultCode;
        this.resultMsg = resultMsg;
    }

    @Override
    public Number getResultCode() {
        return  this.resultCode;
    }

    @Override
    public String getResultMsg() {
        return this.resultMsg;
    }
}
