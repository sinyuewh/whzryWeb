package com.jsj.common.utils.jpa;/**
 * Created by jinshouji on 2018/4/26.
 */

import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * @author ：jinshouji
 * @create ：2018-04-26 15:12
 **/

@Data
public class SearchField
{
    private String fieldName;
    private Object fieldValue;

    private SearchOperator operator = SearchOperator.Equal;
    private boolean isAnd=true;
    private boolean addBeginLeftBracket=false;      //增加开始的左括号，用于处理or的条件
    private boolean addEndRightBracket=false;        //增加结束的右括号，用于处理or的条件

    public SearchField(String fieldName, Object fieldValue, SearchOperator operator) {
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
        this.operator = operator;
    }

    public SearchField(String fieldName, Object fieldValue) {
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }

    public SearchField() {}

    /**
     * 根据查询列表形成sql语句中的where后面的条件
     * @param condition
     * @return 拼接后的条件字符串
     */
    public static String getSqlWhere(List<SearchField> condition)
    {
        StringBuilder sb=new StringBuilder();
        boolean first=true;
        if(null!=condition && condition.size()>0)
        {
            for(SearchField item:condition) {
                if(StringUtils.isEmpty(item.fieldName)==false && item.fieldValue!=null
                        && StringUtils.isEmpty(item.fieldName.toString())==false)
                {
                    if(item.addBeginLeftBracket) sb.append(" ( ");
                    if (first ==false )  //表示不是第一个条件
                    {
                        if(item.isAnd) {
                            sb.append(" And ");
                        }
                        else
                        {
                            sb.append(" OR ") ;
                        }
                    }
                    else
                    {
                        first=false;
                    }
                    //==========================================================================
                    //拼接 sname =:sname, age>=:age的条件
                    sb.append(" ( ");
                    sb.append(item.fieldName);
                    if(item.operator.equals(SearchOperator.Collection)) {
                        sb.append(" in ( ").append(":").append(item.fieldName).append(" ) ");   // in ( :ids )
                    }
                    else if(item.operator.equals(SearchOperator.UserDefine))
                    {
                        sb.append("");
                    }
                    else
                    {
                        sb.append(item.operator.getMessage()).append(":").append(item.fieldName);
                    }
                    sb.append(" ) ");
                    //==================================================================================
                    if(item.addEndRightBracket) sb.append(" ) ");
                }
            }
        }
        return sb.toString();
    }


}
