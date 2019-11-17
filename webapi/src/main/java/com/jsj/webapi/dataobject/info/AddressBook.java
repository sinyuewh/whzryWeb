package com.jsj.webapi.dataobject.info;

import com.jsj.webapi.dataobject.AbstractEntity;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 通讯里实体类定义
 */
@Data
@Entity
@Table
public class AddressBook extends AbstractEntity implements Serializable {
    private static final long serialVersionUID = 5273783072826624131L;

    private  Integer num=1;     //序号
    private  String sname;       //姓名
    private  String kind;
    private  String workPlace;
    private  String job;
    private  String mobile;
    private  String officeTel;
    private  String officeAddr;
    private  String email;

    @Column(columnDefinition ="TEXT")
    private  String remark;
}
