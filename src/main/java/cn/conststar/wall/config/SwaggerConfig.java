package cn.conststar.wall.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Profile;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket getDocker() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(changeInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("cn.conststar.wall.controller"))
                .build();

    }

    private ApiInfo changeInfo() {
        Contact author_contact = new Contact("赵国庆", "http://blog.conststar.cn", "admin@conststar.cn");
        return new ApiInfo(
                "星愿",
                "校园表白墙",
                "v2.0",
                "http://wall.conststar.cn",
                author_contact,
                "MIT License",
                "https://spdx.org/licenses/MIT.html",
                new ArrayList());

    }

}
