package dev.magadiflo.app.sec04;

import dev.magadiflo.app.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

public class Lec08FluxGenerateSingleThread {
    private static final Logger log = LoggerFactory.getLogger(Lec08FluxGenerateSingleThread.class);

    public static void main(String[] args) {
        Flux.generate(
                        () -> 1,
                        (state, sink) -> {
                            log.info("Emitiendo valor: {}", state);
                            sink.next("Valor -> " + state);

                            if (state == 5) {
                                sink.complete();
                            }
                            return state + 1; // actualiza el estado
                        }
                )
                .subscribe(Util.subscriber());
    }
}
