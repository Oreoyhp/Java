package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
/**
 * @author Administrator
 * @version 1.0.0
 * @2019年11月27日 下午2:20:49
 */
@Configuration
@EnableSwagger2
public class Swagger2Config {
	/**
	 * 添加摘要信息(Docket)
	 */
	@Bean
	public Docket controllerApi() {
		return new Docket(DocumentationType.SWAGGER_2)
				.apiInfo(new ApiInfoBuilder().title("标题：应答程序")
						.description("描述：用于模拟应答机器人.").contact(new Contact("crsc", null, null))
						.version("版本号:1.0").build())
				.select().apis(RequestHandlerSelectors.basePackage("com.example.demo")).paths(PathSelectors.any())
				.build();
	}
}