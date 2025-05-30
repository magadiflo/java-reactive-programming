package dev.magadiflo.app.sec03;

import dev.magadiflo.app.common.Util;
import reactor.core.publisher.Flux;

public class Lec05FluxRange {
    public static void main(String[] args) {
        Flux.range(3, 4)
                .map(i -> String.format("%d°: %s", i, Util.faker().name().firstName()))
                .subscribe(Util.subscriber());
    }
}
