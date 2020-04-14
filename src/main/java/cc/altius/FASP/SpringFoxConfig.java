/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.data.rest.configuration.SpringDataRestConfiguration;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.UiConfiguration;
import springfox.documentation.swagger.web.UiConfigurationBuilder;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;


/**
 *
 * @author akil
 */
@Configuration
@EnableSwagger2WebMvc
@Import(SpringDataRestConfiguration.class)
public class SpringFoxConfig extends WebMvcConfigurationSupport {

    @Bean
    public Docket api() {
        Docket d = new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("cc.altius.FASP.rest.controller"))
                .paths(PathSelectors.any())
                .build().apiInfo(apiInfo());
        List<ResponseMessage> responses = new LinkedList<>();
        responses.add(new ResponseMessageBuilder()
                .code(500)
                .message("500 message")
                .responseModel(new ModelRef("SampleData"))
                .build());
        responses.add(new ResponseMessageBuilder()
                .code(403)
                .message("Forbidden!")
                .build()
        );
        d.useDefaultResponseMessages(false)
                .globalResponseMessage(RequestMethod.GET, responses)
                .globalResponseMessage(RequestMethod.PUT, responses)
                .globalResponseMessage(RequestMethod.POST, responses);
        return d;
    }

    @Bean
    public UiConfiguration tryItOutConfig() {
        final String[] methodsWithTryItOutButton = {};
        return UiConfigurationBuilder.builder().supportedSubmitMethods(methodsWithTryItOutButton).build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfo(
                "Sample API by Akil",
                "How to use Swagger ",
                "1234",
                "Terms of service",
                new Contact("Altius", "qat.altius.cc", "qat@altius.cc"),
                "QAT License of API", "API license URL for QAT", Collections.emptyList());
    }
}
