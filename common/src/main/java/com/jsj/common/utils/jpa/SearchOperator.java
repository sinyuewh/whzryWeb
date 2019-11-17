package com.jsj.common.utils.jpa;/**
 * Created by jinshouji on 2018/4/26.
 */

import com.jsj.common.utils.CodeEnum;
import lombok.Data;
import lombok.Getter;

/**
 * @author ：jinshouji
 * @create ：2018-04-26 15:13
 * @Remark ：查询操作枚举
 **/

@Getter
public enum SearchOperator implements CodeEnum
{
    Equal(0," = "),
    BiggerAndEqual(1," >= "),
    Bigger(2," > "),
    SmallerAndEqual(3," <= "),
    Smaller(4," < "),
    NotEqual(5," != "),
    Contains(6," like "),
    Collection(7," in "),
    NullValue(8," is null "),
    NotNullValue(9," is not null "),
    UserDefine(10," ")
    ;
    private Integer code;
    private String message;

    SearchOperator(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
