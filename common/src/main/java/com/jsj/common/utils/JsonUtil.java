package com.jsj.common.utils;/**
 * Created by jinshouji on 2018/4/25.
 */

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.json.JSONObject;

import net.minidev.json.JSONArray;
import net.minidev.json.JSONValue;

/**
 * @author ：jinshouji
 * @create ：2018-04-25 15:43
 * @remark: Json 实用类
 **/

public class JsonUtil {
    /**
     * 将对象转成json字符串
     * @param object
     * @return
     */
    public static String toJson(Object object) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setPrettyPrinting();
        Gson gson = gsonBuilder.create();
        return gson.toJson(object);
    }

    public static String pojo2json(Object pojo){
        String jsonStr = JSONValue.toJSONString(pojo);
        return jsonStr;
    }

    /**
     * json
     * @param json
     * @return
     */
    public static Object json2pojo(String json){
        Object obj = null;
        try {
            obj = JSONValue.parse(json);
         } catch (Exception e) {
            e.printStackTrace();
        }
        return obj;
    }
    /**
     * list
     * @param <T>
     * @param list
     * @return
     */
    public static <T> String list2json(List<T> list){
        String jsonStr = JSONValue.toJSONString(list);
        return jsonStr;
    }
    /**
     * json
     * @param json
     * @return
     */
    public static List json2list(String json){
        Object obj=JSONValue.parse(json);
        JSONArray array=(JSONArray)obj;
        return array;
    }

    public static Map jsonToObject(String jsonStr) throws Exception {
        JSONObject jsonObj = new JSONObject(jsonStr);
        Iterator<String> nameItr = jsonObj.keys();
        String name;
        Map<String, String> outMap = new HashMap<String, String>();
        while (nameItr.hasNext()) {
            name = nameItr.next();
            outMap.put(name, jsonObj.getString(name));
        }
        return outMap;
    }
}
