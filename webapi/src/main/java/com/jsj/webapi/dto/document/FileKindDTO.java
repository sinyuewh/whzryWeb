package com.jsj.webapi.dto.document;/**
 * Created by jinshouji on 2018/10/22.
 */

import lombok.Data;

/**
 * @author ：jinshouji
 * @create ：2018-10-22 11:18
 * @remark ：文档分类DTO
 **/
@Data
public class FileKindDTO {

    private  int id;                          //数据的ID
    private Integer     num = 1;             // 序号
    private String      fileKind;            // 文档类别
    private String      remark;	          // 备注
}
