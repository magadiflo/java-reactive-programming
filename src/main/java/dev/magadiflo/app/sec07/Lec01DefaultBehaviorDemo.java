package dev.magadiflo.app.sec07;

import dev.magadiflo.app.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

public class Lec01DefaultBehaviorDemo {

    private static final Logger log = LoggerFactory.getLogger(Lec01DefaultBehaviorDemo.class);

    public static void main(String[] args) {
        Flux<Integer> flux = Flux.<Integer>create(fluxSink -> {
            for (int i = 1; i < 3; i++) {
                log.info("Generando: {}", i);
                fluxSink.next(i);
            }
            fluxSink.complete();
        }).doOnNext(value -> log.info("value: {}", value));

        Runnable runnable = () -> flux.subscribe(Util.subscriber("sub1"));
        Thread.ofPlatform().start(runnable);
    }
}
