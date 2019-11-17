package com.jsj.webapi.enums;

import com.jsj.common.utils.CodeEnum;
import lombok.Getter;

/**
 * @author ：jinshouji
 * @create ：2018-04-25 14:42
 **/

@Getter
public enum ErrorEnum implements CodeEnum {

    USERNAME_OR_PASSWORD_ISEMPTY(101,"用户名或密码为空!"),
    USERNAME_OR_PASSWORD_ISWRONG(102,"用户名或密码不正确"),

    ACCESS_NO_LOIN(-1,"没有登录！"),
    ACCESS_No_PERMISSIONS(403,"无权访问！"),
    NO_RESOURCE(400,"当前访问的资源不存在！"),

    Project_Error(20,"项目管理模块操作错误！"),
    WbPerson_Error(30,"维保人员模块操作错误！"),
    Lift_Error(40,"电梯管理模块操作错误"),
    WbBill_Error(50,"维保单模块操作错误"),
    LiftInspection_Error(60,"年检报告模块错误"),
    Role_Error(901,"角色管理模块错误")
    ;

    private Integer code;
    public Integer getCode()
    {
        return this.code;
    }


    private String message;
    public String getMessage()
    {
        return  this.message;
    }

    ErrorEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
