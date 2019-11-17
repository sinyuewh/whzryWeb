package com.jsj.webapi.utils;

import com.jsj.common.utils.MyStringUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by jinshouji on 2018/12/22.
 * 高德地图的Util
 *
 */
public class GaodeMapUtil {

    //高德地图的key
    private static String key="1c4ca0cb10f3163ceed3042806463ebe";  //1c4ca0cb10f3163ceed3042806463ebe

    //根据地理信息，得到经纬度等信息
    public static  Object getLocationInfo(String city,String address,String orgName)
    {
        RestTemplate restTemplate=new RestTemplate();
        String url1="https://restapi.amap.com/v3/geocode/geo?address={address}&city={city}&output=JSON&key={key}";
        Map<String, String> map = new HashMap<>();
        map.put("address", address);
        map.put("city",city);
        map.put("key",key);

        JSONObject json=restTemplate.getForEntity(url1,JSONObject.class,map).getBody();
        Integer status=Integer.parseInt(json.get("status").toString());
        if(status==1)
        {
            Object obj=getLocationObject(json.get("geocodes"));
            if(obj==null)    //如利用位置得到Location，则利用orgName
            {
                if(MyStringUtil.isNotEmpty(orgName)) {
                    map.put("address", orgName);
                    json = restTemplate.getForEntity(url1, JSONObject.class, map).getBody();
                    status = Integer.parseInt(json.get("status").toString());
                    if (status == 1) {
                        obj = json.get("geocodes");
                    }
                }
            }
            return obj;
        }
        else
        {
            return null;
        }
    }

    //利用经纬度得到完整的位置信息的数据  longitude 经度 latitude
    public static Object getAddressInfo(String location)
    {
        RestTemplate restTemplate=new RestTemplate();
        String url="https://restapi.amap.com/v3/geocode/regeo?key={key}&location={location}&poitype=&radius=1000&extensions=all&batch=false&roadlevel=0";
        Map<String, String> map = new HashMap<>();
        map.put("location",location);
        map.put("key",key);

        JSONObject json=restTemplate.getForEntity(url,JSONObject.class,map).getBody();
        Integer status=Integer.parseInt(json.get("status").toString());
        if(status==1)
        {
            return json.get("regeocode");
        }
        else
        {
            return null;
        }
    }

    //利用经纬度得到完整的Address对象
    public static Object getAddresInfo(String longitude,String latitude)
    {
        String location=longitude+","+latitude;
        return getAddressInfo(location);
    }


    //利用经纬度得到Addresss信息
    public static String getSimpleAddresInfo(String longitude,String latitude)
    {
        String location=longitude+","+latitude;
        Object object=getAddresInfo(longitude,latitude);
        String result="";
        if(object!=null)
        {
            JSONObject object1=(JSONObject)object;
            if(object1!=null && object1.containsKey("formatted_address"))
            {
                result= object1.get("formatted_address").toString();
            }
        }
        return result;
    }


    //得到Location 的对象
    private static Object getLocationObject(Object geocodes)
    {
        Object obj1=null;
        if (geocodes != null)
        {
            JSONArray object = (JSONArray) geocodes;
            if (object.size() > 0) {
                JSONObject location = (JSONObject) object.get(0);
                if (location != null && location.size() > 0 && location.containsKey("location")) {
                    String s1 = location.getString("location");
                    if (MyStringUtil.isNotEmpty(s1) && s1.contains(",")) {
                        obj1=geocodes;
                    }
                }
            }
        }
        return  obj1;
    }

}
