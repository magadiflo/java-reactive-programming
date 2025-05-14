package dev.magadiflo.app.sec07;

import dev.magadiflo.app.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

public class Lec02SubscribeOn {

    private static final Logger log = LoggerFactory.getLogger(Lec02SubscribeOn.class);

    public static void main(String[] args) {
        Flux<Integer> flux = Flux.<Integer>create(fluxSink -> {
            for (int i = 1; i < 3; i++) {
                log.info("Generando: {}", i);
                fluxSink.next(i);
            }
            fluxSink.complete();
        }).doOnNext(value -> log.info("value: {}", value));

        flux
                .doFirst(() -> log.info("first-1"))
                .subscribeOn(Schedulers.boundedElastic())
                .doFirst(() -> log.info("first-2"))
                .subscribe(Util.subscriber());

        Util.sleepSeconds(2);
    }
}
