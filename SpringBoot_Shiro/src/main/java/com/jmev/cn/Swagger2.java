package com.jmev.cn;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @Author: shaoxin
 * @Date: 2018/9/18 8:26
 * @Email:airvicii@163.com
 */
@Configuration
@EnableSwagger2
public class Swagger2
{
    // 创建rest api swagger 文档
    @Bean
    public Docket createRestApi(){
        return new Docket(DocumentationType.SWAGGER_2)// 文档格式swagger2
                .apiInfo(apiInfo())// 添加api信息
                .select()// ApiSelectorBuilder返回一个实例接口
                .apis(RequestHandlerSelectors.basePackage("com.jmev.cn.controller"))// api路径
                .paths(PathSelectors.any())// 查询任何路径
                .build();// 创建
    }

    // api 信息
    private ApiInfo apiInfo(){
        return new ApiInfoBuilder()
                .title("JMEV Car Info Monitor RESTful APIs")
                .description("JMEV CAR")
                .termsOfServiceUrl("www.jmev.com")
                .version("1.0")
                .build();
    }
}
