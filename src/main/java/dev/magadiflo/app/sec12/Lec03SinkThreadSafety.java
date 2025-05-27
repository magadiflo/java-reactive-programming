package dev.magadiflo.app.sec12;

import dev.magadiflo.app.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;


public class Lec03SinkThreadSafety {
    private static final Logger log = LoggerFactory.getLogger(Lec03SinkThreadSafety.class);

    public static void main(String[] args) {
        demo2();
    }

    // Sink no es seguro para hilos
    private static void demo1() {
        Sinks.Many<Object> sink = Sinks.many().unicast().onBackpressureBuffer();
        Flux<Object> flux = sink.asFlux();

        ArrayList<Object> list = new ArrayList<>();
        flux.subscribe(list::add);

        for (int i = 0; i < 1000; i++) {
            int j = i;
            CompletableFuture.runAsync(() -> {
                sink.tryEmitNext(j); // No es seguro para hilos
            });
        }

        Util.sleepSeconds(2);
        log.info("Tamaño de lista: {}", list.size());
    }

    private static void demo2() {
        Sinks.Many<Object> sink = Sinks.many().unicast().onBackpressureBuffer();
        Flux<Object> flux = sink.asFlux();

        ArrayList<Object> list = new ArrayList<>();
        flux.subscribe(list::add);

        for (int i = 0; i < 1000; i++) {
            int j = i;
            CompletableFuture.runAsync(() -> {
                sink.emitNext(j, (signalType, emitResult) -> { //Sí es hilo seguro. Si queremos garantía en la entrega de nuestros mensajes
                    return Sinks.EmitResult.FAIL_NON_SERIALIZED.equals(emitResult);
                });
            });
        }

        Util.sleepSeconds(2);
        log.info("Tamaño del list: {}", list.size());
    }
}
