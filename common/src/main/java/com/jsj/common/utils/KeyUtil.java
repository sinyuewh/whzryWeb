package com.jsj.common.utils;/**
 * Created by jinshouji on 2018/4/25.
 */

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

/**
 * @author ：jinshouji
 * @create ：2018-04-25 15:44
 * @Remark ：生出唯一编号的方法（如订单编号等）
 **/

public class KeyUtil {
    /**
     * @remark : 得到32位guid编码（随机字符串）
     */
    public static String get32UUID(){
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    /**
     * 生成唯一的主键
     * 格式: 时间格式+随机数的长度
     * @return
     */
    public static synchronized String genUniqueKey(String dateFormat,int randomLenth) {
        //根据随机数的长度设置随机数
        Random random = new Random();
        double min=Math.pow(10,randomLenth-1);
        double max=Math.pow(10,randomLenth)-min;
        Integer number = random.nextInt((int)max) + (int)min;

        //根据日期的格式设置日期
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        return sdf.format(new Date())+String.valueOf(number);
    }

    /**
     * 生成唯一的主键
     * 格式: 时间（年月日时分秒14位+随机数3位）
     * @return
     */
    public static synchronized String genUniqueKey()
    {
        return genUniqueKey("yyyyMMddHHmmss",3);
    }

    /**
     * 生成唯一的主键
     * 格式: 时间（年月日时分秒毫秒17位+随机数3位）
     * @return
     */
    public static synchronized String genUniqueKeyToMS()
    {
        return genUniqueKey("yyyyMMddHHmmssSSS",3);
    }

}
