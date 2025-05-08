package dev.magadiflo.app.sec05;

import dev.magadiflo.app.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

import java.time.Duration;

public class Lec04Delay {

    private static final Logger log = LoggerFactory.getLogger(Lec04Delay.class);

    public static void main(String[] args) {
        Flux.range(1, 4)
                .log()
                .delayElements(Duration.ofMillis(800))
                .subscribe(Util.subscriber());

        Util.sleepSeconds(4);
    }
}
