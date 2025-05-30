package dev.magadiflo.app.sec04;

import dev.magadiflo.app.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

public class Lec07FluxGenerateUntil {
    private static final Logger log = LoggerFactory.getLogger(Lec07FluxGenerateUntil.class);

    public static void main(String[] args) {
        example1();
    }


    private static void example1() {
        Flux<String> generate = Flux.<String>generate(synchronousSink -> {
            String country = Util.faker().country().name();
            synchronousSink.next(country);
        });
        generate.takeUntil(country -> country.equalsIgnoreCase("peru"))
                .subscribe(Util.subscriber());
    }

    private static void example2() {
        Flux.<String>generate(synchronousSink -> {
            String country = Util.faker().country().name();
            synchronousSink.next(country);
            if (country.equalsIgnoreCase("peru")) {
                synchronousSink.complete();
            }

        }).subscribe(Util.subscriber());
    }
}
