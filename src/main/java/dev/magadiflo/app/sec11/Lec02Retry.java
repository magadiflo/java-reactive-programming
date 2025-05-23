package dev.magadiflo.app.sec11;

import dev.magadiflo.app.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.time.Duration;
import java.util.concurrent.atomic.AtomicInteger;

// El operador retry simplemente se vuelve a suscribir cuando ve señales de error
public class Lec02Retry {

    private static final Logger log = LoggerFactory.getLogger(Lec02Retry.class);

    public static void main(String[] args) {
        demo5();
        Util.sleepSeconds(10);
    }

    private static void demo1() {
        getCountryName()
                .retry(2)
                .subscribe(Util.subscriber());
    }

    private static void demo2() {
        getCountryName()
                .retryWhen(Retry.max(2))
                .subscribe(Util.subscriber());
    }

    private static void demo3() {
        getCountryName()
                .retryWhen(Retry.fixedDelay(2, Duration.ofSeconds(3)))
                .subscribe(Util.subscriber());
    }

    private static void demo4() {
        getCountryName()
                .retryWhen(Retry.fixedDelay(2, Duration.ofSeconds(3))
                        .doBeforeRetry(retrySignal -> log.info("Reintentando...")))
                .subscribe(Util.subscriber());
    }

    private static void demo5() {
        getCountryName()
                .retryWhen(Retry.fixedDelay(2, Duration.ofSeconds(3))
                        .filter(throwable -> RuntimeException.class.equals(throwable.getClass())))
                .subscribe(Util.subscriber());
    }

    private static Mono<String> getCountryName() {
        AtomicInteger atomicInteger = new AtomicInteger(0);
        return Mono.fromSupplier(() -> {
                    if (atomicInteger.incrementAndGet() < 3) {
                        throw new RuntimeException("Ocurrió un error al buscar un país");
                    }
                    return Util.faker().country().name();
                })
                .doOnError(throwable -> log.error("ERROR: {}", throwable.getMessage()))
                .doOnSubscribe(subscription -> log.info("suscribiéndose..."));

    }
}
