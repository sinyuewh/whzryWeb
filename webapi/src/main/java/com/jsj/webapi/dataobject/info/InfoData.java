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
public class InfoData extends AbstractEntity implements Serializable {
    private static final long serialVersionUID = 4761716013374949414L;

    private String infoKind;   //数据类型

    private String str1;       //字段数据1
    private String str2;       //字段数据2
    private String str3;       //字段数据3
    private String str4;       //字段数据4
    private String str5;       //字段数据5
    private String str6;       //字段数据6
    private String str7;       //字段数据7
    private String str8;       //字段数据8
    private String str9;       //字段数据9
    private String str10;      //字段数据10

    private String str11;       //字段数据11
    private String str12;       //字段数据12
    private String str13;       //字段数据13
    private String str14;       //字段数据14
    private String str15;       //字段数据15
    private String str16;       //字段数据16
    private String str17;       //字段数据17
    private String str18;       //字段数据18
    private String str19;       //字段数据19
    private String str20;      //字段数据20

    private String str21;       //字段数据21
    private String str22;       //字段数据22
    private String str23;       //字段数据23
    private String str24;       //字段数据24
    private String str25;       //字段数据25
    private String str26;       //字段数据26
    private String str27;       //字段数据27
    private String str28;       //字段数据28
    private String str29;       //字段数据29
    private String str30;      //字段数据30

    private String reportName;      //上报单位
    private Date reportTime;        //上报时间
    private Integer reportStatus;   //上报状态
    private String abc;              //数据分类

    @Column(columnDefinition ="TEXT")
    private String txt1;      //text字段1

    @Column(columnDefinition ="TEXT")
    private String txt2;      //text字段2

    @Column(columnDefinition ="TEXT")
    private String txt3;      //text字段3

    @Column(columnDefinition ="TEXT")
    private String txt4;      //text字段4

    @Column(columnDefinition ="TEXT")
    private String txt5;      //text字段5

    @Column(columnDefinition ="TEXT")
    private String txt6;      //text字段6

    @Column(columnDefinition ="TEXT")
    private String txt7;      //text字段7

    @Column(columnDefinition ="TEXT")
    private String txt8;      //text字段8

    @Column(columnDefinition ="TEXT")
    private String txt9;      //text字段9

    @Column(columnDefinition ="TEXT")
    private String txt10;      //text字段10

    private Date time1;        //日期类型字段1
    private Date time2;        //日期类型字段2
    private Date time3;        //日期类型字段3
    private Date time4;        //日期类型字段4
    private Date time5;        //日期类型字段5


    //根据信息的类别，得到信息类别的名称
    public String getInfoKindName()
    {
        String result="";
        if(this.infoKind.equals("1"))
        {
            result="重点区域";
        }
        else if(this.infoKind.equals("2"))
        {
            result="重点领域";
        }
        else if(this.infoKind.equals("3"))
        {
            result="重点领域";
        }
        else if(this.infoKind.equals("4"))
        {
            result="重点实验室";
        }
        else if(this.infoKind.equals("5"))
        {
            result="重点项目";
        }
        else if(this.infoKind.equals("6"))
        {
            result="重点企业";
        }
        else if(this.infoKind.equals("7"))
        {
            result="重点院校";
        }
        else if(this.infoKind.equals("8"))
        {
            result="金融机构";
        }
        else if(this.infoKind.equals("9"))
        {
            result="";
        }
        return  result;
    }
}
