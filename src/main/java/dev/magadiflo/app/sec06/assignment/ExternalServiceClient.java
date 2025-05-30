package dev.magadiflo.app.sec06.assignment;

import dev.magadiflo.app.common.AbstractHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

import java.util.Objects;

public class ExternalServiceClient extends AbstractHttpClient {

    private static final Logger log = LoggerFactory.getLogger(ExternalServiceClient.class);
    private Flux<Order> orderFlux;

    public Flux<Order> orderStream() {
        if (Objects.isNull(this.orderFlux)) {
            this.orderFlux = this.getOrderStream();
        }
        return this.orderFlux;
    }

    private Flux<Order> getOrderStream() {
        return this.httpClient
                .get()
                .uri("/demo04/orders/stream")
                .responseContent()
                .asString()
                .map(this::parseOrder)
                .doOnNext(order -> log.info("{}", order))
                .publish()
                .refCount(2);
    }

    private Order parseOrder(String message) {
        String[] split = message.split(":");
        return new Order(
                split[1],
                Integer.parseInt(split[2]),
                Integer.parseInt(split[3])
        );
    }

}
