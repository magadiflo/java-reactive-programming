package dev.magadiflo.app.sec05;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

public class Lec05Subscribe {

    private static final Logger log = LoggerFactory.getLogger(Lec05Subscribe.class);

    public static void main(String[] args) {
        Flux.range(1, 5)
                .doOnNext(value -> log.info("recibido: {}", value))
                .doOnError(throwable -> log.error(throwable.getMessage()))
                .doOnComplete(() -> log.info("¡Se completó!"))
                .subscribe(); // Se subscribe y solicita una demanda ilimitada.
    }
}
