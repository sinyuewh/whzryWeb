package com.jsj.webapi.dataobject.info;

import com.jsj.webapi.dataobject.AbstractEntity;
import lombok.Data;
import org.apache.commons.collections.map.HashedMap;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;
import java.util.Map;

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
    public static String getNameByInfoKind(String infoKind)
    {
        String result="";
        if(infoKind.equals("1"))
        {
            result="重点区域";
        }
        else if(infoKind.equals("2"))
        {
            result="重点领域";
        }
        else if(infoKind.equals("3"))
        {
            result="重点实验室";
        }
        else if(infoKind.equals("4"))
        {
            result="重点项目";
        }
        else if(infoKind.equals("5"))
        {
            result="重点企业";
        }
        else if(infoKind.equals("6"))
        {
            result="重点院校";
        }
        else if(infoKind.equals("7"))
        {
            result="金融机构";
        }
        else if(infoKind.equals("8"))
        {
            result="通讯录";
        }
        else if(infoKind.equals("9"))
        {
            result="";
        }
        return  result;
    }

    //得到xls模板的文件名
    public static String getExcelModelFileDir(String infoKind)
    {
        //定义模板和excel的映射关系
        String modelFiledir="";
        if(infoKind.equals("1"))
        {
            modelFiledir= "重点区域.xls";
        }
        else if(infoKind.equals("2"))
        {
            modelFiledir="重点领域.xls";
        }
        else if(infoKind.equals("3"))
        {
            modelFiledir="重点实验室.xls";
        }
        else if(infoKind.equals("4"))
        {
            modelFiledir="重点项目.xls";
        }
        else if(infoKind.equals("5"))
        {
            modelFiledir="重点企业.xls";
        }
        else if(infoKind.equals("6"))
        {
            modelFiledir="重点院校.xls";
        }
        else if(infoKind.equals("7"))
        {
            modelFiledir="金融机构.xls";
        }
        else if(infoKind.equals("8"))
        {
            modelFiledir="通讯录.xls";
        }
        return modelFiledir;
    }

    //得到Word模板的文件名
    public static String getWordModelFileDir(String infoKind)
    {
        //定义模板和excel的映射关系
        String modelFiledir="";
        if(infoKind.equals("1"))
        {
            modelFiledir= "重点区域.doc";
        }
        else if(infoKind.equals("2"))
        {
            modelFiledir="重点领域.doc";
        }
        else if(infoKind.equals("3"))
        {
            modelFiledir="重点实验室.doc";
        }
        else if(infoKind.equals("4"))
        {
            modelFiledir="重点项目.doc";
        }
        else if(infoKind.equals("5"))
        {
            modelFiledir="重点企业.doc";
        }
        else if(infoKind.equals("6"))
        {
            modelFiledir="重点院校.doc";
        }
        else if(infoKind.equals("7"))
        {
            modelFiledir="金融机构.doc";
        }
        else if(infoKind.equals("8"))
        {
            modelFiledir="通讯录.doc";
        }
        return modelFiledir;
    }

    //根据字段的类型，得到导入的Excel数据和字段的映射关系Map
    public static Map<Integer,String> getImportXlsMap(String infoKind)
    {
        Map<Integer,String> map1=new HashedMap();
        if(infoKind.equals("1"))            //重点区域
        {
            map1.put(1,"str1");map1.put(2,"str2");map1.put(3,"str3");map1.put(4,"txt1");
            map1.put(5,"txt2");map1.put(6,"txt3");map1.put(7,"str6");map1.put(8,"str7");
            map1.put(9,"abc");
        }
        else if(infoKind.equals("2"))       //重点领域
        {
            map1.put(1,"str1");map1.put(2,"str3");map1.put(3,"str4");
            map1.put(4,"str5");map1.put(5,"str6");map1.put(6,"str7");map1.put(7,"str8");
            map1.put(8,"abc");
        }
        else if(infoKind.equals("3"))       //重点实验室
        {
            map1.put(1,"str1");map1.put(2,"str2");map1.put(3,"str3");map1.put(4,"str4");
            map1.put(5,"str5");map1.put(6,"str6");map1.put(7,"txt1");map1.put(8,"txt2");
            map1.put(9,"str9");map1.put(10,"str10");
            map1.put(11,"abc");
        }
        else if(infoKind.equals("4"))      //重点项目
        {
            map1.put(1,"str1");map1.put(2,"str2");map1.put(3,"str3");map1.put(4,"str4");
            map1.put(5,"str5");map1.put(6,"str6");map1.put(7,"str7");map1.put(8,"str8");
            map1.put(9,"txt1");map1.put(10,"txt2");map1.put(11,"str11");map1.put(12,"str12");
            map1.put(13,"abc");
        }
        else if(infoKind.equals("5"))      //重点企业
        {
            map1.put(1,"str1");map1.put(2,"str2");map1.put(3,"str3");map1.put(4,"str4");
            map1.put(5,"str5");map1.put(6,"str6");map1.put(7,"str7");map1.put(8,"str8");
            map1.put(9,"str9");map1.put(10,"str10");map1.put(11,"str11");map1.put(12,"str12");
            map1.put(13,"txt1");map1.put(14,"txt2");map1.put(15,"str15");map1.put(16,"str16");
            map1.put(17,"abc");
        }
        else if(infoKind.equals("6"))      //重点院校
        {
            map1.put(1,"str1");map1.put(2,"str2");map1.put(3,"str3");map1.put(4,"str4");
            map1.put(5,"str5");map1.put(6,"txt1");map1.put(7,"txt2");map1.put(8,"str8");
            map1.put(9,"str9");
            map1.put(10,"abc");
        }
        else if(infoKind.equals("7"))     //金融机构
        {
            map1.put(1,"str1");map1.put(2,"str2");map1.put(3,"str3");map1.put(4,"str4");
            map1.put(5,"str5");map1.put(6,"txt1");map1.put(7,"txt2");map1.put(8,"str8");
            map1.put(9,"str9");
            map1.put(10,"abc");
        }
        else if(infoKind.equals("8"))    //通讯录
        {
            map1.put(1,"str1");map1.put(2,"str2");map1.put(3,"str3");map1.put(4,"str4");
            map1.put(5,"str5");map1.put(6,"str6");map1.put(7,"str7");map1.put(8,"str8");
            map1.put(9,"str9");
        }
        return  map1;
    }

    //根据字段的类型，得到导出的Excel数据和字段的映射关系Map
    public static Map<Integer,String> getExportXlsMap(String infoKind)
    {
        Map<Integer,String> map1=new HashedMap();
        if(infoKind.equals("1"))            //重点区域
        {
            map1.put(1,"str1");map1.put(2,"str2");map1.put(3,"str3");map1.put(4,"txt1");
            map1.put(5,"txt2");map1.put(6,"txt3");map1.put(7,"str6");map1.put(8,"str7");
            map1.put(9,"abc");
        }
        else if(infoKind.equals("2"))       //重点领域
        {
            map1.put(1,"str1");map1.put(2,"str3");map1.put(3,"str4");
            map1.put(4,"str5");map1.put(5,"str6");map1.put(6,"str7");map1.put(7,"str8");
            map1.put(8,"abc");
        }
        else if(infoKind.equals("3"))       //重点实验室
        {
            map1.put(1,"str1");map1.put(2,"str2");map1.put(3,"str3");map1.put(4,"str4");
            map1.put(5,"str5");map1.put(6,"str6");map1.put(7,"txt1");map1.put(8,"txt2");
            map1.put(9,"str9");map1.put(10,"str10");
            map1.put(11,"abc");
        }
        else if(infoKind.equals("4"))      //重点项目
        {
            map1.put(1,"str1");map1.put(2,"str2");map1.put(3,"str3");map1.put(4,"str4");
            map1.put(5,"str5");map1.put(6,"str6");map1.put(7,"str7");map1.put(8,"str8");
            map1.put(9,"txt1");map1.put(10,"txt2");map1.put(11,"str11");map1.put(12,"str12");
            map1.put(13,"abc");
        }
        else if(infoKind.equals("5"))      //重点企业
        {
            map1.put(1,"str1");map1.put(2,"str2");map1.put(3,"str3");map1.put(4,"str4");
            map1.put(5,"str5");map1.put(6,"str6");map1.put(7,"str7");map1.put(8,"str8");
            map1.put(9,"str9");map1.put(10,"str10");map1.put(11,"str11");map1.put(12,"str12");
            map1.put(13,"txt1");map1.put(14,"txt2");map1.put(15,"str15");map1.put(16,"str16");
            map1.put(17,"abc");
        }
        else if(infoKind.equals("6"))      //重点院校
        {
            map1.put(1,"str1");map1.put(2,"str2");map1.put(3,"str3");map1.put(4,"str4");
            map1.put(5,"str5");map1.put(6,"txt1");map1.put(7,"txt2");map1.put(8,"str8");
            map1.put(9,"str9");
            map1.put(10,"abc");
        }
        else if(infoKind.equals("7"))     //金融机构
        {
            map1.put(1,"str1");map1.put(2,"str2");map1.put(3,"str3");map1.put(4,"str4");
            map1.put(5,"str5");map1.put(6,"txt1");map1.put(7,"txt2");map1.put(8,"str8");
            map1.put(9,"str9");
            map1.put(10,"abc");
        }
        else if(infoKind.equals("8"))    //通讯录
        {
            map1.put(1,"str1");map1.put(2,"str2");map1.put(3,"str3");map1.put(4,"str4");
            map1.put(5,"str5");map1.put(6,"str6");map1.put(7,"str7");map1.put(8,"str8");
            map1.put(9,"str9");
        }
        return  map1;
    }
}
