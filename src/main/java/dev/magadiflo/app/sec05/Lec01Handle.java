package dev.magadiflo.app.sec05;

import dev.magadiflo.app.common.Util;
import reactor.core.publisher.Flux;

public class Lec01Handle {
    public static void main(String[] args) {
        Flux.range(1, 10)
                .handle((item, synchronousSink) -> {
                    switch (item) {
                        case 1 -> synchronousSink.next(-2);
                        case 4 -> {
                        }
                        case 7 -> synchronousSink.error(new RuntimeException("Lanzando error porque el valor es 7"));
                        default -> synchronousSink.next(item);
                    }
                })
                .cast(Integer.class)
                .subscribe(Util.subscriber());
    }
}
