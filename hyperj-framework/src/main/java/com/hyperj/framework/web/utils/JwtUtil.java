package com.hyperj.framework.web.utils;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expire}")
    private int expire;

    /**
     * 创建token
     */
    public String createToken(long userId){
        // 计算令牌有效期，即当前日期+expire的日期
        Date date =  DateUtil.offset(new Date(), DateField.DAY_OF_YEAR,expire);

        // 通过加密算法生成秘钥
        // 创建算法对象
        Algorithm algorithm = Algorithm.HMAC256(secret);
        JWTCreator.Builder builder = JWT.create();
        String token = builder.withClaim("userId",userId).withExpiresAt(date).sign(algorithm);
        return token;
    }

    // 从token中获取userId
    public long getUserId(String token){
        // 解码jwt
        DecodedJWT jwt =  JWT.decode(token);
        long userId = jwt.getClaim("userId").asInt();
        return userId ;
    }

    // 验证令牌有效性
    public void  verifierToken(String token){
        // 创建算法对象
        Algorithm algorithm = Algorithm.HMAC256(secret);
        // 通过算法对象创建验证对象
        JWTVerifier verifier =  JWT.require(algorithm).build();
        // 验证token，成功无返回，失败抛Runtime异常
        verifier.verify(token);

    }
}
