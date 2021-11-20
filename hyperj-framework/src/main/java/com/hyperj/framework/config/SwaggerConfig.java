package com.hyperj.framework.config;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.ApiSelectorBuilder;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/** Swagger3 **/
// @Configuration 用于定义配置类，可替换 xml 配置文件
// @Configuration 注解类中可以声明一个或多个 @Bean 方法
@Configuration
@EnableOpenApi
public class SwaggerConfig {

    /** 是否开启swagger */
    @Value("${swagger.enabled}")
    private boolean enabled;

    @Autowired HyperjConfig hyperjConfig;

    // swagger的配置信息都要分装在Docker对象里，Docker对象最终会返回给SpringBoot，从而配置好Swagger
    // @Bean是一个方法级别上的注解(产生一个bean的方法，并且交给Spring容器管理)
    // 主要用在@Configuration注解的类里,也可以用在@Component注解的类里
    @Bean
    public Docket createRestApi(){

        // 创建一个空的Docker对象
        Docket docket = new Docket(DocumentationType.OAS_30)
                // 是否启用Swagger
                .enable(enabled);

        /**
         * 配置基本信息
         */
        ApiInfoBuilder builder = new ApiInfoBuilder();
        builder.title(hyperjConfig.getName())
                .description(hyperjConfig.getDescription())
                .version(hyperjConfig.getVersion());

        ApiInfo info = builder.build();

        // 将info传入Docker对象
        docket.apiInfo(info);

        /**
         * 设置哪些类的哪些方法需要添加到Swagger界面
         */
        ApiSelectorBuilder selectorBuilder = docket.select();
        // 设置路径（所有包下的所有类）
        selectorBuilder.paths(PathSelectors.any());
        // 在包下的所有类中添加了@ApiOperation注解的方法才会被添加到Swagger界面
        selectorBuilder.apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class));
        // 配置好以后重新构建Docker对象
        docket = selectorBuilder.build();

        /**
         * 下面的语句是开启对JWT的支持，当用户用Swagger调用受JWT认证保护的方法，
         * 配置Swagger可以在Header中拿到令牌字符串token
         */
        // Authorization: Bearer 模式
        List<SecurityScheme> apiKeys = Collections.singletonList(HttpAuthenticationScheme.JWT_BEARER_BUILDER
                .name("token")
                .build());
        docket.securitySchemes(apiKeys);

        /**
         * 如果用户JWT认证通过,则在Swagger中全局有效
         * 配置令牌字符串token的作用域（全局）
         */
        // 创建认证对象
        AuthorizationScope scope = new AuthorizationScope("global","accessEverything");
        // 封装认证对象到数组中
        AuthorizationScope[] scopes = {scope};
        // 将数组封装到SecurityReference对象中
        SecurityReference reference = new SecurityReference("token",scopes);
        // 将SecurityReference封装到ArrayList中
        List refList = new ArrayList();
        refList.add(reference);
        // 将ArrayList封装成SecurityContext对象
        SecurityContext context = SecurityContext.builder().securityReferences(refList).build();
        // 再将SecurityContext封装到List才能给Docket对象使用
        List ctxList =  new  ArrayList();
        ctxList.add(context);
        // 将ctxList交给Docket
        docket.securityContexts(ctxList);


        return docket;
    }
}
