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
 * @remark:  框架系统表--角色权限表
 **/
@Data
@Entity
@Table
public class RolePermissions extends AbstractEntity implements Serializable {

    private static final long serialVersionUID = -4458565678495725181L;

    @Column(length=50,nullable=false)
    private String roleName;

    @Column(length=50,nullable=false)
    private String permissionsId;           //权限Id （通过表名+“：”+功能名 如 users:search 表示用户表（查询）
}
