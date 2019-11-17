package com.jsj.webapi.dto.log;/**
 * Created by jinshouji on 2018/10/22.
 */

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * @author ：jinshouji
 * @create ：2018-10-22 11:18
 * @remark ：信息数据DTO
 **/
@Data
public class OperateLogDTO {

    private  int id;                 //数据的ID
    private int logType=0;          //日志的类型（0--登录日志 1--操作日志）

    private int userId;             //用户id
    private String userName;        //用户Name

    private Date operatTime;        //操作时间
    private String operatContent;   //操作内容
    private String loginIP;          //登录ip

    private String time0;
    private String time1;

    @JsonFormat(timezone = "GMT+8",pattern="yyyy-MM-dd HH:mm:ss")
    public Date getOperatTime() {
        return this.operatTime;
    }
}
