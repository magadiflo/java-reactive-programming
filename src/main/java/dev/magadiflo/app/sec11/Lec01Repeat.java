package dev.magadiflo.app.sec11;

import dev.magadiflo.app.common.Util;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.concurrent.atomic.AtomicInteger;

public class Lec01Repeat {
    public static void main(String[] args) {
        demo4();
        Util.sleepSeconds(10);
    }

    private static void demo1() {
        getCountryName()
                .repeat(2)
                .subscribe(Util.subscriber());
    }

    private static void demo2() {
        getCountryName()
                .repeat()
                .takeUntil(country -> country.equalsIgnoreCase("peru"))
                .subscribe(Util.subscriber());
    }

    private static void demo3() {
        AtomicInteger atomicInteger = new AtomicInteger(0);
        getCountryName()
                .repeat(() -> atomicInteger.incrementAndGet() < 3)
                .subscribe(Util.subscriber());
    }

    private static void demo4() {
        getCountryName()
                .repeatWhen(flux -> flux
                        .delayElements(Duration.ofSeconds(2))
                        .take(2))
                .subscribe(Util.subscriber());
    }

    private static Mono<String> getCountryName() {
        return Mono.fromSupplier(() -> Util.faker().country().name());
    }
}
