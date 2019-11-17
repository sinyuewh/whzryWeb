package com.jsj.common.utils;/**
 * Created by jinshouji on 2018/4/26.
 */

import org.apache.commons.codec.digest.DigestUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author ：jinshouji
 * @create ：2018-04-26 13:41
 * 备注：字符串实用工具类
 **/

public class MyStringUtil {

    //用于加密和解密的字符串
    private static final String keyStr = "2235494930046549176L";

    //#region 字符串简单的加密和解密
    //字符串的简单加密和解密（加密2次，就是解密）
    public static String EnString(String strCryptThis,String strKey)
    {
        String strEncrypted = "";
        char iCryptChar, iKeyChar, iStringChar;
        int j = 0;
        for (int i = 0; i < strCryptThis.length(); i++)
        {
            iKeyChar = strKey.charAt(i);
            iStringChar = strCryptThis.charAt(i);

            iCryptChar = (char)(iKeyChar ^ iStringChar);
            strEncrypted = strEncrypted + iCryptChar;

            j++;
            if (j == strKey.length())
            {
                j = 0;
            }
        }
        return strEncrypted;
    }

    //字符串默认的加密和解密方法
    public static String EnString(String strCryptThis)
    {
        String str1 = EnString(strCryptThis, keyStr);
        return str1;
    }
    // #endregion

    //#region md5加密
    public static String md5(String text, String key) throws Exception {
        //加密后的字符串
        String encodeStr= DigestUtils.md5Hex(text + key);
        return encodeStr;
    }

    public static String md5(String text) throws Exception {
       return md5(text,keyStr);
    }

    public static boolean verifyMd5(String text, String key, String encodeStr) throws Exception {
        //根据传入的密钥进行验证
        String md5Text = md5(text, key);
        if(md5Text.equalsIgnoreCase(encodeStr))
        {
            return true;
        }
        return false;
    }

    public static boolean verifyMd5(String text, String encodeStr) throws Exception {
        return verifyMd5(text,keyStr,encodeStr);
    }

    //判断字符串是否为空
    public static boolean isNotEmpty(Object s1)
    {
        if(s1 != null && s1.toString().length() != 0)
        {
            return true;
        }
        return false;
    }

    //判断字符串是否为空
    public static boolean isEmpty(Object s1)
    {
        if(s1 != null && s1.toString().length() != 0)
        {
            return false;
        }
        return true;
    }
    //endregion

    /**
     * 判断是否为一个合适的日期字符串
     * @param strDate
     * @return
     */
    public static boolean isDate(String strDate) {
        Pattern pattern = Pattern
                .compile("^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))(\\s(((0?[0-9])|([1-2][0-3]))\\:([0-5]?[0-9])((\\s)|(\\:([0-5]?[0-9])))))?$");
        Matcher m = pattern.matcher(strDate);
        if (m.matches()) {
            return true;
        } else {
            return false;
        }
    }

    //判断手机号码是否正确
    public static boolean isPhone(String phone) {
        String regex = "^((13[0-9])|(14[5,7,9])|(15([0-3]|[5-9]))|(166)|(17[0,1,3,5,6,7,8])|(18[0-9])|(19[8|9]))\\d{8}$";
        if (phone.length() != 11) {
            return false;
        } else {
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(phone);
            boolean isMatch = m.matches();
            return isMatch;
        }
    }


    //寻找子串的数量
    public static int findSubStrCount(String src,String find){
        int o = 0;
        int index=-1;
        while((index=src.indexOf(find,index))>-1){
            ++index;
            ++o;
        }
        return o;
    }


    /**
     * 得到最大长度的字符串
     * @param src
     * @param maxLength
     * @return
     */
    public static String getMaxLengthSubStr(String src,String ext,int maxLength)
    {
        if(src.length()>maxLength){
            return src.substring(0,maxLength)+ext;
        }else{
            return src;
        }
    }


}
