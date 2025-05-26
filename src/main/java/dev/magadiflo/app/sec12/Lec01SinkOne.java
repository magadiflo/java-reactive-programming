package dev.magadiflo.app.sec12;

import dev.magadiflo.app.common.Util;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;

public class Lec01SinkOne {
    public static void main(String[] args) {
        demo2();
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
}
