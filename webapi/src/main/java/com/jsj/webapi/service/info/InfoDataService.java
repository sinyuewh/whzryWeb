package com.jsj.webapi.service.info;

import com.jsj.common.service.BaseService;
import com.jsj.common.utils.MapUtil;
import com.jsj.common.utils.MyStringUtil;
import com.jsj.common.utils.jpa.SearchField;
import com.jsj.common.utils.jpa.SearchOperator;
import com.jsj.webapi.dataobject.info.InfoData;
import com.jsj.webapi.dto.info.InfoDTO;
import com.jsj.webapi.exception.MyException;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Created by jinshouji on 2019/4/24.
 */
@Service
@Slf4j
public class InfoDataService extends BaseService<InfoData, Integer> {
    //用来记录日志
    private Logger logger = LoggerFactory.getLogger(this.getClass());


    //得到特定字段唯一的查询结果
    @Transactional
    public Page getSearch(InfoDTO para, String fields,int pageIndex,int pageSize) throws Exception {
        //设置查询条件
        List<SearchField> condition = new ArrayList<SearchField>();

        //设置关联表的查询 lift inner join orgs left join users
        this.setTableName("infoData");

        //设置查询条件
        if (MyStringUtil.isNotEmpty(para.getInfoKind())) {
            condition.add(new SearchField("infoData.infoKind",  para.getInfoKind()));
        }
        if (MyStringUtil.isNotEmpty(para.getStr1())) {
            condition.add(new SearchField("infoData.str1", "%" + para.getStr1() + "%", SearchOperator.Contains));
        }

        if (MyStringUtil.isNotEmpty(para.getStr2())) {
            condition.add(new SearchField("infoData.str2", "%" + para.getStr2() + "%", SearchOperator.Contains));
        }
        if (MyStringUtil.isNotEmpty(para.getStr3())) {
            condition.add(new SearchField("infoData.str3", "%" + para.getStr3() + "%", SearchOperator.Contains));
        }
        if (MyStringUtil.isNotEmpty(para.getStr4())) {
            condition.add(new SearchField("infoData.str4", "%" + para.getStr4() + "%", SearchOperator.Contains));
        }
        if (MyStringUtil.isNotEmpty(para.getStr5())) {
            condition.add(new SearchField("infoData.str5", "%" + para.getStr5() + "%", SearchOperator.Contains));
        }
        if (MyStringUtil.isNotEmpty(para.getStr6())) {
            condition.add(new SearchField("infoData.str6", "%" + para.getStr6() + "%", SearchOperator.Contains));
        }
        if (MyStringUtil.isNotEmpty(para.getStr7())) {
            condition.add(new SearchField("infoData.str7", "%" + para.getStr7() + "%", SearchOperator.Contains));
        }
        if (MyStringUtil.isNotEmpty(para.getStr8())) {
            condition.add(new SearchField("infoData.str8", "%" + para.getStr8() + "%", SearchOperator.Contains));
        }
        if (MyStringUtil.isNotEmpty(para.getStr9())) {
            condition.add(new SearchField("infoData.str9", "%" + para.getStr9() + "%", SearchOperator.Contains));
        }
        if (MyStringUtil.isNotEmpty(para.getStr10())) {
            condition.add(new SearchField("infoData.str10", "%" + para.getStr10() + "%", SearchOperator.Contains));
        }
        if (MyStringUtil.isNotEmpty(para.getIds())) {
            condition.add(new SearchField("infoData.id in ("+para.getIds()+")", "",SearchOperator.UserDefine ));
        }

        //返回查询的列
        String fs = "distinct "+fields;

        Page p1 = this.getPageListMapData(fs, "", condition, pageIndex, pageSize);
        return p1;
    }


    //得到查询结果
    @Transactional
    public Page getSearch(InfoDTO para, int pageIndex,int pageSize) throws Exception {
        //设置查询条件
        List<SearchField> condition = new ArrayList<SearchField>();

        //设置关联表的查询 lift inner join orgs left join users
        this.setTableName("infoData");

        //设置查询条件
        if (MyStringUtil.isNotEmpty(para.getInfoKind())) {
            condition.add(new SearchField("infoData.infoKind",  para.getInfoKind()));
        }
        if (MyStringUtil.isNotEmpty(para.getStr2())) {
            condition.add(new SearchField(para.getStr1(), "%" + para.getStr2() + "%", SearchOperator.Contains));
        }

        /*
        if (MyStringUtil.isNotEmpty(para.getStr2())) {
            condition.add(new SearchField("infoData.str2", "%" + para.getStr2() + "%", SearchOperator.Contains));
        }
        if (MyStringUtil.isNotEmpty(para.getStr3())) {
            condition.add(new SearchField("infoData.str3", "%" + para.getStr3() + "%", SearchOperator.Contains));
        }
        if (MyStringUtil.isNotEmpty(para.getStr4())) {
            condition.add(new SearchField("infoData.str4", "%" + para.getStr4() + "%", SearchOperator.Contains));
        }
        if (MyStringUtil.isNotEmpty(para.getStr5())) {
            condition.add(new SearchField("infoData.str5", "%" + para.getStr5() + "%", SearchOperator.Contains));
        }
        if (MyStringUtil.isNotEmpty(para.getStr6())) {
            condition.add(new SearchField("infoData.str6", "%" + para.getStr6() + "%", SearchOperator.Contains));
        }
        if (MyStringUtil.isNotEmpty(para.getStr7())) {
            condition.add(new SearchField("infoData.str7", "%" + para.getStr7() + "%", SearchOperator.Contains));
        }
        if (MyStringUtil.isNotEmpty(para.getStr8())) {
            condition.add(new SearchField("infoData.str8", "%" + para.getStr8() + "%", SearchOperator.Contains));
        }
        if (MyStringUtil.isNotEmpty(para.getStr9())) {
            condition.add(new SearchField("infoData.str9", "%" + para.getStr9() + "%", SearchOperator.Contains));
        }
        if (MyStringUtil.isNotEmpty(para.getStr10())) {
            condition.add(new SearchField("infoData.str10", "%" + para.getStr10() + "%", SearchOperator.Contains));
        }
        if (MyStringUtil.isNotEmpty(para.getIds())) {
            condition.add(new SearchField("infoData.id in ("+para.getIds()+")", "",SearchOperator.UserDefine ));
        }
        */

        //返回查询的列
        String fs = "infoData.*";

        Page p1 = this.getPageListMapData(fs, "infoData.id desc", condition, pageIndex, pageSize);
        return p1;
    }


    //得到字段的唯一列表的数据
    @Transactional
    public Page getFieldSearch(String infoKind,String fieldName) throws Exception {
        //设置查询条件
        List<SearchField> condition = new ArrayList<SearchField>();

        //设置关联表的查询 lift inner join orgs left join users
        this.setTableName("infoData");

        //设置查询条件
        if (MyStringUtil.isNotEmpty(infoKind)) {
            condition.add(new SearchField("infoData.infoKind",  infoKind));
        }

        condition.add(new SearchField(fieldName +" is not null",  "",SearchOperator.UserDefine));

        //返回查询的列
        String fs = "distinct "+fieldName;

        Page p1 = this.getPageListMapData(fs, "", condition, 1, 5000);
        return p1;
    }


    /**
     * 删除多条数据
     * @param ids
     * @return
     */
    @Transactional
    public int deletMulDevice(String ids)
    {
        int result=0;
        if(MyStringUtil.isNotEmpty(ids))
        {
            String[] s1=ids.split(",");
            for(String m :s1)
            {
                this.delete(Integer.parseInt(m));
                result++;
            }
        }
        return result;
    }


    //将Xls对象的数据导入到数据库
    @Transactional
    public String ImportXlsToInfoDB(Workbook wb,String infoKind,
                                Integer begRow,
                                Map<Integer,String> colDic) throws  Exception
    {
        //得到第一个shell
        String[] cols=new String[]{};

        Sheet sheet=wb.getSheetAt(0);
        //得到Excel的行数
        int totalRows=sheet.getPhysicalNumberOfRows();
        //总列数
        int totalCells = 0;
        //得到Excel的列数(前提是有行数)，从第二行算起
        if(totalRows>=2 && sheet.getRow(1) != null){
            totalCells=sheet.getRow(1).getPhysicalNumberOfCells();
        }
        //将数据保存到一个List内
        List<Map<String,Object>> list1=new ArrayList<Map<String,Object>>();

        //循环Excel行数,检查数据的行（从第2行开始读取数据）
        int errorRow=0;
        int errorCol=0;
        try {
            for (int r = begRow; r < totalRows; r++) {
                String rowMessage = "";
                Row row = sheet.getRow(r);
                if (row == null) {
                    return "第" + (r + 1) + "行数据有问题，请仔细检查！";
                }
                Map<String, Object> data = new HashMap<String, Object>();
                //检查数据的列
                errorCol=0;
                for (int c = 0; c < totalCells; c++) {
                    Cell cell = row.getCell(c);
                    if(colDic.containsKey(c)) {
                        if (null != cell) {
                            data.put(colDic.get(c), cell.getStringCellValue());
                        } else {
                            return "第" + (c + 1) + "列数据有问题，请仔细检查；";
                        }
                    }
                    errorCol++;
                }
                list1.add(data);
                errorRow++;
            }
        }
        catch(Exception err)
        {
            throw new MyException(1000,errorRow+"行"+errorCol+"列数据有问题！");
        }
        //针对list进行遍历，处理其中的数据，将数据保存到
        for(Map<String,Object> data : list1)
        {
            InfoDTO dto1=new InfoDTO();
            MapUtil.fillBeanByMap(data,dto1);

            //创建对象和赋值
            InfoData target=new InfoData();
            BeanUtils.copyProperties(dto1,target);
            target.setDefault();
            target.setInfoKind(infoKind);
            target=this.save(target);
        }
        return "";          //返回空字符串，表示成功
    }
}
