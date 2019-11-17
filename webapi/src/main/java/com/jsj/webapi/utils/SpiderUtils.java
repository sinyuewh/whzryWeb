package com.jsj.webapi.utils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
/**
 * Created by jinshouji on 2018/12/17.
 */

/**
 * 说明：根据读取质监局的二维码得到的URL，得到URL的输出流，并将输出流的信息解析出电梯二维码
 * 编码：周荣春
 * 时间：2018年12月17日
 */
public class SpiderUtils {

    public static Map<String,String>  getHtmlData(String url) throws Exception {

        Map<String,String> retMap = new HashMap<String,String>();
        String html = getResponseBodyByUrlAndMethodGet(url);
        Document doc = Jsoup.parse(html);
        Elements tbody = doc.select("tbody");
        Elements tr = tbody.select("tr");
        tr.remove(0);
        for (int i = 0; i < tr.size(); ++i){
            Element element = tr.get(i);
            Elements tds = element.select("td");
            Element element1 = tds.get(0);
            Element element2 = tds.get(1);
            retMap.put(element1.text().replace("&nbsp;","").replace(" ","").trim(),element2.text().replace("&nbsp;","").replace(" ","").trim());
        }
        //return JSONObject.fromObject(retMap).toString();
        return retMap;
    }

    private static String getResponseBodyByUrlAndMethodGet(String requestUrl) throws Exception{
        String response="";
        StringBuffer buffer = new StringBuffer();
        try{
            URL url = new URL(requestUrl);
            HttpURLConnection urlCon= (HttpURLConnection)url.openConnection();
            if(200==urlCon.getResponseCode()){
                InputStream is = urlCon.getInputStream();
                InputStreamReader isr = new InputStreamReader(is,"utf-8");
                BufferedReader br = new BufferedReader(isr);
                String str = null;
                while((str = br.readLine())!=null){
                    buffer.append(str);
                }
                br.close();
                isr.close();
                is.close();
                response = buffer.toString();
            }
        }catch(IOException e){
            e.printStackTrace();
        }
        return response;
    }
}
