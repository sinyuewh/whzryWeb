package com.jsj.webapi.utils;

import com.jsj.common.bean.HttpResult;
import com.jsj.common.utils.HttpResultUtil;
import com.jsj.common.utils.KeyUtil;
import com.jsj.webapi.dto.info.InfoKindDTO;
import com.jsj.webapi.enums.ErrorEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.lang.reflect.Type;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by jinshouji on 2019/4/24.
 */
@Component
public class CommApp {

    private static final Logger logger = LoggerFactory.getLogger(CommApp.class);

    private static RedisTemplate redisTemplate;

    @Autowired
    public void setRedisTemplate(RedisTemplate redisTemplate) {
        CommApp.redisTemplate = redisTemplate;
    }

    private static String contextPath;

    @Value("${server.context-path}")
    public void setContextPath(String contextPath)
    {
        CommApp.contextPath=contextPath;
    }

    //项目的错误处理（枚举）
    public static HttpResult error(ErrorEnum errorEnum)
    {
        int code=errorEnum.getCode();
        String msg=errorEnum.getMessage();
        return HttpResultUtil.error(code,msg);
    }

    /**
     * 将文件上传保存到saveDir目录，上传成功后，返回文件的相对url（不带文件服务器的根目录）
     * @param file
     * @param serverPath  : 文件服务器的根目录
     * @param saveDir  : 文件保存的目录
     * @return
     */
    public static String UploadFile(MultipartFile file,
                                    String serverPath,
                                    String saveDir )
    {
        String filename="";
        if(file!=null)
        {
            //保存到服务器的文件名
            String serviceName = KeyUtil.get32UUID()
                    + file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));

            File tempFile = new File(serverPath+saveDir + File.separator + serviceName);

            if (!tempFile.getParentFile().exists()) {
                tempFile.getParentFile().mkdirs();
            }

            if (!file.isEmpty()) {
                try {
                    BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(tempFile));
                    // "d:/"+file.getOriginalFilename() 指定目录
                    out.write(file.getBytes());
                    out.flush();
                    out.close();
                    //filename=tempFile.getPath();
                    filename=serviceName;

                } catch (FileNotFoundException e) {
                    e.printStackTrace();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return filename;
    }

    /**
     * 说明：根据服务器的IP或域名和端口得到其访问的地址
     * @param ServerIP
     * @param port
     * @return
     */
    public static String getServerAddress(String ServerIP,int port)
    {
        String address="";
        if( ServerIP.indexOf("http://")<0 && ServerIP.indexOf("https://")<0 )
        {
            address="http://"+ServerIP;
        }
        else
        {
            address=ServerIP;
        }

        if(port==80 || port==443 || port==0 ) //表示采用默认的端口
        {
            return address;
        }
        else
        {
            return address+":" + port ;
        }
    }

    /**
     * List的复制
     * @param Source   数据源
     * @param type     目标对象类型
     * @param maxCount 最大的行数
     * @return
     * @throws Exception
     */
    public static List getCopyFrom(List Source,Type type,Integer maxCount) throws Exception
    {
        if(Source!=null && Source.size()>0)
        {
            List list=new ArrayList();
            int index=0;
            for(Object object:Source)
            {
                if(index>maxCount) break;
                Object obj1=Class.forName(type.getTypeName()).newInstance();
                BeanUtils.copyProperties(object,obj1);
                list.add(obj1);
                index++;
            }
            return list;
        }
        return null;
    }


    public static List getCopyFrom(List Source,Type type) throws Exception
    {
        if(Source!=null && Source.size()>0) {
            return CommApp.getCopyFrom(Source,type,Source.size());
        }
        return null;
    }
    /**
     * 随机生成6位验证码
     * @return
     */
    public static String getRandomCode(Integer code){
        Random random = new Random();
        StringBuffer result= new StringBuffer();
        for (int i=0;i<code;i++){
            result.append(random.nextInt(10));
        }
        return result.toString();
    }

    //验证手机号码是否正确
    public static String isPhone(String phone) {
        String error=null;
        String regex = "^((13[0-9])|(14[5,7,9])|(15([0-3]|[5-9]))|(166)|(17[0,1,3,5,6,7,8])|(18[0-9])|(19[8|9]))\\d{8}$";
        if (phone.length() != 11) {
            error="手机号应为11位数";
        } else {
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(phone);
            boolean isMatch = m.matches();
            if (!isMatch) {
                error="请填入正确的手机号";
            }
        }
        return error;
    }

    /**
     * 得到数据类别的Map
     * @Author 金寿吉
     * @Date  2019-12-4
     * @return
     */
    public static Map<Integer,InfoKindDTO> getInfoMap()
    {
       Map<Integer,InfoKindDTO> map=new HashMap<>();
       InfoKindDTO data1=new InfoKindDTO("重点区域","重点区域.xls","重点区域.doc");
       map.put(1,data1);

       InfoKindDTO data3=new InfoKindDTO("重点实验室","重点实验室.xls","重点实验室.doc");
       map.put(3,data3);

       InfoKindDTO data4=new InfoKindDTO("重点项目","重点项目.xls","重点项目.doc");
       map.put(4,data4);

       InfoKindDTO data5=new InfoKindDTO("重点企业","重点企业.xls","重点企业.doc");
       map.put(5,data5);

       InfoKindDTO data6=new InfoKindDTO("重点院校","重点院校.xls","重点院校.doc");
       map.put(6,data6);

       InfoKindDTO data7=new InfoKindDTO("金融机构","金融机构.xls","金融机构.doc");
       map.put(7,data7);

       return map;
    }
}
