package dev.magadiflo.app.sec12;

import dev.magadiflo.app.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

import java.time.Duration;

public class Lec06MulticastDirectAllOrNothing {
    private static final Logger log = LoggerFactory.getLogger(Lec06MulticastDirectAllOrNothing.class);

    public static void main(String[] args) {
        demo1();
        Util.sleepSeconds(10);
    }

    private static void demo1() {
        System.setProperty("reactor.bufferSize.small", "16");

        Sinks.Many<Object> sink = Sinks.many().multicast().directAllOrNothing();
        Flux<Object> flux = sink.asFlux();

        flux.subscribe(Util.subscriber("sam"));
        flux.delayElements(Duration.ofMillis(200)).subscribe(Util.subscriber("mike"));

        for (int i = 1; i <= 100; i++) {
            Sinks.EmitResult emitResult = sink.tryEmitNext(i);
            log.info("item: {}, result: {}", i, emitResult);
        }
    }
}
