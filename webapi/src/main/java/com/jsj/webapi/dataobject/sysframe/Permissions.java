package com.jsj.webapi.dataobject.sysframe;

/**
 * Created by jinshouji on 2018/4/30.
 */

import com.jsj.webapi.dataobject.AbstractEntity;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @author ：jinshouji
 * @create ：2018-04-25 18:03
 * @remark:  框架系统表--权限定义表
 **/
@Data
@Entity
@Table
public class Permissions extends AbstractEntity implements Serializable {

    private static final long serialVersionUID = -5230266442526530228L;

    @Column(length=50,unique=true,nullable=false)
    private String permissionsId;           //权限Id （通过 类名+“：”+功能名 如 users:search 表示用户表（查询）全部用小写

    @Column(length=50,nullable=false)
    private String permissionsName;        //权限名称

    private int parentId;                   //父权限ID
    private int num;                     //排序
    private String remark;                 //权限说明

}
