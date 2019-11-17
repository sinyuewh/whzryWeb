package com.jsj.webapi.dataobject.log;/**
 * Created by jinshouji on 2018/8/19.
 */

import com.jsj.webapi.dataobject.AbstractEntity;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * @author ：jinshouji
 * @create ：2018-08-19 14:34
 * @remark ：操作日志
 **/

@Data
@Entity
@Table
public class OperateLog extends AbstractEntity implements Serializable {

    private static final long serialVersionUID = -2674135287242246840L;
    private int logType=0;          //日志的类型（0--登录日志 1--操作日志）

    private int userId;             //用户id
    private String userName;        //用户Name

    private Date operatTime;             //操作时间
    private String operatContent;       //操作内容
    private String loginIP;              //登录ip

}
