package com.hyperj.framework.config.shiro;

import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.apache.shiro.mgt.SecurityManager;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Shiro的配置类，用于替换XML的写法
 */
@Configuration
public class ShiroConfig  {

    /**
     * 封装oauth2Realm对象给Shiro
     * @return
     */
    @Bean("securityManager")
    public SecurityManager securityManager( OAuth2Realm oauth2Realm){
        DefaultWebSecurityManager securityManager =  new DefaultWebSecurityManager();
        securityManager.setRealm(oauth2Realm);
        securityManager.setRememberMeManager(null);
        return securityManager;
    }


    /**
     * 封装shiroFilter对象给Shiro
     * @param securityManager
     * @param oAuth2Filter
     * @return
     */
    @Bean("shiroFilter")
    public ShiroFilterFactoryBean shiroFilter( SecurityManager securityManager, OAuth2Filter oAuth2Filter){
        // 构建FactoryBean对象
        ShiroFilterFactoryBean shiroFilterBean = new ShiroFilterFactoryBean();
        shiroFilterBean.setSecurityManager(securityManager);

        // oauth过滤，把filter封装成map给FactoryBean
        Map<String, Filter> filters = new HashMap<>();
        filters.put("oauth2",oAuth2Filter);
        shiroFilterBean.setFilters(filters);

        Map<String,String> filterMap = new LinkedHashMap<>();
        // 定义拦截路径规则
        // anon不拦截，oauth2拦截
        // 这里将Swagger 2和3的版本可能会拦截的地址都写上了
        filterMap.put("/webjars/**","anon");
        filterMap.put("/druid/**","anon");
        filterMap.put("/app/**","anon");
        filterMap.put("/sys/login","anon");
        filterMap.put("/swagger/**","anon");
        filterMap.put("/v3/api-docs","anon");
        filterMap.put("/v2/api-docs","anon");
        filterMap.put("/swagger-ui.html","anon");
        filterMap.put("/swagger-resources/**","anon");
        filterMap.put("/swagger-ui/**","anon");
        filterMap.put("/captcha.jpg","anon");
        filterMap.put("/user/register","anon");
        filterMap.put("/user/login","anon");
        filterMap.put("/system/user/**","anon");
        filterMap.put("/test/**","anon");
        filterMap.put("/**","oauth2");
        shiroFilterBean.setFilterChainDefinitionMap(filterMap);

        return shiroFilterBean;
    }

    /**
     * 返回生命周期
     * @return
     */
    @Bean("lifecycleBeanPostProcessor")
    public  LifecycleBeanPostProcessor lifecycleBeanPostProcessor(){
        return  new LifecycleBeanPostProcessor();
    }

    /**
     * AOP切面类，web方法执行前，权限验证
     * @param securityManager
     * @return
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(  SecurityManager securityManager){
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager);
        return advisor;
    }


}
