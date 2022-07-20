package com.techelevator.tenmo.swagger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import java.util.Arrays;
import java.util.List;
/**
 * This implementation of SpringFoxConfig supports JSON Web Token (JWT) based authentication. To authenticate
 * do the following:
 *
 *  - Open the Swagger UI at http://localhost:8080/swagger-ui.html
 *  - Run the authentication-controller's /login service with a valid username and password
 *  - Copy just the token returned, without the quotes, to the clipboard. E.g. From this response
 *
 *     {
 *        "token": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ1c2VyMSIsImF1dGgiOiJST0xFX1VTRVIiLCJleHAiOjE2MjE0MjgyNDd9.TWTubIPgfNBqUjxGlaO0SG-WVuw678-K-psMGpV-oZqBnYdbGuuHt9_S8bn89sA9srSNeCxcfkE22KSNVUkLjQ",
 *        "user": {
 *           ...
 *        }
 *     }
 *
 *     copy
 *
 *       eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ1c2VyMSIsImF1dGgiOiJST0xFX1VTRVIiLCJleHAiOjE2MjE0MjgyNDd9.TWTubIPgfNBqUjxGlaO0SG-WVuw678-K-psMGpV-oZqBnYdbGuuHt9_S8bn89sA9srSNeCxcfkE22KSNVUkLjQ
 *
 *     to the clipboard
 *   - Note how the Authorize button in the top right of the Swagger UI has an open lock icon. This indicates there is no
 *     authenticated user at the moment. Click the button
 *   - In the dialog that's displayed, type the word "Bearer" (with an uppercase B and without quotes) followed by a space
 *     and then paste the token. For example:
 *       Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ1c2VyMSIsImF1dGgiOiJST0xFX1VTRVIiLCJleHAiOjE2MjE0MjgyNDd9.TWTubIPgfNBqUjxGlaO0SG-WVuw678-K-psMGpV-oZqBnYdbGuuHt9_S8bn89sA9srSNeCxcfkE22KSNVUkLjQ
 *   - Click the Authorize button. Then click the Done button
 *   - The Authorize button should now show a closed lock icon
 *   - Any services that require authentication should now have their Principal set to the user associated with the token
 *
 */
@Configuration
public class SpringFoxConfig {
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .securityContexts(Arrays.asList(securityContext()))
                .securitySchemes(Arrays.asList(apiKey()))
                .select()
                .apis(RequestHandlerSelectors.withClassAnnotation(RestController.class))
                .build();
    }
    private ApiKey apiKey() {
        return new ApiKey("JWT", "Authorization", "header");
    }
    private SecurityContext securityContext() {
        return SecurityContext.builder().securityReferences(defaultAuth()).build();
    }
    private List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return Arrays.asList(new SecurityReference("JWT", authorizationScopes));
    }
}