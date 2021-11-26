package com.hyperj.app;


import com.hyperj.common.utils.ShiroUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.text.IniRealm;
import org.apache.shiro.subject.Subject;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class HyperjAppApplicationTests {

    @Test
    public void testLoginLogout(){
        // 得到shiro.ini文件
//        DefaultSecurityManager securityManager = new DefaultSecurityManager();
//        IniRealm iniRealm = new IniRealm("classpath:shiro.ini");
//        securityManager.setRealm(iniRealm);
//        // 绑定securityManager
//        SecurityUtils.setSecurityManager(securityManager);
//        // 获取用户
//        Subject currentUser = SecurityUtils.getSubject();
//        UsernamePasswordToken token = new UsernamePasswordToken("zhangsan","1111");
//        try {
//            currentUser.login(token);
//            System.out.println("登陆成功");
//        }catch (UnknownAccountException uae){
//            System.out.println("未知账号");
//        }catch (IncorrectCredentialsException ice){
//            System.out.println("密码错误");
//        }catch (Exception e){
//            System.out.println("未知错误");
//        }

    }

}
