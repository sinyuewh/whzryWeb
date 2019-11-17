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
public class FileData extends AbstractEntity implements Serializable {

    private static final long serialVersionUID = 4357701551493820744L;

    private  Integer num =1;         //序号
    private  String  fileKind;		  //文档类别
    private  String  fileTitle;     //文件标题
    private  String  fileName;      //文件名
    private  String  filedir;       //文件完整的存储目录
    private  String  filekeys;      //文件关键字
    private  String  remark;	      //备注
}
