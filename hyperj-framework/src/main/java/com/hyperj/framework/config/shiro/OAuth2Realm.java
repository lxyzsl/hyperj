package com.hyperj.framework.config.shiro;

import com.hyperj.framework.web.utils.JwtUtil;
import com.hyperj.system.bean.po.SysUserPo;
import com.hyperj.system.service.ISysUserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OAuth2Realm extends AuthorizingRealm {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private ISysUserService sysUserService;


    /**
     * 判断传入的令牌对象是否复核要求
     * @param token
     * @return
     */
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof OAuth2Token;
    }

    /**
     * 授权（验证权限调用）
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        // TODO 查询用户的权限列表
        // TODO 把权限列表添加到info对象中
        // 返回授权对象
        return info;
    }

    /**
     * 认证（登录时调用）
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        // 从认证token对象中获取token字符串
        String accessToken = (String) authenticationToken.getPrincipal();
        // 从令牌中获取userId
        long userId = jwtUtil.getUserId(accessToken);
        //  检测该账户是否被冻结
        SysUserPo user = sysUserService.getUserInfo(userId);
        if(null == user){
            throw new LockedAccountException("账号已被锁定，请联系管理员");
        }
        // 往info对象中添加用户信息，token，当前Realm类的名字
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user,accessToken,getName());
        return info;

    }
}
