package dev.magadiflo.app.sec07;

import dev.magadiflo.app.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

public class Lec04VirtualThreads {

    private static final Logger log = LoggerFactory.getLogger(Lec04VirtualThreads.class);

    public static void main(String[] args) {
        System.setProperty("reactor.schedulers.defaultBoundedElasticOnVirtualThreads", "true");

        Flux<Integer> flux = Flux.<Integer>create(fluxSink -> {
                    for (int i = 1; i < 3; i++) {
                        log.info("Generando: {}", i);
                        fluxSink.next(i);
                    }
                    fluxSink.complete();
                })
                .doOnNext(value -> log.info("value: {}", value))
                .doFirst(() -> log.info("first-1-{}", Thread.currentThread().isVirtual()))
                .subscribeOn(Schedulers.boundedElastic())
                .doFirst(() -> log.info("first-2"));

        flux.subscribe(Util.subscriber());

        Util.sleepSeconds(2);
    }
}
