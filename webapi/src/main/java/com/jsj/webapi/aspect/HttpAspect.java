package com.jsj.webapi.aspect;/**
 * Created by jinshouji on 2018/5/15.
 */

import com.jsj.common.utils.ArrayUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @author ：jinshouji
 * @create ：2018-05-15 15:58
 * 接口的入参和出参日志的AOP
 **/

@Aspect
@Component
@Order(1)
public class HttpAspect {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    //com.jsj.webapi.controller.Login
    //com.jsj.webapi.controller.platform.basedata
    //com.xyz.service..*.*(..)
    @Pointcut("execution(public * com.jsj.webapi.controller..*.*(..))")
    public void log() {}

    @Before("log()")
    public void doBefore(JoinPoint joinPoint)
    {
        ServletRequestAttributes attributes=(ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request=attributes.getRequest();
        //url
        logger.info("IP={},Method={},url={},class_method={},args=【{}】"
                ,request.getRemoteAddr(),request.getMethod(),request.getRequestURI(),
                joinPoint.getSignature().getDeclaringTypeName()+"."+joinPoint.getSignature().getName(), ArrayUtil.getArrayJoinString(joinPoint.getArgs()));

    }

    //接口的返回参数
    @AfterReturning(returning ="object",pointcut = "log()")
    public void doAfterReturning(Object object)
    {
        ServletRequestAttributes attributes=(ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request=attributes.getRequest();
        logger.info("IP={},Method={},url={},response=【{}】",request.getRemoteAddr(),request.getMethod(),request.getRequestURI(),object);
    }

    @After("log()")
    public void doAfter()
    {
        //logger.info("doAfter");
    }
}
