package com.hyperj.common.exception;

public interface BaseExceptionInterface {

    /**
     * 错误码
     */
    Number getResultCode();

    /**
     * 错误信息
     */
    String getResultMsg();
}
