package dev.magadiflo.app.sec06;

import dev.magadiflo.app.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

import java.time.Duration;

public class Lec05HotReplayCachePublisher {
    private static final Logger log = LoggerFactory.getLogger(Lec05HotReplayCachePublisher.class);

    public static void main(String[] args) {
        Flux<Integer> stockFlux = stockStream()
                .replay(2)
                .autoConnect(0);

        Util.sleepSeconds(6);
        log.info("mar se unió");
        stockFlux.subscribe(Util.subscriber("mar"));

        Util.sleepSeconds(3);
        log.info("gab se unió");
        stockFlux.subscribe(Util.subscriber("gab"));

        Util.sleepSeconds(8);
    }

    private static Flux<Integer> stockStream() {
        return Flux.generate(synchronousSink -> synchronousSink.next(Util.faker().random().nextInt(10, 100)))
                .delayElements(Duration.ofSeconds(3))
                .doOnNext(price -> log.info("Emitiendo precio {}", price))
                .cast(Integer.class);
    }
}
