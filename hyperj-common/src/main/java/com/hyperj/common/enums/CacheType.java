package com.hyperj.common.enums;

/**
 * Redis类型枚举类,其中定义的是缓存key的前缀
 */
public enum CacheType {

    CAPTCHA("captcha:"),
    ;

    private String type;

    CacheType(String type) {
        this.type = type;
    }

    public String type(){
        return this.type;
    }
}
