package dev.magadiflo.app.sec04;

import dev.magadiflo.app.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

import java.util.concurrent.atomic.AtomicInteger;

public class Lec08FluxGenerateSingleThread {
    private static final Logger log = LoggerFactory.getLogger(Lec08FluxGenerateSingleThread.class);

    public static void main(String[] args) {
        incorrectGenerate();
    }

    private static void correctGenerate() {
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

    private static void incorrectGenerate() {
        // ⚠️ Estado compartido entre hilos (aquí está el problema)
        AtomicInteger sharedState = new AtomicInteger(1);

        // La función generadora accede al estado compartido
        Runnable task = () -> {
            Flux.generate(synchronousSink -> {
                        int state = sharedState.getAndIncrement(); // ❌ Múltiples hilos incrementan el mismo contador
                        log.info("Emitiendo valor : {}", state);

                        synchronousSink.next("Valor -> " + state);

                        if (state >= 5) {
                            synchronousSink.complete();
                        }
                    })
                    .subscribe(Util.subscriber());
        };

        for (int i = 0; i < 3; i++) {
            new Thread(task, "Hilo-" + i).start();
        }
    }
}
