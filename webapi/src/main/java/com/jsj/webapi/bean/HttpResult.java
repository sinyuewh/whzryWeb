package com.jsj.webapi.bean;


import com.jsj.common.config.SystemConfig;
import lombok.Data;

/**
 * Created by Administrator on 2016/12/7.
 */
@Data
public class HttpResult {

    private static int STATE_SUCCESS = 0;
    private static int STATE_FAIL = 1;
    private static int STATE_UNAUTHORIZED = 401;
    private static int NO_LOGIN = - 1;

    private int state;
    private String code;
    private String msg;
    private Object data;
    private String filePre = SystemConfig.getInstance().getFilePre();


    public HttpResult(int state, String code, String msg, Object obj, String filePre) {
        this.state = state;
        this.code = code;
        this.msg = msg;
        this.data = obj;
        this.filePre = filePre;
    }

    public HttpResult() {
    }

    public HttpResult(int state, String code, String msg, Object obj) {
        this.state = state;
        this.code = code;
        this.msg = msg;
        this.data = obj;
    }

    public HttpResult(int state, String msg, Object obj) {
        this.state = state;
        this.msg = msg;
        this.data = obj;
    }

    /*
    public static HttpResult createSuccess(String msg) {
        return createSuccess(msg, null);
    }*/

    public static HttpResult createSuccess(String msg, Object content) {
        return new HttpResult(STATE_SUCCESS,"0", msg, content);
    }

    public static HttpResult createSuccess(Object content) {
        return createSuccess(null, content);
    }

    public static HttpResult createSuccess() {
        return createSuccess(null, null);
    }

    public static HttpResult createFAIL(String msg) {
        return new HttpResult(STATE_FAIL, msg, null);
    }

    public static HttpResult createResult(boolean succ) {
        if(succ==false)
        {
            return HttpResult.createFAIL("提示：操作失败，可能是网络故障！");
        }
        else {
            return createSuccess(null, null);
        }
    }

    public static HttpResult createFAIL()
    {
        return HttpResult.createFAIL("提示：操作失败，可能是网络故障！");
    }

    //表示用户没有登陆
    public static HttpResult createNoLogin(String msg) {
        return new HttpResult(NO_LOGIN, "-1",msg, null);
    }


    public static HttpResult createFAIL(String code, String msg) {
        return new HttpResult(STATE_FAIL, code, msg, null);
    }

    public static HttpResult createFAIL(String code, String msg, Object content) {
        return new HttpResult(STATE_FAIL, code, msg, content);
    }

    public static HttpResult createUnauthorized(String msg) {
        return new HttpResult(STATE_UNAUTHORIZED, msg, null);
    }

    public static HttpResult createUnauthorized(String code, String msg) {
        return new HttpResult(STATE_UNAUTHORIZED, code, msg, null);
    }

    public static HttpResult createUnauthorized(String code, String msg, Object content) {
        return new HttpResult(STATE_UNAUTHORIZED, code, msg, content);
    }
}
