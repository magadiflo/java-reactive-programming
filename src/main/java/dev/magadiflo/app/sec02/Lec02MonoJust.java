package dev.magadiflo.app.sec02;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

public class Lec02MonoJust {

    private static final Logger log = LoggerFactory.getLogger(Lec02MonoJust.class);

    public static void main(String[] args) {
        Mono<String> mono = Mono.just("Spanish");
        mono.subscribe(
                value -> log.info("recibido: {}", value),
                error -> log.info("error: {}", error.getMessage()),
                () -> log.info("¡completado!")
        );
    }
}
