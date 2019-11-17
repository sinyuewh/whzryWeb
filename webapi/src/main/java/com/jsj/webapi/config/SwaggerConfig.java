package com.jsj.webapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.async.DeferredResult;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Created by jinshouji on 2018/10/22.
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket ProductApi()
    {
        return new Docket(DocumentationType.SWAGGER_2)
                .genericModelSubstitutes(DeferredResult.class)
                .useDefaultResponseMessages(false)
                .forCodeGeneration(false)
                .pathMapping("/")
                .select()
                .build()
                .apiInfo(productApiInfo());
    }

    private ApiInfo productApiInfo() {
        ApiInfo apiInfo = new ApiInfo("中锐源信息管理系统文档",
                "文档描述。。。",
                "1.0.0",
                "API TERMS URL",
                "sinyuewh@163.com",
                "license",
                "license url");
        return apiInfo;
    }
}
