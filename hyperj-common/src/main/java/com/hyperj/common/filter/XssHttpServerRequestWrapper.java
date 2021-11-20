package com.hyperj.common.filter;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HtmlUtil;
import cn.hutool.json.JSONUtil;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.*;
import java.nio.charset.Charset;
import java.util.LinkedHashMap;
import java.util.Map;

public class XssHttpServerRequestWrapper extends HttpServletRequestWrapper {

    // 子类构造器必须实现父类构造器
    public XssHttpServerRequestWrapper(HttpServletRequest request) {
        super(request);
    }

    @Override
    public String getParameter(String name) {
        String value = super.getParameter(name);
        if(!StrUtil.hasEmpty(value)){
            // 使用hutool进行转义
            value = HtmlUtil.filter(value);
        }
        return value;
    }

    @Override
    public String[] getParameterValues(String name) {
        String[] values = super.getParameterValues(name);
        if(values != null){
            for(int i=0;i< values.length;i++){
                String value = values[i];
                if(!StrUtil.hasEmpty(value)){
                    // 使用hutool进行转义
                    value = HtmlUtil.filter(value);
                }
                values[i] = value;
            }
        }
        return values;
    }

    @Override
    public Map<String, String[]> getParameterMap() {
        Map<String ,String[]> parameters = super.getParameterMap();
        // LinkedHashMap可以是的集合变得有序
        Map<String, String[]> map = new LinkedHashMap<>();
        if(parameters != null){
            for(String key : parameters.keySet()){
                String[] values = parameters.get(key);
                for (int i=0;i<values.length;i++){
                    String value  = values[i];
                    if(!StrUtil.hasEmpty(value)){
                        value = HtmlUtil.filter( value);
                    }
                    values[i] = value;
                }
                map.put(key,values);
            }
        }
        return map;
    }

    @Override
    public String getHeader(String name) {
        String value =  super.getHeader(name);
        if(!StrUtil.hasEmpty(value)){
            value = HtmlUtil.filter(value);
        }
        return value;
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        // 输入流
        InputStream in = super.getInputStream();
        // 创建字符流，为了从字符流中读取数据
        InputStreamReader reader = new InputStreamReader(in, Charset.forName("UTF-8"));
        // 为了读取数据的高效性，给读取数数据配置一个缓冲流
        BufferedReader buffer = new BufferedReader(reader);
        // 为了将从缓冲流读取的数据保存，这里new一个字符流
        StringBuffer body = new StringBuffer();
        // 从缓冲流中读取第一行数据
        String line = buffer.readLine();
        while (line != null) {
            // 如果这行数据有效，则将数据拼接到body中
            body.append(line);
            // 读取下一行数据,直到读取到空数据
            line = buffer.readLine();
        }

        // 关闭缓冲流
        buffer.close();
        // 关闭字符流
        reader.close();
        // 关闭输入流
        in.close();

        // 借助HuTool工具类将，读取到的JSON数据转为Map对象
        Map<String, Object> map = JSONUtil.parseObj(body.toString());
        // 转义Map对象
        Map<String, Object> result = new LinkedHashMap<>();
        for (String key : map.keySet()) {
            Object val = map.get(key);
            // 判断值类型
            if (val instanceof String) {
                if (!StrUtil.hasEmpty(val.toString())) {
                    result.put(key, HtmlUtil.filter(val.toString()));
                }
            } else {
                result.put(key, val);
            }
        }

        // 将result转成Json字符串，再创建一个IO流，从这个字符串中读取数据然后返回
        String json = JSONUtil.toJsonStr(result);
        // ByteArrayInputStream对象还不能直接返回，还需要构建ServletInputStream对象
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(json.getBytes());

        // 这里用到了匿名内部类，直接将创建对象与返回对象一并完成
        return new ServletInputStream() {
            @Override
            public boolean isFinished() {
                return false;
            }

            @Override
            public boolean isReady() {
                return false;
            }

            @Override
            public void setReadListener(ReadListener readListener) {

            }


            @Override
            public int read() throws IOException {
                // 从之前的IO流中读取数据并返回
                return byteArrayInputStream.read();
            }
        };
    }



}
