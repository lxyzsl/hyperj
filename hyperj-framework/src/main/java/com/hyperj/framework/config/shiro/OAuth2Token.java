package com.hyperj.framework.config.shiro;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * 客户端提交的Token不能直接交给Shiro框架，需要先封装成 AuthenticationToken类型的对象。
 */
public class OAuth2Token implements AuthenticationToken {

    private final String token;

    public OAuth2Token(String token){
        this.token = token;
    }

    // 返回认证过程中提交的账户表示
    @Override
    public Object getPrincipal() {
        return token;
    }

    // 返回用户在验证提交的身份验证过程中的凭据 account identity
    @Override
    public Object getCredentials() {
        return token;
    }
}
