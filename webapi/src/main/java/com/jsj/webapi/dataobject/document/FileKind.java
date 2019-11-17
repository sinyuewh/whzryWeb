package com.jsj.webapi.dataobject.document;

import com.jsj.webapi.dataobject.AbstractEntity;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * infoData： 文档类别
 */

@Data
@Entity
@Table
public class FileKind extends AbstractEntity implements Serializable {

    private static final long serialVersionUID = 8056724121404851839L;

    private Integer     num = 1;     // 序号
    private String      fileKind;   // 文档类别
    private String      remark;	  // 备注

}
