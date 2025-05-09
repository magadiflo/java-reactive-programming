package dev.magadiflo.app.sec05;

import dev.magadiflo.app.common.Util;
import reactor.core.publisher.Flux;

public class Lec02HandleUntilAssignment {
    public static void main(String[] args) {
        Flux.<String>generate(synchronousSink -> synchronousSink.next(Util.faker().country().name()))
                .handle((country, synchronousSink) -> {
                    synchronousSink.next(country);
                    if (country.equalsIgnoreCase("peru")) {
                        synchronousSink.complete();
                    }
                }).subscribe(Util.subscriber());
    }
}
