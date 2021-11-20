package com.hyperj.common.exception;

import lombok.Data;

/**
 * 全局异常
 */
@Data
public class GlobalException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    protected Number errorCode;

    protected String errorMsg;

    public GlobalException(){
        super();
    }

    public GlobalException(BaseExceptionInterface exceptionInterface){
        super(exceptionInterface.getResultCode().toString());
        this.errorCode = exceptionInterface.getResultCode();
        this.errorMsg = exceptionInterface.getResultMsg();
    }


    public GlobalException(BaseExceptionInterface exceptionInterface,Throwable e){
        super(exceptionInterface.getResultCode().toString(),e);
        this.errorCode = exceptionInterface.getResultCode();
        this.errorMsg = exceptionInterface.getResultMsg();
    }

    public GlobalException(String errorMsg){
        super(errorMsg);
        this.errorMsg = errorMsg;
    }

    public GlobalException(Number errorCode,String errorMsg){
        super(errorCode.toString());
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    public GlobalException(Number errorCode,String errorMsg,Throwable e){
        super(errorCode.toString(),e);
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    @Override
    public Throwable fillInStackTrace() {
        return this;
    }


}
