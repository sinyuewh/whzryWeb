package com.jsj.webapi.config;/**
 * Created by jinshouji on 2018/9/30.
 */

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;


/**
 * @author ：jinshouji
 * @create ：2018-09-30 13:57
 * @remark ：配置跨域解决方案
 **/
@Configuration
public class WebAppConfigurer extends WebMvcConfigurerAdapter
{
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                //.allowedOrigins("http://192.168.89.89")
                //rest集中请求方式
                .allowedMethods("GET", "POST","DELETE")
                .allowCredentials(true).maxAge(3600);  //.allowCredentials(true)
    }
}
