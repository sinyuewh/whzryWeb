package com.jsj.common.config;/**
 * Created by jinshouji on 2018/10/24.
 */

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author ：jinshouji
 * @create ：2018-10-24 15:12
 **/
@Data
@Component("systemConfig")
@ConfigurationProperties(prefix = "classpath:system.properties")
public class SystemConfig {
    String redisHost;

    String redisPort;

    String redisPassword;

    String accessKeyId;

    String accessKeySecret;

    String ossURL;

    String ossBulketName;

    String ossEndpoint;

    String fileTemp;

    String fileDir;

    String filePre;

    String domain;

    String mobileProject;

    String backendProject;

    String websocket;

    String msgServer;

    String poiSearch;

    String poiKey;

    public static SystemConfig getInstance() {
        return (SystemConfig) SpringApplicationContextHolder.getSpringBean("systemConfig");
    }
}
