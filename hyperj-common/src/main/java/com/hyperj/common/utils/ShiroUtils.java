package com.hyperj.common.utils;

import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.Md5Hash;


/**
 * shiro工具类
 */
public class ShiroUtils {

    /**
     * 生成随机盐
     */
    public static String randomSalt()
    {
        // 一个Byte占两个字节，此处生成的3字节，字符串长度为6
        SecureRandomNumberGenerator secureRandom = new SecureRandomNumberGenerator();
        return secureRandom.nextBytes(3).toHex();
    }

    /**
     * 生成密码
     */
    public static String encryptPassword(String username,String password,String salt){
        return new Md5Hash(username + password + salt).toHex();
    }



}
