package dev.magadiflo.app.sec05;

import dev.magadiflo.app.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class Lec06ErrorHandling {
    private static final Logger log = LoggerFactory.getLogger(Lec06ErrorHandling.class);

    public static void main(String[] args) {
        onErrorContinue();
    }

    private static void onErrorReturn() {
        Flux.range(1, 10)
                .map(value -> value == 5 ? 5 / 0 : value) //Intencional
                .onErrorReturn(IllegalArgumentException.class, -1)
                .onErrorReturn(ArithmeticException.class, -2)
                .onErrorReturn(-3)
                .subscribe(Util.subscriber());
    }

    private static void onErrorResume() {
        Flux.range(1, 10)
                .map(value -> value == 5 ? 5 / 0 : value) //Intencional
                .onErrorResume(throwable -> fallback(throwable.getMessage()))
                .subscribe(Util.subscriber());
    }

    private static void onErrorResume2() {
        Flux.error(() -> new RuntimeException("Error de la fuente principal (intencional)"))
                .onErrorResume(ArithmeticException.class, throwable -> fallback(throwable))
                .onErrorResume(throwable -> fallback())
                .onErrorReturn(-5)
                .subscribe(Util.subscriber());

    }

    private static void onErrorComplete() {
        Mono.error(new RuntimeException("Error emitido intencionalmente"))
                .onErrorComplete()
                .subscribe(Util.subscriber());
    }

    private static void onErrorContinue() {
        Flux.range(1, 10)
                .map(value -> value == 5 ? 5 / 0 : value) //Intencional
                .onErrorContinue((throwable, value) -> {
                    log.error("Error ocurrido: {}", throwable.getMessage());
                    log.error("Valor que causó el error: {}", value);
                })
                .subscribe(Util.subscriber());
    }

    private static Mono<Integer> fallback(Throwable throwable) {
        log.error("Ocurrió un error: {}", throwable.getMessage());
        log.info("Generando valor aleatorio...");
        return Mono.fromSupplier(() -> Util.faker().random().nextInt(10, 100));
    }

    private static Flux<Integer> fallback(String message) {
        log.error("Mensaje del error: {}", message);
        log.info("Generando valor aleatorio..");
        return Flux.range(50, 5);
    }

    private static Flux<Integer> fallback() {
        return Flux.error(new IllegalArgumentException("Ocurrió un error (intencional)"));
    }

}
