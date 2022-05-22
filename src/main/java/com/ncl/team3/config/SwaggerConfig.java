package com.ncl.team3.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.VendorExtension;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;

/**
 * This class is a configuration class for configuring Swagger documents taken over by SpringBoot.
 * The class defines the information that needs to be displayed for the document,
 * such as the document name, the version of the document, permissions and other information
 * @author Lei Chen
 * @version 1.0
 * @StudentNumber: 200936497
 * @date 2022/04/25 12:55:43
 */
@Configuration
public class SwaggerConfig {
    @Value("${config.swagger3.flag}") //读取配置文件
    private boolean flag;

    @Bean
    public Docket docket(Environment environment){
        return new Docket(DocumentationType.OAS_30) // 指定3.0版本
                .groupName("文件上传")
                .apiInfo(apiInfo())
                .enable(flag)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.ncl.team3.controllers"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo(){
        String title= "API document for team3";
        String description = "This is API document for front end as a reference.";
        String version = "V1.0";
         String termsOfServiceUrl = "";
         Contact contact = new Contact("LeiChen","","L.chen55@newcastle.ac.uk");
         String license = "no licenses";
         String licenseUrl = "";
        // Collection< VendorExtension > vendorExtensions
        ApiInfo apiInfo = new ApiInfo(title,description,version,termsOfServiceUrl,contact,license,licenseUrl,new ArrayList<>());
        return apiInfo;
    }

}
