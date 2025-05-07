package dev.magadiflo.app.sec04;

import dev.magadiflo.app.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

public class Lec08GenerateWithState {
    private static final Logger log = LoggerFactory.getLogger(Lec08GenerateWithState.class);

    public static void main(String[] args) {
        Flux.generate(
                () -> 0,
                (counter, synchronousSink) -> {
                    String country = Util.faker().country().name();
                    synchronousSink.next(country);
                    counter++;
                    if (counter == 10 || country.equalsIgnoreCase("peru")) {
                        synchronousSink.complete();
                    }
                    return counter;
                },
                counter -> log.info("Último valor del contador: {}", counter)
        ).subscribe(Util.subscriber());
    }
}
