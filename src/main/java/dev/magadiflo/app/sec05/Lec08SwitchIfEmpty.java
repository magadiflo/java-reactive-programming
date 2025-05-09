package dev.magadiflo.app.sec05;

import dev.magadiflo.app.common.Util;
import reactor.core.publisher.Flux;

public class Lec08SwitchIfEmpty {

    public static void main(String[] args) {
        Flux.range(1, 10)
                .filter(value -> value > 11) // Intencional
                .switchIfEmpty(fallback())
                .subscribe(Util.subscriber());
    }

    private static Flux<Integer> fallback() {
        return Flux.range(100, 3);
    }
}
