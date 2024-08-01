package com.moli.features.doct;

import com.github.xiaoymin.knife4j.spring.extension.OpenApiExtensionResolver;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import springfox.documentation.builders.*;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.schema.ScalarType;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author moli
 * @time 2024-08-01 14:33:42
 * @description Knife4j 配置
 */
@ConditionalOnProperty(
        name = {"knife4j.enable"},
        havingValue = "true"
)
@Configuration
@EnableOpenApi
@EnableConfigurationProperties({DocProperties.class})
public class Knife4jConfig {

    /**
     * open api extension by knife4j.
     */
    private final OpenApiExtensionResolver openApiExtensionResolver;

    private final DocProperties docProperties;

    @Autowired
    public Knife4jConfig(OpenApiExtensionResolver openApiExtensionResolver,
                         DocProperties docProperties) {
        this.openApiExtensionResolver = openApiExtensionResolver;
        this.docProperties = docProperties;
    }

    /**
     * @return swagger config
     */
    @Bean
    public Docket openApi() {
        String groupName = "Test Group";
        return new Docket(DocumentationType.OAS_30)
                .groupName(groupName)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                .paths(PathSelectors.any())
                .build()
                .globalRequestParameters(getGlobalRequestParameters())
                .globalResponses(HttpMethod.GET, getGlobalResponse())
                .extensions(openApiExtensionResolver.buildExtensions(groupName))
                .extensions(openApiExtensionResolver.buildSettingExtensions());
    }

    /**
     * @return global response code->description
     */
    private List<Response> getGlobalResponse() {
        return Arrays.stream(HttpStatus.values())
                .map(code -> new ResponseBuilder()
                        .code(code.value() + "")
                        .description(code.getReasonPhrase())
                        .build()
                )
                .collect(Collectors.toList());
    }

    /**
     * @return global request parameters
     */
    private List<RequestParameter> getGlobalRequestParameters() {
        List<RequestParameter> parameters = new ArrayList<>();
        parameters.add(new RequestParameterBuilder()
                .name("AppKey")
                .description("App Key")
                .required(false)
                .in(ParameterType.QUERY)
                .query(q -> q.model(m -> m.scalarModel(ScalarType.STRING)))
                .required(false)
                .build());
        return parameters;
    }

    /**
     * @return api info
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title(docProperties.getTitle())
                .description(docProperties.getDescription())
                .contact(new Contact(docProperties.getAuthor(), docProperties.getAddress(), docProperties.getEmail()))
                .termsOfServiceUrl(docProperties.getTermsOfServiceUrl())
                .version(docProperties.getVersion())
                .build();
    }
}
