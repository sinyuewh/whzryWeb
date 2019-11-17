package com.jsj.webapi.exception;
/**
 * Created by jinshouji on 2018/5/14.
 */

/**
 * @author ：jinshouji
 * @create ：2018-05-14 17:33
 * @remark ：自定义异常处理
 **/
public class MyException extends RuntimeException {
    private  Integer code;
    public MyException(Integer code,String msg)
    {
        super(msg);
        this.code=code;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
