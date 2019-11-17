package com.jsj.webapi.dataobject.sysframe;/**
 * Created by jinshouji on 2018/8/19.
 */

import com.jsj.webapi.dataobject.AbstractEntity;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @author ：jinshouji
 * @create ：2018-08-19 13:27
 * @remark: 附件存储表
 **/

@Data
@Entity
@Table
public class Attachment extends AbstractEntity implements Serializable
{

    private static final long serialVersionUID = -6255384405652983777L;
    private String parentId;            //父数据ID（采用数据的guid)
    private String fileName;             //文件名
    private String saveFileName;        //保存文件名
    private String fileDir;              //文件路径
    private String remark;              //备注
}
