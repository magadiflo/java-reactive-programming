package dev.magadiflo.app.sec12;

import dev.magadiflo.app.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

import java.time.Duration;

public class Lec05MulticastDirectBestEffort {
    private static final Logger log = LoggerFactory.getLogger(Lec05MulticastDirectBestEffort.class);

    public static void main(String[] args) {
        demo3();
        Util.sleepSeconds(25);
    }

    private static void demo1() {
        System.setProperty("reactor.bufferSize.small", "16");

        Sinks.Many<Object> sink = Sinks.many().multicast().onBackpressureBuffer();
        Flux<Object> flux = sink.asFlux();

        flux.subscribe(Util.subscriber("sam")); // Este subscriber es muy rápido
        flux.delayElements(Duration.ofMillis(200)).subscribe(Util.subscriber("mike"));// Este subscriber es muy lento

        for (int i = 1; i <= 100; i++) {
            Sinks.EmitResult emitResult = sink.tryEmitNext(i);
            log.info("Item: {}, result: {}", i, emitResult);
        }
    }

    private static void demo2() {
        System.setProperty("reactor.bufferSize.small", "16");

        Sinks.Many<Object> sink = Sinks.many().multicast().directBestEffort(); // Centrarse en el subscriber rápido, ignora al subscriber lento
        Flux<Object> flux = sink.asFlux();

        flux.subscribe(Util.subscriber("sam"));
        flux.delayElements(Duration.ofMillis(200)).subscribe(Util.subscriber("mike"));

        for (int i = 1; i <= 100; i++) {
            Sinks.EmitResult emitResult = sink.tryEmitNext(i);
            log.info("item: {}, result: {}", i, emitResult);
        }
    }

    private static void demo3() {
        System.setProperty("reactor.bufferSize.small", "16");

        Sinks.Many<Object> sink = Sinks.many().multicast().directBestEffort();
        Flux<Object> flux = sink.asFlux();

        flux.subscribe(Util.subscriber("sam"));
        flux.onBackpressureBuffer().delayElements(Duration.ofMillis(200)).subscribe(Util.subscriber("mike"));

        for (int i = 1; i <= 100; i++) {
            Sinks.EmitResult emitResult = sink.tryEmitNext(i);
            log.info("item: {}, res: {}", i, emitResult);
        }
    }
}
