package com.jsj.common.service;/**
 * Created by jinshouji on 2018/4/26.
 */

import org.apache.commons.lang3.StringUtils;

import java.util.Iterator;
import java.util.Map;

/**
 * @author ：jinshouji
 * @create ：2018-04-26 10:12
 **/

public abstract class SqlBuilderBase
{
    protected abstract String buildFromClause();

    protected abstract Map<String,Object> buildWhereParameter();
    protected abstract String buildSelectClause();


    public int pageSize;
    public int pageIndex;
    private boolean isPage=false;

    private String m_fromSQL;
    private Map<String, Object> m_whereParameter;
    private String m_selectClause;

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    private String orderBy;


    public SqlBuilderBase() {

    }

    public void setPage(Integer pageIndex,Integer pageSize){
        this.pageIndex=pageIndex;
        this.pageSize=pageSize;
        this.isPage=true;

    }

    protected  void init(){
        m_fromSQL= buildFromClause();
        m_whereParameter=buildWhereParameter();
        m_selectClause=buildSelectClause();
        System.out.print(getCountSQL());
    }

    public String getCountSQL() {
        return "select count(*) " + m_fromSQL;
    }

    public String getSelectFormSQL() {
        String sql= m_selectClause + m_fromSQL;
        if(!StringUtils.isBlank(orderBy)){
            sql=sql+"  order by "+orderBy+" ";
        }
        return sql;
    }



    public void putParameter(javax.persistence.Query query) {
        Iterator<Map.Entry<String, Object>> iterator = m_whereParameter.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Object> next = iterator.next();
            query.setParameter(next.getKey(), next.getValue());
        }
    }
}

