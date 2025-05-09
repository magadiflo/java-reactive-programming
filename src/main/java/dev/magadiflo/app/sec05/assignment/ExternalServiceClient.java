package dev.magadiflo.app.sec05.assignment;

import dev.magadiflo.app.common.AbstractHttpClient;
import reactor.core.publisher.Mono;

import java.time.Duration;

public class ExternalServiceClient extends AbstractHttpClient {

    public Mono<String> getProductName(int productId) {
        String defaultPath = "/demo03/product/" + productId;
        String timeoutPath = "/demo03/timeout-fallback/product/" + productId;
        String emptyPath = "/demo03/empty-fallback/product/" + productId;
        return this.getProductName(defaultPath)
                .timeout(Duration.ofSeconds(2), getProductName(timeoutPath))
                .switchIfEmpty(getProductName(emptyPath));
    }

    private Mono<String> getProductName(String path) {
        return this.httpClient
                .get()
                .uri(path)
                .responseContent()
                .asString()
                .next();
    }

}
