package dev.magadiflo.app.sec06;

import dev.magadiflo.app.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

import java.time.Duration;

public class Lec04HotAutoConnectPublisher {
    private static final Logger log = LoggerFactory.getLogger(Lec04HotAutoConnectPublisher.class);

    public static void main(String[] args) {
        Flux<String> movieFlux = movieStream()
                .publish().autoConnect(); // Casi igual que publish().refCount(1). NO se detiene cuando los suscriptores cancelan. Por lo tanto, comenzará a producir incluso para 0 suscriptores una vez iniciado.
        //.publish().autoConnect(0); // Con el valor 0, empezará a emitir datos sin esperar a que haya algún subscriptor suscrito.

        Util.sleepSeconds(2);
        movieFlux
                .take(4)
                .subscribe(Util.subscriber("mar"));

        Util.sleepSeconds(3);
        movieFlux
                .take(3)
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
