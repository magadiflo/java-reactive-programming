package dev.magadiflo.app.sec05;

import dev.magadiflo.app.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

import java.util.function.Function;
import java.util.function.UnaryOperator;

public class Lec10Transform {

    private static final Logger log = LoggerFactory.getLogger(Lec10Transform.class);

    public static void main(String[] args) {
        boolean isDebugEnabled = false;

        getCustomers()
                .transform(isDebugEnabled ? addDebugger() : Function.identity())
                .subscribe();
        log.info("");
        getPurchaseOrders()
                .transform(addDebugger())
                .subscribe();
    }

    private static Flux<Cusomer> getCustomers() {
        return Flux.range(1, 3)
                .map(value -> new Cusomer(value, Util.faker().name().firstName()));
    }

    private static Flux<PurchaseOrder> getPurchaseOrders() {
        return Flux.range(1, 3)
                .map(value -> new PurchaseOrder(Util.faker().commerce().productName(), value, value * 10));
    }

    private static <T> UnaryOperator<Flux<T>> addDebugger() {
        return flux -> flux
                .doOnNext(value -> log.info("recibido: {}", value))
                .doOnError(throwable -> log.error("error: {}", throwable.getMessage()))
                .doOnComplete(() -> log.info("¡Se completó!"));
    }

    record Cusomer(int id, String name) {
    }

    record PurchaseOrder(String productName, int price, int quantity) {
    }

}
