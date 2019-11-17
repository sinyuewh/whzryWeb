package com.jsj.webapi.utils;

import com.jsj.common.utils.jpa.PageUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import javax.persistence.EntityManager;
import java.lang.reflect.Field;
import java.math.BigInteger;
import java.util.*;

/**
 * Created by optimum on 2018-02-02.
 */
public class SQLUtil<T> {
    //#region 转MAP
    public static Page<Map<String, Object>> getResult(String sql,
                                                      Map<String, Object> parameter,
                                                      LinkedHashMap<String, String> vlaueConfig,
                                                      Integer pageIndex,
                                                      Integer pageSize,
                                                      EntityManager entityManager) {

        PageRequest pageRequest = PageUtil.buildPageRequest(pageIndex, pageSize);


        long count = getCount(sql, parameter, entityManager);


        List resultList = getReslut(getSQLHeader(vlaueConfig), sql, pageIndex, pageSize, parameter, entityManager);

        List<Map<String, Object>> result = convert(resultList, vlaueConfig);

        PageImpl page = new PageImpl(result, pageRequest, count);
        return page;
    }

    public static List<Map<String, Object>> getResult(String sql,
                                                      Map<String, Object> parameter,
                                                      LinkedHashMap<String, String> vlaueConfig,
                                                      EntityManager entityManager) {


        List resultList = getReslut(getSQLHeader(vlaueConfig), sql, parameter, entityManager);

        List<Map<String, Object>> result = convert(resultList, vlaueConfig);

        return result;
    }

    public static String getSQLHeader(LinkedHashMap<String, String> vlaueConfig) {
        Iterator<Map.Entry<String, String>> iterator = vlaueConfig.entrySet().iterator();
        StringBuffer sb = new StringBuffer();
        sb.append("select ");
        int size = vlaueConfig.size();
        int i = 0;
        while (iterator.hasNext()) {
            i++;
            Map.Entry<String, String> next = iterator.next();
            if (i == size) {
                sb.append(next.getValue() + " " + next.getKey() + " ");
            } else {
                sb.append(next.getValue() + " " + next.getKey() + " , ");
            }


        }
        return sb.toString();
    }

    public static List<Map<String, Object>> convert(List resultList, LinkedHashMap<String, String> vlaueConfig) {

        List<Map<String, Object>> result = new LinkedList<>();

        for (Object obj : resultList) {
            Map<String, Object> map = new LinkedHashMap<>();
            Object[] o = (Object[]) obj;

            int i = 0;
            Iterator<Map.Entry<String, String>> iterator = vlaueConfig.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, String> next = iterator.next();
                map.put(next.getKey(), o[i]);

                i++;
            }
            result.add(map);

        }
        return result;


    }

    public static List getReslut(String sqlHeader,
                                  String sql,
                                  Map<String, Object> parameter,
                                  EntityManager entityManager) {
        javax.persistence.Query queryList = entityManager.createNativeQuery(sqlHeader + sql);

        putParameter(parameter, queryList);


        List resultList = queryList.getResultList();

        return resultList;
    }

    public static List getReslut(String sqlHeader,
                                  String sql,
                                  int pageIndex,
                                  int pageSize,
                                  Map<String, Object> parameter,
                                  EntityManager entityManager) {
        javax.persistence.Query queryList = entityManager.createNativeQuery(sqlHeader + sql);
        queryList.setFirstResult((pageIndex-1) * pageSize).setMaxResults(pageSize);
        putParameter(parameter, queryList);



        List resultList = queryList.getResultList();

        return resultList;
    }

    public static long getCount(String sql,
                                 Map<String, Object> parameter,
                                 EntityManager entityManager) {
        String sqlCount = " select count(*) as count " + sql;
        javax.persistence.Query query = entityManager.createNativeQuery(sqlCount);
        putParameter(parameter, query);
        BigInteger count = (BigInteger) query.getSingleResult();
        return count.longValue();
    }


    public static void putParameter(Map<String, Object> parmaeter, javax.persistence.Query query) {
        Iterator<Map.Entry<String, Object>> iterator = parmaeter.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Object> next = iterator.next();
            query.setParameter(next.getKey(), next.getValue());


        }
    }
    //#endregion
    // 初始化目标
    private  Class<T>  tClass;
    public SQLUtil(Class<T> newClass) {
        this.tClass = newClass;
    }
    public SQLUtil() {
    }
    //#region 获取对象
    public  Page getEntityResult(String sql,
                                 Map<String, Object> parameter,
                                 LinkedHashMap<String, String> vlaueConfig,
                                 Integer pageIndex,
                                 Integer pageSize,
                                 EntityManager entityManager) {

        PageRequest pageRequest = PageUtil.buildPageRequest(pageIndex, pageSize);
        long count = SQLUtil.getCount(sql, parameter, entityManager);
        List resultList = SQLUtil.getReslut(SQLUtil.getSQLHeader(vlaueConfig), sql, pageIndex, pageSize, parameter, entityManager);
        List<T> result = ConvertToEntityList(resultList, vlaueConfig);
        PageImpl page = new PageImpl(result, pageRequest, count);
        return page;
    }

    /** 将的到的结果转为实体
     * @param resultList
     * @param vlaueConfig
     * @return
     */
    private List<T> ConvertToEntityList(List resultList, LinkedHashMap<String, String> vlaueConfig) {
        //最后返回的值
        List<T> result =new  ArrayList<>();
        //获取实体类，对象
        Class entity = null;
        try {
            entity = Class.forName(tClass.getName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        //获取实体类属性
        Field[] fieldArray = entity.getFields();
        for (Object obj : resultList) {
            //创建实体对象
            T tempObj = null;
            try {
                tempObj = (T) entity.newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            Object[] o = (Object[]) obj;
            int i = 0;
            //根据vlaueConfig找到对应的resultList对象中的值
            //根据vlaueConfig的Key与T类的属性做对比，将值赋给T的对象
            Iterator<Map.Entry<String, String>> iterator = vlaueConfig.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, String> next = iterator.next();
                for (Field fd: fieldArray)
                {
                    if(fd.getName().toLowerCase().equals(next.getKey().toLowerCase())){
                        fd.setAccessible(true);
                        try {
                            fd.set(tempObj, o[i]);
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    }
                }
                i++;
            }
            result.add(tempObj);
        }
        return result;
    }
    //#endregion
}

