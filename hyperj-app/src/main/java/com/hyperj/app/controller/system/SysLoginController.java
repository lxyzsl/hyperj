package com.hyperj.app.controller.system;

import cn.hutool.core.util.StrUtil;
import com.hyperj.common.utils.redis.RedisStringCache;
import com.hyperj.framework.web.utils.JwtUtil;
import com.hyperj.framework.web.utils.R;
import com.hyperj.system.bean.po.SysUserPo;
import com.hyperj.system.bean.request.SysLoginRequest;
import com.hyperj.system.bean.vo.SysUserVo;
import com.hyperj.system.convert.SysUserConvert;
import com.hyperj.system.service.ISysMenuService;
import com.hyperj.system.service.ISysRoleService;
import com.hyperj.system.service.ISysUserService;
import com.hyperj.system.service.impl.SysLoginService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/system/auth")
@Api(tags="系统用户登录接口")
public class SysLoginController {

    @Autowired
    private SysLoginService sysLoginService;

    @Autowired
    private ISysRoleService sysRoleService;

    @Autowired
    private ISysUserService sysUserService;

    @Autowired
    private ISysMenuService sysMenuService;

    @Autowired
    private SysUserConvert sysUserConvert;

    @Autowired
    private RedisStringCache redisStringCache;

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

    @GetMapping("/getUserInfo")
    @ApiOperation("获取用户信息")
    public R getUserInfo(@RequestHeader("Authorization") String authHeader){
        String token = StrUtil.split(authHeader," ")[1];
        long userId = jwtUtil.getUserId(token);
        SysUserPo sysUserPo = sysUserService.getUserInfo(userId);
        SysUserVo sysUserVo =  sysUserConvert.sysUserVo(sysUserPo);
        // 角色集合
        Set<String> roles = sysRoleService.selectRoleKeys(sysUserVo.getUserId());
        // 权限集合
        Set<String> permissions = sysMenuService.selectPermsByUserId(userId);
        return R.success().put("user",sysUserVo).put("roles",roles).put("permissions",permissions);
    }


    @GetMapping("/logout")
    @ApiOperation("登出")
    public R logOut(@RequestHeader("Authorization") String authHeader){
        String token = StrUtil.split(authHeader," ")[1];
        long userId = jwtUtil.getUserId(token);
        SysUserPo sysUserPo = sysUserService.getUserInfo(userId);
        Subject subject =  SecurityUtils.getSubject();
        SysUserPo currentUser = (SysUserPo) subject.getPrincipal();
        if(currentUser != null && sysUserPo.getUserId().equals(currentUser.getUserId())){
            subject.logout();
        }
        redisStringCache.delete(token);

        return R.success();
    }



}
