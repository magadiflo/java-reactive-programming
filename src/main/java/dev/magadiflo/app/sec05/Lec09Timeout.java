package dev.magadiflo.app.sec05;

import dev.magadiflo.app.common.Util;
import reactor.core.publisher.Mono;

import java.time.Duration;

public class Lec09Timeout {
    public static void main(String[] args) {
        timeoutFallback();
        Util.sleepSeconds(5);
    }

    private static void timeoutDefaultBehavior() {
        getProductName()
                .timeout(Duration.ofSeconds(1)) //Como máximo esperamos 1 segundo, sino lanzamos el TimeoutException
                .subscribe(Util.subscriber());
    }

    private static void timeoutDefaultBehaviorWithErrorHandler() {
        getProductName()
                .timeout(Duration.ofSeconds(1))
                .onErrorReturn("Producto de respaldo")
                .subscribe(Util.subscriber());
    }

    private static void timeoutFallback() {
        getProductName()
                .timeout(Duration.ofSeconds(1), getFallback())
                .subscribe(Util.subscriber());
    }

    private static Mono<String> getProductName() {
        return Mono.fromSupplier(() -> Util.faker().commerce().productName())
                .delayElement(Duration.ofSeconds(3));
    }

    private static Mono<String> getFallback() {
        return Mono.fromSupplier(() -> "getFallback(): " + Util.faker().commerce().productName())
                .delayElement(Duration.ofSeconds(3));

    }
}
