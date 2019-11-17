package com.jsj.webapi.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by jinshouji on 2018/11/29.
 */
@Component("jpushConfig")
public class JpushConfig {

    // 读取极光配置信息中的用户名密码
    @Value("${jpush.appKey}")
    private String appkey;
    @Value("${jpush.masterSecret}")
    private String masterSecret;
    @Value("${jpush.liveTime}")

    private String liveTime;

    public String getAppkey() {

        return appkey;
    }

    public String getMasterSecret() {

        return masterSecret;
    }

    public void setLiveTime(String liveTime) {

        this.liveTime = liveTime;
    }

    public String getLiveTime() {
        return liveTime;
    }

    public void setAppkey(String appkey) {
        this.appkey = appkey;
    }

    public void setMasterSecret(String masterSecret) {
        this.masterSecret = masterSecret;
    }
}
