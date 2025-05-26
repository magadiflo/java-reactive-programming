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
        Sinks.One<Object> sink = Sinks.one();
        Mono<Object> mono = sink.asMono();
        mono.subscribe(Util.subscriber());

        sink.tryEmitValue("Hola");
//        sink.tryEmitEmpty();
//        sink.tryEmitError(new RuntimeException("Error cuando se usaba sink"));
    }

    private static void demo2() {
        Sinks.One<Object> sink = Sinks.one();
        Mono<Object> mono = sink.asMono();
        mono.subscribe(Util.subscriber("sam"));
        mono.subscribe(Util.subscriber("mike"));

        sink.tryEmitValue("Hola");
    }

    private static void demo3() {
        Sinks.One<Object> sink = Sinks.one();
        Mono<Object> mono = sink.asMono();

        mono.subscribe(Util.subscriber());

        sink.emitValue("Hola", (signalType, emitResult) -> {
            log.info("Hola");
            log.info(signalType.name());
            log.info(emitResult.name());

            return false;
        });

        sink.emitValue("Buenas", (signalType, emitResult) -> {
            log.info("Buenas");
            log.info(signalType.name());
            log.info(emitResult.name());

            return false;
        });
    }
}
