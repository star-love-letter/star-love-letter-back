package cn.conststar.wall.config;

import cn.conststar.wall.response.ResponseCodeEnums;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseBuilder;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Response;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.*;

@Configuration
@EnableOpenApi
@EnableWebMvc
public class ConfigSwagger {

    @Bean
    public Docket createRestApi() {
        List<Response> globalResponses = new ArrayList<>();
        for (ResponseCodeEnums item : ResponseCodeEnums.values()) {
            globalResponses.add(new ResponseBuilder()
                    .code(String.valueOf(item.getCode()))
                    .description(item.getDesc())
                    .build());
        }

        return new Docket(DocumentationType.OAS_30).pathMapping("/")
                .useDefaultResponseMessages(true)
                .globalResponses(HttpMethod.POST,globalResponses)
                .globalResponses(HttpMethod.GET,globalResponses)
                .globalResponses(HttpMethod.PUT,globalResponses)
                .globalResponses(HttpMethod.DELETE,globalResponses)

                // 将api的元信息设置为包含在json ResourceListing响应中。
                .apiInfo(apiInfo())
                .select() // 指定需要发布到Swagger的接口目录，不支持通配符
                .apis(RequestHandlerSelectors.basePackage("cn.conststar.wall.controller"))
                .paths(PathSelectors.any())
                .build()
                // 支持的通讯协议集合
                .protocols(newHashSet("https", "http"));
    }


    /**
     * API 页面上半部分展示信息
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("星愿墙")
                .description("接口文档")
                .contact(new Contact("开发者邮箱", null, "admin@conststar.cn"))
                .version("1.2.5")
                .build();
    }

    @SafeVarargs
    private final <T> Set<T> newHashSet(T... ts) {
        if (ts.length > 0) {
            return new LinkedHashSet<>(Arrays.asList(ts));
        }
        return null;
    }


}