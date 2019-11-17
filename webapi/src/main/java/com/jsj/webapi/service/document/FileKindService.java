package com.jsj.webapi.service.document;

import com.jsj.common.service.BaseService;
import com.jsj.common.utils.MyStringUtil;
import com.jsj.common.utils.jpa.SearchField;
import com.jsj.common.utils.jpa.SearchOperator;
import com.jsj.webapi.dataobject.document.FileKind;
import com.jsj.webapi.dto.document.FileKindDTO;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jinshouji on 2019/4/24.
 */
@Service
@Slf4j
public class FileKindService extends BaseService<FileKind, Integer> {

    //用来记录日志
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    //得到查询结果
    @Transactional
    public Page getSearch(FileKindDTO para, int pageIndex, int pageSize) throws Exception {
        //设置查询条件
        List<SearchField> condition = new ArrayList<SearchField>();

        //设置关联表的查询 lift inner join orgs left join users
        this.setTableName("FileKind");

        //设置查询条件
        if (MyStringUtil.isNotEmpty(para.getFileKind())) {
            condition.add(new SearchField("fileKind", "%" + para.getFileKind() + "%", SearchOperator.Contains));
        }
        if (MyStringUtil.isNotEmpty(para.getRemark())) {
            condition.add(new SearchField("remark", "%" + para.getRemark() + "%", SearchOperator.Contains));
        }

        //返回查询的列
        String fs = "num,id,fileKind,remark";

        Page p1 = this.getPageListMapData(fs, "num", condition, pageIndex, pageSize);
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
}
