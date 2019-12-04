package com.jsj.webapi.dto.sysframe;/**
 * Created by jinshouji on 2018/10/22.
 */

import lombok.Data;

import javax.persistence.Column;
import java.util.Date;

/**
 * @author ：jinshouji
 * @create ：2018-10-22 11:18
 * @remark ：用户数据DTO
 **/
@Data
public class OrgsDTO {

    private  int id;                //数据的ID

    private String orgName;         //机构名称

    private String orgCode;         //组织机构代码

    private String longitude;       //单位的经度

    private String latitude;        //单位的维度

    private String linkMan;         //联系人

    private String linkTel;        //联系电话

    private String remark;           //备注

    /*-------保留字段--------------*/
    private String postCode;        //邮政编码

    private String fax;             //传真

    private String email;           //电子邮箱

    private String url;             //组织机构的网址

    private int parentId=0;        //父数据id

}
