package com.jsj.webapi.dataobject.sysframe;

import com.jsj.webapi.dataobject.AbstractEntity;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Created by jinshouji on 2019/11/9.
 * Excel 数据模板的定义
 */
@Data
@Entity
@Table
public class ExcelModel extends AbstractEntity implements Serializable {
    private static final long serialVersionUID = 269071915469027645L;

    private Integer modelKind=0;         //模板类型  0--excel导入模板   1---excel导出模板
    private String modelName;            //模板的Name
    private String fileDir;              //文件路径

    private Integer beginRow;            //开始的数据行
    private String colMapping;           //字段映射
    private String remark;              //备注

}
