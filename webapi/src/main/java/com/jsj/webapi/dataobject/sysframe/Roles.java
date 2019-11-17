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
 * @remark:  框架系统表--角色表
 **/
@Data
@Entity
@Table
public class Roles extends AbstractEntity implements Serializable{

    private static final long serialVersionUID = 2791899073297440881L;

    private int wbId;               //维保单位id

    @Column(length=50,unique=true,nullable=false)
    private String roleName;       //角色名称

    @Column(length=200)
    private String remark;       //角色说明
}
