package com.hyperj.app.controller.system;

import com.hyperj.common.utils.redis.RedisStringCache;
import com.hyperj.framework.web.utils.JwtUtil;
import com.hyperj.framework.web.utils.R;
import com.hyperj.system.bean.request.SysLoginRequest;
import com.hyperj.system.service.impl.SysLoginService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/system/auth")
@Api(tags="系统用户登录接口")
public class SysLoginController {

    @Autowired
    private SysLoginService sysLoginService;

    @Autowired
    private JwtUtil jwtUtil;

    @Value("${jwt.cache-expire}")
    private int cacheExpire;

    @PostMapping("/login")
    @ApiOperation("登录")
    public R login(@Validated SysLoginRequest loginRequest){

        // 验证用户信息
        Long userId =  sysLoginService.validate(loginRequest);
        // 获取token
        String token = jwtUtil.createToken(userId);
        // TODO 获取用户权限

        // 缓存用户token
        RedisStringCache.setCacheObject(token,userId,cacheExpire,TimeUnit.DAYS);
        return R.success("登录成功").put("token",token);
    }

}
