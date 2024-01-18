package dev.igor.camelaccount.route;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import dev.igor.camelaccount.dto.AccountAvailableBalanceResponse;
import dev.igor.camelaccount.error.ApplicationError;

@Component
public class GetAccountRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        from("direct:get-accounts")
            .doTry()
                .removeHeaders("CamelHttp*")
                .setHeader(Exchange.HTTP_METHOD, constant(HttpMethod.GET.toString()))
                .setHeader(Exchange.HTTP_PATH, simple("/accounts/available-balance?accountCode=${header.accountCode}&amount=${header.amount}"))
                .to("{{account.url}}")
                .unmarshal().json(JsonLibrary.Jackson, AccountAvailableBalanceResponse.class)
            .endDoTry()
            .doCatch(Exception.class)
                .process(new ApplicationError())
            .end()
        .end();
    }
}
