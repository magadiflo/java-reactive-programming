package dev.magadiflo.app.sec03;

import dev.magadiflo.app.common.Util;
import reactor.core.publisher.Flux;

public class Lec02MultipleSubscribers {
    public static void main(String[] args) {
        Flux<Integer> numbers = Flux.just(1, 2, 3, 4);

        numbers
                .filter(value -> value % 2 == 0)
                .subscribe(Util.subscriber("sub1"));

        numbers.subscribe(Util.subscriber("sub2"));
    }
}
