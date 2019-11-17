package com.jsj.common.utils.jpa;/**
 * Created by jinshouji on 2018/4/26.
 */

import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;

/**
 * @author ：jinshouji
 * @create ：2018-04-26 10:04
 **/

public class SearchFilter
{
    public String fieldName;
    public Object value;
    public Operator operator;
    public ConjunctionType conjunctionType;

    public SearchFilter(String fieldName, Operator operator, Object value) {
        this.fieldName = fieldName;
        this.value = value;
        this.operator = operator;
        this.conjunctionType = ConjunctionType.ADD;
    }

    public SearchFilter(String fieldName, Operator operator, Object value, ConjunctionType conjunctionType) {
        this.fieldName = fieldName;
        this.value = value;
        this.operator = operator;
        this.conjunctionType = conjunctionType;
    }

    /**
     * searchParams中key的格式为OPERATOR_FIELDNAME
     */
    public static Map<String, SearchFilter> parse(Map<String, Object> searchParams) {
        Map<String, SearchFilter> filters = Maps.newHashMap();

        for (Map.Entry<String, Object> entry : searchParams.entrySet()) {
            // 过滤掉空值
            String key = entry.getKey();
            Object value = entry.getValue();
            if (value == null || "".equals(value)) {
                continue;
            }

            // 拆分operator与filedAttribute
            String[] names = StringUtils.split(key, "_");
            if (names.length != 2 && names.length != 3) {
                throw new IllegalArgumentException(key + " is not a valid search filter name");
            }
            String filedName = names[1];
            Operator operator = Operator.valueOf(names[0]);
            SearchFilter filter;
            if (names.length == 3) {
                // 创建searchFilter
                filter = new SearchFilter(filedName, operator, value, ConjunctionType.valueOf(names[2]));
            } else {
                // 创建searchFilter
                filter = new SearchFilter(filedName, operator, value);
            }

            filters.put(key, filter);
        }

        return filters;
    }

    public enum Operator {
        EQ, NE, LIKE, GT, LT, GTE, LTE, IN, NOTIN, ISNULL, ISNOTNULL, NOTLIKE
    }

    // add by chenbo;
    public enum ConjunctionType {
        ADD, OR
    }
}
