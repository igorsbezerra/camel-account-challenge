package dev.igor.camelaccount.route;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import dev.igor.camelaccount.dto.AccountResponse;
import dev.igor.camelaccount.error.ApplicationError;

@Component
public class GetAccountByAccountCodeRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        from("direct:get-accounts-account-code")
            .doTry()
                .removeHeaders("CamelHttp*")
                .setHeader(Exchange.HTTP_METHOD, constant(HttpMethod.GET.toString()))
                .setHeader(Exchange.HTTP_PATH, simple("/accounts/${header.accountCode}"))
                .to("{{account.url}}")
                .unmarshal().json(JsonLibrary.Jackson, AccountResponse.class)
            .endDoTry()
            .doCatch(Exception.class)
                .process(new ApplicationError())
            .end()
        .end();
    }
}
