package com.hyperj.framework.config.shiro;

import com.hyperj.common.exception.CustomException;
import com.hyperj.framework.web.utils.JwtUtil;
import com.hyperj.system.bean.po.SysUserPo;
import com.hyperj.system.convert.SysUserConvert;
import com.hyperj.system.service.ISysMenuService;
import com.hyperj.system.service.ISysRoleService;
import com.hyperj.system.service.ISysUserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class OAuth2Realm extends AuthorizingRealm {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private ISysUserService sysUserService;

    @Autowired
    private ISysRoleService sysRoleService;

    @Autowired
    private ISysMenuService sysMenuService;


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
        // 用户信息
        SysUserPo user = (SysUserPo) SecurityUtils.getSubject().getPrincipal();
        if(user == null){
            throw new CustomException("授权失败");
        }
        // 角色列表
        Set<String> roles = new HashSet<String>();
        // 功能列表
        Set<String> menus = new HashSet<String>();
        // 管理员拥有所有权限
        if (user.getRoot().equals("1"))
        {
            info.addRole("admin");
            info.addStringPermission("*:*:*");
        }else{
            // 查询用户的角色列表
            roles = sysRoleService.selectRoleKeys(user.getUserId());
            // 查询用户的权限列表
            menus =  sysMenuService.selectPermsByUserId(user.getUserId());
            // 角色加入AuthorizationInfo认证对象
            info.setRoles(roles);
            // 权限加入AuthorizationInfo认证对象
            info.setStringPermissions(menus);
        }
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
        //  检测该账户状态
        SysUserPo user = sysUserService.getUserInfo(userId);

        if(user.getStatus().equals("0")){
            throw new LockedAccountException("账号已被锁定，请联系管理员");
        }
        if(user.getRemoved().equals("1")){
            throw new LockedAccountException("账号已被删除，请联系管理员");
        }
        // 往info对象中添加用户信息，token，当前Realm类的名字
        System.out.println("doGetAuthenticationInfo");
        System.out.println(user);
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user,accessToken,getName());
        return info;

    }
}
