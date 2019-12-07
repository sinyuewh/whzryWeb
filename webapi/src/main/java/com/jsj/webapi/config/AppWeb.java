package com.jsj.webapi.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Created by jinshouji on 2018/10/16.
 */
@Data
@Component
@ConfigurationProperties(prefix="appWeb") //接收application.yml中的appWeb下面的属性
public class AppWeb {
    private  int pageSize;              //读取配置文件中pageSize的值
    private String templates;            //#模板文件保存的目录
    private String uploadFile;           //#用户上传的其他文件（例如年检报告，事故记录，电梯知识库）存放的目录
    private String appFile;              //#App上传的文件目录
    private String tempFile;             //临时文件的保存目录
    private String usePermissions;      //用户的权限配置
}
