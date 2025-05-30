package dev.magadiflo.app.sec12;

import dev.magadiflo.app.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

public class Lec04Multicast {
    private static final Logger log = LoggerFactory.getLogger(Lec04Multicast.class);

    public static void main(String[] args) {
        demo2();
    }

    private static void demo1() {
        //onBackPressureBuffer - cola limitada
        Sinks.Many<Object> sink = Sinks.many().multicast().onBackpressureBuffer();
        Flux<Object> flux = sink.asFlux();

        flux.subscribe(Util.subscriber("sam"));
        flux.subscribe(Util.subscriber("mike"));

        sink.tryEmitNext("Hola");
        sink.tryEmitNext("Buen día");

        Util.sleepSeconds(2);

        flux.subscribe(Util.subscriber("jake"));
        sink.tryEmitNext("Examen del curso");
    }

    private static void demo2() {
        Sinks.Many<Object> sink = Sinks.many().multicast().onBackpressureBuffer();
        Flux<Object> flux = sink.asFlux();

        sink.tryEmitNext("Hola");
        sink.tryEmitNext("Buen día");
        sink.tryEmitNext("¿Cómo están?");

        Util.sleepSeconds(2);

        flux.subscribe(Util.subscriber("sam"));
        flux.subscribe(Util.subscriber("mike"));
        flux.subscribe(Util.subscriber("jake"));

        sink.tryEmitNext("Examen del curso");
    }

}
