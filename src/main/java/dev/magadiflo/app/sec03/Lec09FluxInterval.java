package dev.magadiflo.app.sec03;

import dev.magadiflo.app.common.Util;
import reactor.core.publisher.Flux;

import java.time.Duration;

public class Lec09FluxInterval {
    public static void main(String[] args) {
        Flux.interval(Duration.ofMillis(500)) // esto nunca se detendrá
                .map(i -> Util.faker().name().firstName())
                .subscribe(Util.subscriber());

        Util.sleepSeconds(5);
    }
}
