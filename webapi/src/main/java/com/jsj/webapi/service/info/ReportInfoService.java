package com.jsj.webapi.service.info;

import com.jsj.common.service.BaseService;
import com.jsj.common.utils.MapUtil;
import com.jsj.common.utils.MyStringUtil;
import com.jsj.common.utils.jpa.SearchField;
import com.jsj.common.utils.jpa.SearchOperator;
import com.jsj.webapi.dataobject.info.InfoData;
import com.jsj.webapi.dataobject.info.ReportInfoData;
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
public class ReportInfoService extends BaseService<ReportInfoData, Integer> {
    //用来记录日志
    private Logger logger = LoggerFactory.getLogger(this.getClass());


    /**
     * 根据数据的ids，得到按组分类的List
     * @param ids
     * @return
     */
    @Transactional
    public Dictionary<String,List<Map<String,Object>>> getKindGroupListByToDoList(String ids)
    {
        Dictionary<String,List<Map<String,Object>>> dic1=new Hashtable<>();
        for(int i=1;i<=10;i++)
        {
            String sql="select * from InfoData where id in (select parent from reportinfodata where infoKind='"+i+"') order by infoKind";

        }

        return dic1;
    }

}
