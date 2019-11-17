package com.jsj.webapi.service.sysframe;/**
 * Created by jinshouji on 2018/10/18.
 */

import com.jsj.common.service.BaseService;
import com.jsj.common.utils.MyStringUtil;
import com.jsj.common.utils.jpa.SearchField;
import com.jsj.common.utils.jpa.SearchOperator;
import com.jsj.webapi.dataobject.sysframe.Users;
import com.jsj.webapi.repository.sysframe.UsersRepository;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ：jinshouji
 * @create ：2018-10-18 16:30
 * 说明：通用操作的业务类
 **/
@Service
@Slf4j
public class CommApiService extends BaseService<Users, Integer> {

    //用来记录日志
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UsersRepository usersRepository;


    /**
     * 数据检查数据的唯一性
     * @param tablename
     * @param colname
     * @param checkvalue
     * @return 0 没有重复 1--重复
     */
    public int checkUniqueness(String tablename, String colname,
                               String checkvalue,int id)
    {
        //空值忽略检查
        if(MyStringUtil.isNotEmpty(tablename)==false || MyStringUtil.isNotEmpty(colname)==false
                || MyStringUtil.isNotEmpty(checkvalue)==false )
        {
            return 0;
        }
        else
        {
            List<SearchField> condition=new ArrayList<SearchField>();
            this.setTableName(tablename);
            condition.add(new SearchField(colname,checkvalue));
            if(id!=0)
            {
                condition.add(new SearchField("id",id, SearchOperator.NotEqual));
            }
            String fs="count(*) as count0" ;
            Page p1= this.getPageListMapData(fs,"",condition,1,1);
            if(p1.getTotalPages()>0)
            {
                return 1;
            }
        }
        return 0;
    }

    //新增数据的唯一性检查
    public int checkUniqueness(String tablename, String colname,
                               String checkvalue)
    {
        return this.checkUniqueness(tablename,colname,checkvalue,0);
    }


}
