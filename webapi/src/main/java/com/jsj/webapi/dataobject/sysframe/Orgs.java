package com.jsj.webapi.dataobject.sysframe;/**
 * Created by jinshouji on 2018/8/17.
 */

import com.jsj.webapi.dataobject.AbstractEntity;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @author ：jinshouji
 * @create ：2018-08-17 16:00
 * @remark:  框架系统表--组织机构表
 **/

@Data
@Entity
@Table
public class Orgs extends AbstractEntity implements Serializable
{
    private static final long serialVersionUID = -5504859756457317770L;

    @Column(length=50,nullable=false)
    private String orgName;         //机构名称

    @Column(length =50)
    private String orgCode;         //组织机构代码

    @Column(length =10)
    private String longitude;       //单位的经度

    @Column(length =10)
    private String latitude;        //单位的维度

    @Column(length =50)
    private String linkMan;         //联系人

    @Column(length =50)
    private String linkTel;        //联系电话

    @Column(length =100)
    private String remark;           //备注

    /*-------保留字段--------------*/
    @Column(length =10)
    private String postCode;        //邮政编码

    @Column(length =10)
    private String fax;             //传真

    @Column(length =100)
    private String email;           //电子邮箱

    @Column(length =100)
    private String url;             //组织机构的网址

    private int parentId=0;        //父数据id
}
