//package com.example.tatoebaproject.config;
//
////import com.google.common.base.Predicate;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
////import static com.google.common.base.Predicates.or;
//import java.util.function.Predicate;
//
//import static org.mockito.AdditionalMatchers.or;
//
//@Configuration
//@EnableSwagger2
//public class SwaggerConfig {
//
//    @Bean
//    public Docket postsApi() {
//        return new Docket(DocumentationType.SWAGGER_2).groupName("public-api")
//                .apiInfo(apiInfo())
//                .select()
//                .paths(PathSelectors.any())
//                .build();
//    }
//
//    private Predicate<String> postPaths() {
//        return or(regex("/api/posts.*"), regex("/.*"));
//    }
//
//    private ApiInfo apiInfo() {
//        return new ApiInfoBuilder().title("Tatoeba Api")
//                .description("Tatoeba API reference for developers")
//                .termsOfServiceUrl("")
//                .licenseUrl("").version("1.0").build();
//    }
//
//
//}
