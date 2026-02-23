package dev.magadiflo.app.sec12;

import dev.magadiflo.app.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;

public class Lec01SinkOne {
    private static final Logger log = LoggerFactory.getLogger(Lec01SinkOne.class);

    public static void main(String[] args) {
        demo1();
    }

    private static void demo1() {
        // 1. Creación del Sink
        Sinks.One<Object> sink = Sinks.one();

        // 2. Exposición como Mono y suscripción
        Mono<Object> mono = sink.asMono();
        mono.subscribe(Util.subscriber());

        // 3. Emisión manual de la señal
        sink.tryEmitValue("Hola");
//        sink.tryEmitEmpty();
//        sink.tryEmitError(new RuntimeException("Error cuando se usaba sink"));
    }

    private static void demo2() {
        Sinks.One<Object> sink = Sinks.one();
        Mono<Object> mono = sink.asMono();

        // Dos suscriptores escuchando el mismo flujo
        mono.subscribe(Util.subscriber("sam"));
        mono.subscribe(Util.subscriber("mike"));

        // Emitimos un solo valor
        sink.tryEmitValue("Hola");
    }

    private static void demo2_1() {
        Sinks.One<Object> sink = Sinks.one();
        Mono<Object> mono = sink.asMono();
        mono.subscribe(Util.subscriber("sam"));
        mono.subscribe(Util.subscriber("mike"));

        sink.tryEmitValue("Hola");

        Util.sleepSeconds(5);
        mono.subscribe(Util.subscriber("jake"));
    }

    private static void demo3() {
        Sinks.One<Object> sink = Sinks.one();
        Mono<Object> mono = sink.asMono();

        mono.subscribe(Util.subscriber());

        // Emite con normalidad el valor "Hola"
        sink.emitValue("Hola", (signalType, emitResult) -> {
            log.info("Hola");
            log.info(signalType.name());
            log.info(emitResult.name());

            return false;
        });

        // Falla la emisión porque ya se emitió anteriormente. Ahora sí se imprimirán los logs.
        sink.emitValue("Buenas", (signalType, emitResult) -> {
            log.info("Buenas");
            log.info(signalType.name());
            log.info(emitResult.name());

            return false;
        });
    }
}
