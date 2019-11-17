package com.jsj.common.utils;

/**
 * @author ：jinshouji
 * @create ：2018-04-25 14:24
 * @Remark ：Code和message枚举的实用工具类
 **/
public class EnumUtil
{
    /**
     * 根据枚举的Code得到对应的文本
      * @param code
     * @param enumClass
     * @param <T>
     * @return
     */
    public static <T extends CodeEnum> T getByCode(Integer code, Class<T> enumClass) {
        for (T each: enumClass.getEnumConstants()) {
            if (code.equals(each.getCode())) {
                return each;
            }
        }
        return null;
    }
}
