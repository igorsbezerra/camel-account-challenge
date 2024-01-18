package dev.igor.camelaccount.route;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import dev.igor.camelaccount.dto.AccountRequest;
import dev.igor.camelaccount.dto.AccountResponse;
import dev.igor.camelaccount.error.ApplicationError;

@Component
public class PostAccountRoute extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        from("direct:post-accounts")
            .doTry()
                .removeHeaders("CamelHttp*")
                .setHeader(Exchange.HTTP_METHOD, constant(HttpMethod.POST.toString()))
                .setHeader(Exchange.HTTP_PATH, simple("/accounts"))
                .marshal().json(JsonLibrary.Jackson, AccountRequest.class)
                .to("{{account.url}}")
                .unmarshal().json(JsonLibrary.Jackson, AccountResponse.class)
            .endDoTry()
            .doCatch(Exception.class)
                .process(new ApplicationError())
            .end()
        .end();
    }
}
