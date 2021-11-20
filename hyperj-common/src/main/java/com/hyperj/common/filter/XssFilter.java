package com.hyperj.common.filter;


import com.hyperj.common.utils.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class XssFilter implements Filter {

    /**
     * 排除链接
     */
    public List<String> excludes = new ArrayList<>();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        String tempExcludes = filterConfig.getInitParameter("excludes");
        if (StringUtils.isNotEmpty(tempExcludes))
        {
            String[] url = tempExcludes.split(",");
            for (int i = 0; url != null && i < url.length; i++)
            {
                excludes.add(url[i]);
            }
        }
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        // 首先将servletRequest对象强制转换成HttpServletRequest对象
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        // 如果是GET，DELETE方法，或者在白名单中的不过滤
        if (handleExcludeURL(request))
        {
            filterChain.doFilter(request, response);
            return;
        }
        // 将请求给Wrapper
        XssHttpServerRequestWrapper wrapper = new XssHttpServerRequestWrapper(request);
        // 过滤转义
        filterChain.doFilter(wrapper,servletResponse);
    }

    private boolean handleExcludeURL(HttpServletRequest request){
        String url = request.getServletPath();
        String method = request.getMethod();
        // GET DELETE 不过滤
        if(method == null || method.matches("GET") || method.matches("DELETE")){
            return true;
        }

        return StringUtils.matches(url, excludes);
    }

    @Override
    public void destroy() {

    }
}
