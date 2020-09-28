package com.project.quizo.config;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.springframework.http.HttpMethod;

import com.project.quizo.Resource.UserLoginPostDTO;
import com.fasterxml.classmate.TypeResolver;

import springfox.documentation.builders.OperationBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiDescription;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.ApiListingScannerPlugin;
import springfox.documentation.spi.service.contexts.DocumentationContext;
import springfox.documentation.spring.web.readers.operation.CachingOperationNameGenerator;

public final class LoginLogoutListingScanner implements ApiListingScannerPlugin {

	private final CachingOperationNameGenerator operationNames;

	private final TypeResolver typeResolver;

	public LoginLogoutListingScanner(CachingOperationNameGenerator operationNames, TypeResolver typeResolver) {
		this.operationNames = operationNames;
		this.typeResolver = typeResolver;
	}

	@Override
	public boolean supports(DocumentationType delimiter) {
		return DocumentationType.SWAGGER_2.equals(delimiter);
	}

	@Override
	public List<ApiDescription> apply(DocumentationContext context) {
		return List.of(
				new ApiDescription("Quizo", "/users/login", "User login endpoint",
						Collections.singletonList(new OperationBuilder(operationNames).codegenMethodNameStem("login")
								.method(HttpMethod.POST)
								.tags(Set.of("user-login"))
								.summary("User Login")
								.parameters(Collections.singletonList(new ParameterBuilder()
										.description("User login details")
										.type(typeResolver.resolve(UserLoginPostDTO.class)).name("UserLoginPostDTO")
										.parameterType("body").parameterAccess("access").required(true)
										.modelRef(new ModelRef("UserLoginPostDTO")).build()))
								.responseMessages(responseMessages()).build()),
						false),
				new ApiDescription("Quizo", "/users/logout", "User logout endpoint",
						Collections.singletonList(new OperationBuilder(operationNames).codegenMethodNameStem("logut")
								.method(HttpMethod.GET)
								.tags(Set.of("user-logut"))
								.summary("User Logout")
								.responseMessages(Collections.singleton(new ResponseMessageBuilder().code(204).build()))
								.build()),
						false));

	}

	private Set<ResponseMessage> responseMessages() {
		return Set.of(new ResponseMessageBuilder().code(200).message("Successfully logged in").build(),
				new ResponseMessageBuilder().code(400).message("Invalid login request").build(),
				new ResponseMessageBuilder().code(401).message("Bad credentials").build());
	}

}