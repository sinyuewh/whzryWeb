package com.jsj.common.bean;

/**
 * @author ：jinshouji
 * @create ：2018-04-25 15:27
 * @remark : http请求返回的最外层对象
 **/

 import lombok.Data;

        import java.io.Serializable;

@Data
public class HttpResult<T> implements Serializable {

    private static final long serialVersionUID = 3068837394742385883L;

    /** 错误码. */
    private Integer code;

    private Integer state;

    /** 提示信息. */
    private String msg;

    /** 具体内容. */
    private T data;

    /** 具体内容. */
    private T obj;

}
