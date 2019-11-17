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
public class FileDataDTO {
    private  Integer num =1;         //序号
    private  String  fileKind;		  //文档类别
    private  String  fileTitle;     //文件标题
    private  String  fileName;      //文件名
    private  String  filedir;       //文件完整的存储目录
    private  String  filekeys;      //文件关键字
    private  String  remark;	      //备注
}
