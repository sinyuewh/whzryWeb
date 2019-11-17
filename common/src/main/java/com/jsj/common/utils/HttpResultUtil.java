package com.jsj.common.utils;

import com.jsj.common.bean.HttpResult;

/**
 * @author ：jinshouji
 * @create ：2018-04-25 15:33
 * @Remark : http请求返回的最外层对象实用工具类
 **/

public class HttpResultUtil {
    //禁止访问
    public static int FORBIDDEN_VISIT = 403;

    //返回一个成功的ResultVO
    public static HttpResult success(Object object) {
        HttpResult resultVO = new HttpResult();
        resultVO.setData(object);
        resultVO.setCode(0);
        resultVO.setState(0);
        resultVO.setMsg("成功");
        return resultVO;
    }

    public static HttpResult success() {
        return success(null);
    }

    //返回失败的ResultVO
    public static HttpResult error(Integer code, String msg) {
        HttpResult resultVO = new HttpResult();
        resultVO.setCode(code);
        resultVO.setState(code);
        resultVO.setMsg(msg);
        return resultVO;
    }


}
