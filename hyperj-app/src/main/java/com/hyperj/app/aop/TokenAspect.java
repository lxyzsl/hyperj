package com.hyperj.app.aop;

import com.hyperj.framework.config.shiro.ThreadLocalToken;
import com.hyperj.framework.web.utils.R;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class TokenAspect {

    @Value("${jwt.header}")
    private String header;

    // 在threadLocalToken媒介类中的token一定是新生成的
    @Autowired
    private ThreadLocalToken threadLocalToken;

    // 定义切点，所有controller控制器下的所有方法
    @Pointcut("execution(public * com.hyperj.app.controller.*.*(..))")
    public void aspect(){
    }

    // 定义环绕事件
    @Around("aspect()")
    public Object around(ProceedingJoinPoint point) throws Throwable{
        R r = (R) point.proceed(); // 监听方法执行结果
        String token = threadLocalToken.getToken();
//        System.out.println("token1:"+token);
        // 如果ThreadLocal中存在Token，说明是更新的Token
        if(token != null){
            r.put(header,token); // 在响应中放置Token
            threadLocalToken.clear();
        }
        return r;
    }
}
