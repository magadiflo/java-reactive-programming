package dev.magadiflo.app.sec13.client;

import dev.magadiflo.app.common.AbstractHttpClient;
import reactor.core.publisher.Mono;

public class ExternalServiceClient extends AbstractHttpClient {

    public Mono<String> getBook() {
        return this.httpClient
                .get()
                .uri("/demo07/book")
                .responseContent()
                .asString()
                .next();
    }

}
