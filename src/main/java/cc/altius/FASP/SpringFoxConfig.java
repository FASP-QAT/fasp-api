/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP;


/**
 *
 * @author akil
 */
//@Configuration
//@EnableSwagger2WebMvc
//@Import(SpringDataRestConfiguration.class)
//public class SpringFoxConfig extends WebMvcConfigurationSupport {
//
//    @Bean
//    public Docket api() {
//        Docket d = new Docket(DocumentationType.SWAGGER_2)
//                .select()
//                .apis(RequestHandlerSelectors.basePackage("cc.altius.FASP.rest.controller"))
//                .paths(PathSelectors.any())
//                .build().apiInfo(apiInfo());
//        List<ResponseMessage> responses = new LinkedList<>();
//        responses.add(new ResponseMessageBuilder()
//                .code(500)
//                .message("500 message")
//                .responseModel(new ModelRef("SampleData"))
//                .build());
//        responses.add(new ResponseMessageBuilder()
//                .code(403)
//                .message("Forbidden!")
//                .build()
//        );
//        d.useDefaultResponseMessages(false)
//                .globalResponseMessage(RequestMethod.GET, responses)
//                .globalResponseMessage(RequestMethod.PUT, responses)
//                .globalResponseMessage(RequestMethod.POST, responses);
//        return d;
//    }
//
//    @Bean
//    public UiConfiguration tryItOutConfig() {
//        final String[] methodsWithTryItOutButton = {};
//        return UiConfigurationBuilder.builder().supportedSubmitMethods(methodsWithTryItOutButton).build();
//    }
//
//    private ApiInfo apiInfo() {
//        return new ApiInfo(
//                "Sample API by Akil",
//                "How to use Swagger ",
//                "1234",
//                "Terms of service",
//                new Contact("Altius", "qat.altius.cc", "qat@altius.cc"),
//                "QAT License of API", "API license URL for QAT", Collections.emptyList());
//    }
//}
