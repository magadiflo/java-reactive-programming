package dev.magadiflo.app.sec06;

import dev.magadiflo.app.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

import java.time.Duration;

public class Lec02HotSharePublisher {
    private static final Logger log = LoggerFactory.getLogger(Lec02HotSharePublisher.class);

    public static void main(String[] args) {
        Flux<String> movieFlux = movieStream()
                .share(); // Convierte este Cold Publisher en Hot Publisher

        Util.sleepSeconds(2);
        movieFlux.subscribe(Util.subscriber("mar"));

        Util.sleepSeconds(3);
        movieFlux
                .take(3) // simulando que solo ve 3 escenas (emisiones)
                .subscribe(Util.subscriber("gab"));

        Util.sleepSeconds(13);
    }

    private static Flux<String> movieStream() {
        return Flux.generate(
                        () -> {
                            log.info("Request recibido");
                            return 1;
                        }, (stateValue, synchronousSink) -> {
                            String scene = "escena de película %d".formatted(stateValue);
                            log.info("Ejecutando {}", stateValue);
                            synchronousSink.next(scene);
                            return ++stateValue;
                        }, stateValue -> log.info("Valor final del estado: {}", stateValue)
                )
                .take(10)
                .delayElements(Duration.ofSeconds(1))
                .cast(String.class);
    }
}
