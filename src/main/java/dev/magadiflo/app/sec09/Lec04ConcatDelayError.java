package dev.magadiflo.app.sec09;

import dev.magadiflo.app.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

import java.time.Duration;

public class Lec04ConcatDelayError {

    private static final Logger log = LoggerFactory.getLogger(Lec04ConcatDelayError.class);

    public static void main(String[] args) {
        demo1();
        Util.sleepSeconds(3);
    }

    private static void demo1() {
        Flux.concatDelayError(producer1(), producer3(), producer2())
                .subscribe(Util.subscriber());

    }

    private static Flux<Integer> producer1() {
        return Flux.just(1, 2, 3)
                .doOnSubscribe(subscription -> log.info("Subscribiendo al producer1"))
                .delayElements(Duration.ofMillis(10));
    }

    private static Flux<Integer> producer2() {
        return Flux.just(40, 50, 60)
                .doOnSubscribe(subscription -> log.info("Subscribiendo al producer2"))
                .delayElements(Duration.ofMillis(10));
    }

    private static Flux<Integer> producer3() {
        return Flux.error(new RuntimeException("Error en el producer3"));
    }
}
