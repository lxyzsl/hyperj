package com.hyperj.framework.exception;

import com.hyperj.common.enums.HttpStatusEnum;
import com.hyperj.common.exception.GlobalException;
import com.hyperj.framework.web.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;


// 如果不想每次都写private  final Logger logger = LoggerFactory.getLogger(当前类名.class); 可以用注解@Slf4j;
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 系统异常
     */
    @ExceptionHandler(GlobalException.class)
    @ResponseBody
    public R handleException(GlobalException e, HttpServletRequest request){
        String requestURI = request.getRequestURI();
        log.error("请求地址'{}',业务发生异常！原因是：'{}'",requestURI,e.getMessage());
        return R.error(HttpStatusEnum.BODY_NOT_MATCH);
    }


    /**
     * 系统异常
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public R handleException(Exception e, HttpServletRequest request){
        String requestURI = request.getRequestURI();
        log.error("请求地址'{}',系统异常！原因是：'{}'",requestURI,e.getMessage());
        return R.error(e.getMessage());
    }

    /**
     * 空指针异常
     */
    @ExceptionHandler(NullPointerException.class)
    @ResponseBody
    public R handleException(NullPointerException e,HttpServletRequest request){
        String requestURI = request.getRequestURI();
        log.error("请求地址'{}',空指针异常！原因是：'{}'",requestURI,e.getMessage());
        return R.error(HttpStatusEnum.BODY_NOT_MATCH);
    }

    /**
     * 请求方式不支持
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseBody
    public R handleException(HttpRequestMethodNotSupportedException e,HttpServletRequest request){
        String requestURI = request.getRequestURI();
        log.error("请求地址'{}',不支持'{}'请求",requestURI,e.getMethod());
        return R.error(HttpStatusEnum.NOT_ALLOW);
    }

    /**
     * 拦截未知的运行时异常
     */
    @ExceptionHandler(RuntimeException.class)
    public R handleRuntimeException(RuntimeException e, HttpServletRequest request)
    {
        String requestURI = request.getRequestURI();
        log.error("请求地址'{}',发生未知异常.", requestURI, e);
        return R.error("内部错误");
    }

    /**
     * 自定义验证异常
     */
    @ExceptionHandler(BindException.class)
    public R handleBindException(BindException e)
    {
        System.out.println("handleBindException");
        String message = e.getAllErrors().get(0).getDefaultMessage();
        log.error("参数校验绑定异常：{}，详细信息：{}",message,e.getMessage());
        return R.error(message);
    }

    /**
     * 自定义验证异常
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public R handleMethodArgumentNotValidException(MethodArgumentNotValidException e)
    {
        String message = e.getBindingResult().getFieldError().getField() + e.getBindingResult().getFieldError().getDefaultMessage();
        log.error("方法参数验证失败：{}，详细信息：{}",message,e.getMessage());

        return R.error(message);
    }

}
