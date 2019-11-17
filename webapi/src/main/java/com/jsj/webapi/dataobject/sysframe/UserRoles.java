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
 * @remark:  框架系统表--用户角色表
 **/
@Data
@Entity
@Table
public class UserRoles extends AbstractEntity implements Serializable {

    private static final long serialVersionUID = 5947528404036996662L;

    private  int userId;       //用户id

    @Column(length=50,nullable=false)
    private String roleName;
}
