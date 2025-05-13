package dev.magadiflo.app.sec06;

import dev.magadiflo.app.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.ConnectableFlux;
import reactor.core.publisher.Flux;

import java.time.Duration;

public class Lec01ColdHot {
    private static final Logger log = LoggerFactory.getLogger(Lec01ColdHot.class);

    public static void main(String[] args) {
        hotPublisher2();
    }

    private static void coldPublisher() {
        Flux<Integer> coldFlux = Flux.range(1, 3);
        coldFlux.subscribe(Util.subscriber("sub1"));
        coldFlux.subscribe(Util.subscriber("sub2"));
    }

    private static void hotPublisher() {
        Flux<Long> hotFlux = Flux.interval(Duration.ofSeconds(1))
                .share(); // Convierte este Cold Publisher en Hot Publisher

        hotFlux.subscribe(Util.subscriber("sub1"));
        Util.sleepSeconds(2); // Esperamos 3 segundos antes de añadir el segundo suscriptor

        hotFlux.subscribe(Util.subscriber("sub2"));
        Util.sleepSeconds(4);
    }

    private static void hotPublisher2() {
        ConnectableFlux<Long> hot = Flux.interval(Duration.ofSeconds(1)).publish();
        hot.connect(); // Inicia la emisión, incluso sin suscriptores

        Util.sleepSeconds(3);

        hot.subscribe(Util.subscriber("sub1")); // Sub1 se une tarde

        Util.sleepSeconds(6);
    }
}
