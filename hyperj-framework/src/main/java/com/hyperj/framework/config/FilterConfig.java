package com.hyperj.framework.config;

import com.hyperj.common.filter.XssFilter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.DispatcherType;
import java.util.HashMap;
import java.util.Map;

@Configuration
// 在spring boot中有时候需要控制配置类是否生效,可以使用@ConditionalOnProperty注解来控制@Configuration是否生效.
// 这里拿xss.enabled的值与havingValue的值对比，如果匹配则生效
@ConditionalOnProperty(value = "xss.enabled", havingValue = "true")
public class FilterConfig {

    @Value("${xss.excludes}")
    private String excludes;

    @Value("${xss.urlPatterns}")
    private String urlPatterns;

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Bean
    public FilterRegistrationBean xssFilterRegistration(){
        // 实例FilterRegistrationBean
        FilterRegistrationBean registration = new FilterRegistrationBean();
        // 设置该过滤器用于管理request方式的资源
        registration.setDispatcherTypes(DispatcherType.REQUEST);
        // 注册过滤器
        registration.setFilter(new XssFilter());
        // 设置拦截路径
        registration.addUrlPatterns(StringUtils.split(urlPatterns,","));
        // 设置过滤器名称
        registration.setName("xssFilter");
        // 设置过滤器优先级，这里设置为最高
        registration.setOrder(FilterRegistrationBean.HIGHEST_PRECEDENCE);
        // 设置其他参数
        Map<String,String> initParameters = new HashMap<>();
        initParameters.put("excludes", excludes);
        registration.setInitParameters(initParameters);
        return registration;
    }
}
