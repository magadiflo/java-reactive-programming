package dev.magadiflo.app.sec10;

import dev.magadiflo.app.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.GroupedFlux;
import reactor.core.publisher.Mono;

import java.time.Duration;

public class Lec05GroupedFlux {
    private static final Logger log = LoggerFactory.getLogger(Lec05GroupedFlux.class);

    public static void main(String[] args) {
        demo3();
        Util.sleepSeconds(35);
    }

    private static void demo1() {
        Flux.range(1, 30)
                .delayElements(Duration.ofSeconds(1))
                .groupBy(value -> value % 2)
                .flatMap(Lec05GroupedFlux::processEvents)
                .subscribe(Util.subscriber());
    }

    private static void demo2() {
        Flux.range(1, 30)
                .delayElements(Duration.ofSeconds(1))
                .map(value -> value * 2)
                .groupBy(value -> value % 2)
                .flatMap(Lec05GroupedFlux::processEvents)
                .subscribe(Util.subscriber());
    }

    private static void demo3() {
        Flux.range(1, 30)
                .delayElements(Duration.ofSeconds(1))
                .map(value -> value * 2)
                .startWith(1)
                .groupBy(value -> value % 2)
                .flatMap(Lec05GroupedFlux::processEvents)
                .subscribe(Util.subscriber());
    }

    private static Mono<Void> processEvents(GroupedFlux<Integer, Integer> groupedFlux) {
        log.info("Flujo recibido para key: {}", groupedFlux.key());
        return groupedFlux
                .doOnNext(value -> log.info("key: {}, value: {}", groupedFlux.key(), value))
                .doOnComplete(() -> log.info("{} completado", groupedFlux.key()))
                .then();
    }
}
