package com.hyperj.system.service.impl;

import com.hyperj.common.enums.CacheType;
import com.hyperj.common.exception.CustomException;
import com.hyperj.common.utils.ShiroUtils;
import com.hyperj.common.utils.StringUtils;
import com.hyperj.common.utils.redis.RedisStringCache;
import com.hyperj.system.bean.po.SysUserPo;
import com.hyperj.system.bean.request.SysLoginRequest;
import com.hyperj.system.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SysLoginService {

    @Autowired
    private ISysUserService sysUserService;

    // 登录，验证用户
    public Long validate(SysLoginRequest loginRequest) {
        // 验证验证码（测试时忽略）
//        validateCaptcha(loginRequest.getCaptcha(),loginRequest.getCaptchaId());

        SysUserPo user = sysUserService.getUserByUserName(loginRequest.getUserName());

        if(user == null){
            throw new CustomException("用户不存在");
        }

        if(user.getRemoved().equals("1")){
            throw new CustomException("该账号已被删除");
        }

        if(user.getStatus().equals("0")){
            throw new CustomException("该账号已被停用");
        }

        String password = ShiroUtils.encryptPassword(
                user.getUserName(),loginRequest.getPassword(),user.getSalt()
        );
        if(!user.getPassword().equals(password)){
            throw new CustomException("密码输入错误");
        }

        return user.getUserId();
    }

    // 验证码验证
    public void validateCaptcha(String captcha, String captchaId){
        String captchaCache = RedisStringCache.get(captchaId, CacheType.CAPTCHA);
        if(StringUtils.isEmpty(captchaCache)){
            throw new CustomException("验证码已失效");
        }else if(!StringUtils.equalsIgnoreCase(captcha,captchaCache)){
            throw new CustomException("验证码错误");
        }
        RedisStringCache.remove(captchaId,CacheType.CAPTCHA);

    }

}
