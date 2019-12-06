package com.jsj.webapi.dataobject.info;

import com.jsj.webapi.dataobject.AbstractEntity;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * infoData： 综合信息通用表
 */

@Data
@Entity
@Table
public class ReportInfoData extends AbstractEntity implements Serializable {

    private static final long serialVersionUID = -8701147945382636515L;
    private String infoKind;   //数据类型
    private String infoName;    //数据类型Name

    private String str1;       //字段数据1
    private String str2;       //字段数据2

}
