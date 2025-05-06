package dev.magadiflo.app.sec03.assignment;

import dev.magadiflo.app.common.AbstractHttpClient;
import reactor.core.publisher.Flux;

public class ExternalServiceClient extends AbstractHttpClient {

    public Flux<Integer> getPriceChanges() {
        return this.httpClient
                .get()
                .uri("/demo02/stock/stream")
                .responseContent()
                .asString()
                .map(Integer::parseInt);
    }

}
