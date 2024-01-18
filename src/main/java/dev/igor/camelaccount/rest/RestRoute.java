package dev.igor.camelaccount.rest;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import dev.igor.camelaccount.dto.AccountAvailableBalanceResponse;
import dev.igor.camelaccount.dto.AccountRequest;
import dev.igor.camelaccount.dto.AccountResponse;

@Component
public class RestRoute extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        restConfiguration()
            .apiContextRouteId("swagger")
            .component("servlet")
            .contextPath("")
            .apiContextPath("/swagger")
            .apiProperty("api.title", "Swagger Service User")
            .apiProperty("api.description", "Swagger Service User")
            .apiProperty("api.version", "Swagger Service User")
            .apiProperty("host", "localhost")
            .apiProperty("port", "8080")
            .apiProperty("schemes", "http")
            .bindingMode(RestBindingMode.auto);

        rest("/accounts")
            .post()
                .id("rest-account-create")
                .description("Method responsible to create account")
                .bindingMode(RestBindingMode.auto)

                .type(AccountRequest.class)
                .produces(MediaType.APPLICATION_JSON_VALUE)
                .outType(AccountResponse.class)

                .responseMessage()
                    .code(HttpStatus.CREATED.value())
                    .message("CREATED")
                .endResponseMessage()
            .to("direct:post-accounts")
            .get("/{accountCode}")
                .id("rest-account-find-by-account-code")
                .description("Method responsible for find by account by accountCode")
                .bindingMode(RestBindingMode.auto)

                .produces(MediaType.APPLICATION_JSON_VALUE)
                .outType(AccountResponse.class)

                .responseMessage()
                    .code(HttpStatus.OK.value())
                    .message("OK")
                .endResponseMessage()
            .to("direct:get-accounts-account-code")
            .get("/available-balance")
                .id("rest-account-available")
                .description("Method responsible for view available account")
                .bindingMode(RestBindingMode.auto)

                .produces(MediaType.APPLICATION_JSON_VALUE)
                .outType(AccountAvailableBalanceResponse.class)

                .responseMessage()
                    .code(HttpStatus.OK.value())
                    .message("OK")
                .endResponseMessage()
            .to("direct:get-accounts");
    }
}
