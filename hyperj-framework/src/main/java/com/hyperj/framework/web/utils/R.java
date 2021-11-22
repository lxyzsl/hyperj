package com.hyperj.framework.web.utils;


import com.hyperj.common.enums.HttpStatusEnum;
import com.hyperj.common.exception.BaseExceptionInterface;


import java.util.HashMap;

public class R extends HashMap<String,Object> {

    private static final long serialVersionUID = 1L;

    /**
     * 响应代码
     */
    public static final String CODE = "code";

    private static final String SUCCESS = "success";

    /**
     * 响应消息
     */
    public static final String MSG = "msg";

    /**
     * 响应对象
     */
    public static final String  Data = "data";


    public R(Number code,String msg,Object data){
        super.put(CODE, code);
        super.put(SUCCESS, code.equals(HttpStatusEnum.SUCCESS.getResultCode()));
        super.put(MSG, msg);
        if(null != data){
            super.put(Data, data);
        }
    }

    public R(BaseExceptionInterface exception,Object data){
        super.put(CODE, exception.getResultCode());
        super.put(SUCCESS,false);
        super.put(MSG,exception.getResultMsg());
        if(null != data){
            super.put(Data, data);
        }

    }

    /**
     * 成功
     */
    public static R success(){
        return new R(HttpStatusEnum.SUCCESS.getResultCode(), HttpStatusEnum.SUCCESS.getResultMsg(),null);
    }

    /**
     * 成功
     */
    public static R success(String msg){
        return new R(HttpStatusEnum.SUCCESS.getResultCode(),msg,null);
    }

    /**
     * 成功
     */
    public static R success(Object data){
        return new R(HttpStatusEnum.SUCCESS.getResultCode(), HttpStatusEnum.SUCCESS.getResultMsg(),data);
    }

    /**
     * 成功
     */
    public static R success(String msg,Object data){
        return new R(HttpStatusEnum.SUCCESS.getResultCode(),msg,data);
    }


    /**
     * 失败
     */
    public static R error(BaseExceptionInterface exceptionInterface){
        return new R(exceptionInterface,null);
    }

    /**
     * 失败
     */
    public static R error(BaseExceptionInterface exceptionInterface,Object data){
        return new R(exceptionInterface.getResultCode(),exceptionInterface.getResultMsg(),data);
    }

    /**
     * 失败
     */
    public static R error(){
        return new R(HttpStatusEnum.INTERNAL_SERVER_ERROR.getResultCode(), HttpStatusEnum.INTERNAL_SERVER_ERROR.getResultMsg(),null);
    }


    /**
     * 失败
     */
    public static R error(String msg){
        return new R(HttpStatusEnum.INTERNAL_SERVER_ERROR.getResultCode(),msg,null);
    }

    /**
     * 失败
     */
    public static R error(String msg,Object data){
        return new R(HttpStatusEnum.INTERNAL_SERVER_ERROR.getResultCode(),msg,data);
    }

    /**
     * 失败
     */
    public static R error(Number code,String msg){
        return new R(code,msg,null);
    }

    /**
     * 失败
     */
    public static R error(Number code,String msg,Object data){
        return new R(code,msg,data);
    }

    @Override
    public R put(String key, Object value) {
        super.put(key, value);
        return this;
    }
}
