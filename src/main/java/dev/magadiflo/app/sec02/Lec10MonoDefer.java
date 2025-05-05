package dev.magadiflo.app.sec02;

import dev.magadiflo.app.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

public class Lec10MonoDefer {

    private static final Logger log = LoggerFactory.getLogger(Lec10MonoDefer.class);

    public static void main(String[] args) {
        Mono<String> monoDefer = Mono.defer(() -> {
            log.info("Creando nuevo mono con defer...");
            return Mono.just(getNombre());
        });

        Util.sleepSeconds(1);
        monoDefer.subscribe(Util.subscriber("defer1"));

        Util.sleepSeconds(4);
        monoDefer.subscribe(Util.subscriber("defer2"));
    }

    private static String getNombre() {
        log.info("Generando nombre...");
        return Util.faker().name().firstName();
    }
}
