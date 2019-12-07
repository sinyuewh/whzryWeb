package com.jsj.webapi.utils;

import com.jsj.common.utils.KeyUtil;
import com.jsj.common.utils.MyStringUtil;
import com.jsj.webapi.config.AppWeb;
import com.jsj.webapi.config.ServerConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

/**
 * Created by jinshouji on 2019/4/24.
 */
@Component
public class FileUtils {

    private static final Logger logger = LoggerFactory.getLogger(CommApp.class);

    private static AppWeb appWeb;

    private static ServerConfig serverConfig;

    private static String contextPath;

    @Value("${server.context-path}")
    public void setContextPath(String contextPath)
    {
        FileUtils.contextPath=contextPath;
    }

    @Autowired
    public void setAppWeb(AppWeb appWeb) { FileUtils.appWeb=appWeb;}

    @Autowired
    public void setServerConfig(ServerConfig serverConfig){
        FileUtils.serverConfig=serverConfig;
    }

    /**
     * 将文件上传保存到saveDir目录，上传成功后，返回文件的相对url（不带文件服务器的根目录）
     * @param file
     * @param skey   -- 文件保存的前缀
     * @return
     */
    public static String UploadFile(MultipartFile file,String skey) throws  Exception
    {
        File path = new File(ResourceUtils.getURL("classpath:").getPath());
        if(!path.exists()) path = new File("");
        System.out.println("path:"+path.getAbsolutePath());

        //如果上传目录为/static/images/upload/，则可以如下获取：
        File upload = new File(path.getAbsolutePath(),"static/appFiles/");
        if(!upload.exists()) upload.mkdirs();
        System.out.println("upload url:"+upload.getAbsolutePath());

        String filename="";
        if(file!=null)
        {
            String serverPath=FileUtils.getFileRootPath();      //文件的根目录
            String saveDir=appWeb.getAppFile();              //App文件保存的目录
            //保存到服务器的文件名
            String serviceName = KeyUtil.get32UUID()
                    + file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));

            //如果skey不为空，则增加文件的前缀
            if(MyStringUtil.isNotEmpty(skey)) {
                serviceName=skey+"_"+serviceName;
            }

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
                    filename="/"+saveDir+"/"+serviceName;

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
     * 利用文件的完整url，得到文件的url信息，去掉http，ip和端口和前缀的信息的信息
     * @param fullUrl
     * @return
     */
    public static String GetFileUrlInfo(String fullUrl)
    {
        String url="";
        if(MyStringUtil.isNotEmpty(fullUrl))
        {
            int pos1=fullUrl.indexOf("//");
            if(pos1>0)
            {
                fullUrl=fullUrl.substring(pos1+2);
            }

            //修改于2019年7月29日
            int pos2=fullUrl.indexOf(contextPath);
            if(pos2>0)
            {
                fullUrl=fullUrl.substring(pos2+contextPath.length());
            }
            else
            {
                pos2=fullUrl.indexOf("/");
                if(pos2>0)
                {
                    fullUrl=fullUrl.substring(pos2);
                }
            }
            url=fullUrl;
        }
        return url;
    }


    //多个文件
    public static String GetMulFileUrlInfo(String fullUrl)
    {
        String url="";
        if(MyStringUtil.isNotEmpty(fullUrl))
        {
            String[] s1=fullUrl.split(",");
            boolean first=true;
            for(String m :s1)
            {
                if(first) {
                    first=false;
                    url=GetFileUrlInfo(m);
                }
                else
                {
                    url=url+";"+GetFileUrlInfo(m);
                }
            }
        }
        return url;
    }


    /**
     * 得到url的完整地址
     * @param fileDir
     * @return
     */
    public static String GetFileFullUrlInfo(String fileDir)
    {
        String url="";
        if(MyStringUtil.isNotEmpty(fileDir) && fileDir.equals("null")==false)
        {
            String ServerUrl= CommApp.getServerAddress(serverConfig.getServer(),serverConfig.getPort());
            String f1=fileDir.substring(0,1);
            if(f1.equals("/")==false)
            {
                fileDir="/"+fileDir;
            }
            if(fileDir.contains(contextPath)==false)
            {
                fileDir=contextPath+fileDir;
            }
            url=ServerUrl + fileDir;
        }
        return url;
    }


     //根据文件目录得到文件名的信息
     public static String GetFileName(String fileDir)
     {
         String fName = fileDir;
         String fileName = fName.substring(fName.lastIndexOf("/")+1);
         return fileName;
     }


     //得到static的物理根目录
     public static String getFileRootPath()
     {
         return ClassUtils.getDefaultClassLoader().getResource("").getPath()+"static/";
     }
}
