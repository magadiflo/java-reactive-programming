package dev.magadiflo.app.sec09.assignment;

import dev.magadiflo.app.common.AbstractHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

public class ExternalServiceClient extends AbstractHttpClient {

    private static final Logger log = LoggerFactory.getLogger(ExternalServiceClient.class);

    public Mono<Product> getProduct(int productId) {
        return Mono.zip(
                this.getProductName(productId),
                this.getReview(productId),
                this.getPrice(productId)
        ).map(t -> new Product(t.getT1(), t.getT2(), t.getT3()));
    }

    private Mono<String> getProductName(int productId) {
        return this.remoteService("/demo05/product/" + productId);
    }

    private Mono<String> getReview(int productId) {
        return this.remoteService("/demo05/review/" + productId);
    }

    private Mono<String> getPrice(int productId) {
        return this.remoteService("/demo05/price/" + productId);
    }

    private Mono<String> remoteService(String path) {
        return this.httpClient
                .get()
                .uri(path)
                .responseContent()
                .asString()
                .next();
    }
}
