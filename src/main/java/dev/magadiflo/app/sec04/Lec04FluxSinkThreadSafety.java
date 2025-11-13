package dev.magadiflo.app.sec04;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

public class Lec04FluxSinkThreadSafety {

    private static final Logger log = LoggerFactory.getLogger(Lec04FluxSinkThreadSafety.class);

    public static void main(String[] args) {
        Flux<String> flux = Flux.create(fluxSink -> {
            Runnable task = () -> {
                for (int i = 0; i < 5; i++) {
                    fluxSink.next("Hilo -> " + Thread.currentThread().getName() + ", valor -> " + i);
                }
            };

            // Ejecutamos el mismo sink desde varios hilos
            for (int i = 0; i < 3; i++) {
                Thread.ofPlatform().start(task);
            }

            // Esperamos un poco para que los hilos terminen de emitir
            try {
                Thread.sleep(1000);         // Espera para que todos los hilos terminen
                fluxSink.complete();              // Marca el flujo como completado
            } catch (InterruptedException e) {
                fluxSink.error(e);
            }
        });

        flux.subscribe(value -> log.info("Recibido: {}", value));
    }
}
