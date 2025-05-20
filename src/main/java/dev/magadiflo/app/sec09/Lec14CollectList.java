package dev.magadiflo.app.sec09;

import dev.magadiflo.app.common.Util;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class Lec14CollectList {

    public static void main(String[] args) {
        demo1();
    }

    private static void demo1() {
        Flux.range(1, 10)
                .collectList()
                .subscribe(Util.subscriber());
    }

    private static void demo2() {
        Flux.range(1, 10)
                .concatWith(Mono.error(new RuntimeException("Concatenamos error")))
                .collectList()
                .subscribe(Util.subscriber());
    }
}
