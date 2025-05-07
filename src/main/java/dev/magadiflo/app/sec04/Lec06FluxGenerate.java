package dev.magadiflo.app.sec04;

import dev.magadiflo.app.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

public class Lec06FluxGenerate {
    private static final Logger log = LoggerFactory.getLogger(Lec06FluxGenerate.class);

    public static void main(String[] args) {
        example2();
    }

    private static void example1() {
        Flux.generate(synchronousSink -> {
            synchronousSink.next(1);
            synchronousSink.complete();
        }).subscribe(Util.subscriber());
    }

    private static void example2() {
        Flux.generate(synchronousSink -> {
            log.info("Invocado");
            synchronousSink.next(1);
        }).subscribe(Util.subscriber());
    }
}
