package com.jsj.common.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * Created by jinshouji on 2018/4/30.
 */

@Data
@Component("myRedisConfig")
@PropertySource("classpath:redis.properties")
public class RedisConfig {

    @Value("${host}")
    private  String host;

    @Value("${port}")
    private  String port;

    @Value("${timeout}")
    private  int timeout;

    @Value("${password}")
    private String password;

    @Value("${dbindex}")
    private  int dbindex;

    public static RedisConfig getInstance() {
        return (RedisConfig) SpringApplicationContextHolder.getSpringBean("myRedisConfig");
    }
}
