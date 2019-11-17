package com.jsj.webapi.exception;
/**
 * Created by jinshouji on 2018/5/14.
 */

import com.jsj.common.bean.HttpResult;
import com.jsj.common.utils.HttpResultUtil;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.HostUnauthorizedException;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.authz.UnauthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author ：jinshouji
 * @create ：2018-05-14 17:38
 * @remark : 统一的异常处理类
 **/

@ControllerAdvice
public class ExceptionHandle {
    protected Logger logger = LoggerFactory.getLogger(this.getClass());
    @ExceptionHandler(value=Exception.class)
    @ResponseBody
    public HttpResult handle(Exception e)
    {
        if (e instanceof MyException)
        {
            MyException myException = (MyException) e;
            return HttpResultUtil.error(myException.getCode(), myException.getMessage());
        }
        else {
            //可以写入日志
            logger.error("【系统异常】{}", e);

            //根据异常的情况，进行处理
            if (e instanceof UnauthorizedException) {
                return HttpResultUtil.error(HttpResultUtil.FORBIDDEN_VISIT, e.getMessage());
            } else if (e instanceof AuthorizationException) {

            } else if (e instanceof HostUnauthorizedException) {

            } else if (e instanceof UnauthenticatedException) {

            } else if (e instanceof org.springframework.web.servlet.NoHandlerFoundException) {
                return HttpResultUtil.error(404, e.getMessage());
            }
            return HttpResultUtil.error(-2, e.getMessage());
        }
    }
}
