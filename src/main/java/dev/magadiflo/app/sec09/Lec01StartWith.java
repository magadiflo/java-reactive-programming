package dev.magadiflo.app.sec09;

import dev.magadiflo.app.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

import java.time.Duration;

// startWith, llama a varios publishers en un orden específico
public class Lec01StartWith {

    private static final Logger log = LoggerFactory.getLogger(Lec01StartWith.class);

    public static void main(String[] args) {
        producer1()
                .startWith(0, 1, 2, 3)
                .subscribe(Util.subscriber());

        Util.sleepSeconds(3);
    }

    private static Flux<Integer> producer1() {
        return Flux.just(4, 5, 6)
                .doOnSubscribe(subscription -> log.info("Subscribiendo al producer1"))
                .delayElements(Duration.ofMillis(10));
    }
}
