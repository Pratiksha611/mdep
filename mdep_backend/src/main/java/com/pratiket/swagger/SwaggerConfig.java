package com.pratiket.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author Pratiksha Deshmukh
 * created on 07-08-2021
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig extends WebMvcConfigurationSupport
{

    @Bean
    public Docket mdepSwaggerApi()
    {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.pratiket"))
                .paths(PathSelectors.regex("/.*")).build().apiInfo(metaData());
    }

    private ApiInfo metaData()
    {
        return new ApiInfoBuilder().title("MDEP Backend Service API's")
                .description("REST API for MDEP Backend Service")
                .version("1.0.0")
                .build();
    }

    @Override
    protected void addResourceHandlers(final ResourceHandlerRegistry registry)
    {
        registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
    }
}
