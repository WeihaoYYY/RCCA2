package com.example.rcca2.config;

import io.swagger.annotations.ApiOperation;
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

@EnableSwagger2
@Configuration
public class SwaggerConfig {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
//                .apis(RequestHandlerSelectors.basePackage("com.example.rcca2"))
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class)) // 只选择有@ApiOperation注解的方法
                .paths(PathSelectors.any())
                .build().apiInfo(apiInfoMetaData());
    }

    private ApiInfo apiInfoMetaData() {

        return new ApiInfoBuilder().title("R2 API Documentation")
                .description("Describing the purpose of the API")
                .license("Apache 2.0")
                .licenseUrl("http://www.apache.org/licenses/LICENSE-2.0.html")
                .contact(new Contact("Weihao Yue", "3.104.152.3", "yue032994@outlook.com"))
                .version("1.0.0")
                .build();
    }
}