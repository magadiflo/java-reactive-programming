package dev.magadiflo.app.sec03;

import dev.magadiflo.app.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

public class Lec11FluxDefer {
    private static final Logger log = LoggerFactory.getLogger(Lec11FluxDefer.class);

    public static void main(String[] args) {
        // getName() se ejecuta solo una vez
        Flux<String> justFlux = Flux.just(getName());

        justFlux.subscribe(Util.subscriber("just"));
        justFlux.subscribe(Util.subscriber("just"));

        // getName() se ejecuta en cada suscripción
        Flux<String> deferFlux = Flux.defer(() -> Flux.just(getName()));

        deferFlux.subscribe(Util.subscriber("defer"));
        deferFlux.subscribe(Util.subscriber("defer"));
    }

    private static String getName() {
        log.info("Generando nombre...");
        return Util.faker().name().firstName();
    }
}
