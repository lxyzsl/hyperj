package com.hyperj.framework.config.shiro;

import cn.hutool.core.util.StrUtil;
import com.alibaba.druid.support.json.JSONUtils;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.hyperj.common.enums.HttpStatusEnum;
import com.hyperj.common.exception.CustomException;
import com.hyperj.common.utils.redis.RedisStringCache;
import com.hyperj.framework.web.utils.JwtUtil;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.web.filter.authc.AuthenticatingFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

@Component
// prototype表示每次获得bean都会生成一个新的对象，如果是单例就无法使用TreadLocal
@Scope("prototype")
public class OAuth2Filter extends AuthenticatingFilter {

    @Autowired
    private ThreadLocalToken threadLocalToken;

    @Value("${jwt.cache-expire}")
    private int cacheExpire;

    @Value("${jwt.header}")
    private String  header;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private RedisStringCache redisStringCache;

    /**
     * 获取请求头中携带的Token字符串
     */
    private String getRequestToken(HttpServletRequest request){
        String token = request.getHeader(header);
        // 如果请求头中没有token，那就尝试从请求体里获取
        if(!StrUtil.isNotBlank(token)){
            token = request.getParameter(header);
        }
        // 如果是Bearer 则需要截取
        if(!StrUtil.isBlank(token) && header.equals("Authorization")){
           String[] tokenArr = StrUtil.split(token," ");
           return tokenArr[1];
        }
        return token;
    }

    /**
     * 拦截请求后，用于把请求携带的令牌字符串封装成对象返回Shiro框架
     */
    @Override
    protected AuthenticationToken createToken(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
        // 获取请求Token
        String token = getRequestToken((HttpServletRequest) servletRequest);
        if(StrUtil.isBlank(token)){
            return null;
        }
        return new OAuth2Token(token);
    }

    /**
     * 拦截请求，判断请求是否需要被Shiro处理
     * 过滤掉Option请求
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
       HttpServletRequest req = (HttpServletRequest) request;
       if(req.getMethod().equals(RequestMethod.OPTIONS.name())){
           return true;
       }else{
           return false;
       }
    }

    /**
     * 认证和授权时获取token，验证token是否过期，是否要刷新token
     */
    @Override
    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;

        resp.setContentType("text/html");
        resp.setCharacterEncoding("UTF-8");
        resp.setHeader("Access-Control-Allow-Credentials","true");
        resp.setHeader("Access-Control-Allow-Origin",req.getHeader("Origin"));

        threadLocalToken.clear();

        String token = getRequestToken(req);
        // 如果Token为空
        if(StrUtil.isBlank(token)){
            // 设置响应码
            resp.setStatus((Integer) HttpStatusEnum.SC_UNAUTHORIZED.getResultCode());
            // 往响应体些消息
            resp.getWriter().print(HttpStatusEnum.SC_UNAUTHORIZED.getResultMsg());
            return false;
        }
        // 验证token内容是否有效，是否过期
        try {
            jwtUtil.verifierToken(token);
        }catch(TokenExpiredException e){
            // token过期
            // 查看redis缓存中是否有这个Token，如果有则刷新token，没有则返回false
            if(redisStringCache.hasKey(token)){
                redisStringCache.delete(token);
                // 从老的token中获取userId用来生成新token
                long userId = jwtUtil.getUserId(token);
                token = jwtUtil.createToken(userId);
                // 新token存入redis
                RedisStringCache.setCacheObject(token,userId,cacheExpire, TimeUnit.DAYS);
                // 将token存入媒介类
                threadLocalToken.setToken(token);
            }else{
                // 设置响应码
                resp.setStatus((Integer) HttpStatusEnum.SC_UNAUTHORIZED.getResultCode());
                // 往响应体些消息
                resp.getWriter().print(HttpStatusEnum.SC_UNAUTHORIZED.getResultMsg());
                return false;
            }
        }catch(Exception e){
            // 设置响应码
            resp.setStatus((Integer) HttpStatusEnum.SC_UNAUTHORIZED.getResultCode());
            // 往响应体些消息
            resp.getWriter().print(HttpStatusEnum.SC_UNAUTHORIZED.getResultMsg());
            return false;
        }
        // token没问题，由于无法直接调用Realm类，所以这里通过执行父类的executeLogin方法间接让Shiro调用Realm类。
        return executeLogin(servletRequest, servletResponse);
    }

    /**
     * 认证失败，给客户端返回错误消息
     */
    @Override
    protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e, ServletRequest request, ServletResponse response) {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        resp.setContentType("text/html");
        resp.setCharacterEncoding("UTF-8");
        resp.setHeader("Access-Control-Allow-Credentials","true");
        resp.setHeader("Access-Control-Allow-Origin",req.getHeader("Origin"));

        resp.setStatus((Integer) HttpStatusEnum.SC_UNAUTHORIZED.getResultCode());
        HashMap<String, String> result = new HashMap<>();
        result.put("msg",e.getMessage());
        try {
            resp.getWriter().print(JSONUtils.toJSONString(result));
        }catch (Exception exception){
            // 不处理
        }
        // 因为是登录失败才会执行到这个方法，所以一定返回false
        return false;
    }

    @Override
    public void doFilterInternal(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        super.doFilterInternal(request, response, chain);
    }
}
