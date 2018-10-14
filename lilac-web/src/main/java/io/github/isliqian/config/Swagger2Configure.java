package io.github.isliqian.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Created by LiQian_Nice on 2018/3/20
 */
@Configuration  //spring boot配置注解
@EnableSwagger2//启用swagger2功能注解
public class Swagger2Configure {

    @Bean
    public Docket createRestApi() {//api文档实例

        return new Docket(DocumentationType.SWAGGER_2)//文档类型：DocumentationType.SWAGGER_2
                .apiInfo(apiInfo())//api信息
                .select()//构建api选择器
                //.apis(RequestHandlerSelectors.basePackage("com.example.*"))//api选择器选择api的包
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())//api选择器选择包路径下任何api显示在文档中
                .build()//创建文档
                .useDefaultResponseMessages(false);
    }

    private ApiInfo apiInfo() {//接口的相关信息
        return new ApiInfoBuilder()
                .title("LiLac API - TEST")
                .description("Base Spring boot2")
                .termsOfServiceUrl("https://github.com/isliqian/luma")
                .contact(new Contact("李前Nice","https://github.com/isliqian","qianboy233@gmail.com"))
                .version("1.0")
                .build();
    }
}
