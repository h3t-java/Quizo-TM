package com.project.quizo.config;

import java.util.List;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;

import com.project.quizo.resource.UserLoginPostDTO;
import com.fasterxml.classmate.TypeResolver;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.ApiListingScannerPlugin;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.spring.web.readers.operation.CachingOperationNameGenerator;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

	@Autowired
	private TypeResolver typeResolver;

	@Bean
	@Autowired
	public Docket api(ServletContext servletContext) {
		return new Docket(DocumentationType.SWAGGER_2).groupName("Quizo")
				.additionalModels(typeResolver.resolve(UserLoginPostDTO.class)).select()
				.apis(RequestHandlerSelectors.basePackage("com.project.quizo.web.rest.controller"))
				.paths(PathSelectors.any()).build().apiInfo(apiEndPointsInfo()).useDefaultResponseMessages(false)
				.globalResponseMessage(RequestMethod.GET, globalResponseMessages())
				.globalResponseMessage(RequestMethod.POST, globalResponseMessages())
				.globalResponseMessage(RequestMethod.PUT, globalResponseMessages())
				.globalResponseMessage(RequestMethod.PATCH, globalResponseMessages())
				.globalResponseMessage(RequestMethod.DELETE, globalResponseMessages());
	}

	private ApiInfo apiEndPointsInfo() {
		return new ApiInfoBuilder().title("Quizo API").description("Quizo Management REST API").version("1.0.0")
				.build();
	}

	@Bean
	@Autowired
	public ApiListingScannerPlugin addExtraOperations(CachingOperationNameGenerator operationNames) {
		return new LoginLogoutListingScanner(operationNames, typeResolver);
	}

	private List<ResponseMessage> globalResponseMessages() {
		return List.of(new ResponseMessageBuilder().code(400).message("Bad request").build(),
				new ResponseMessageBuilder().code(401).message("User is not logged in").build(),
				new ResponseMessageBuilder().code(403)
						.message("User does not have necessary permission to access the resource or has not reset their password").build());
	}

}