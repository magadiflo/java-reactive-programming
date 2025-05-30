package dev.magadiflo.app.sec03;

import dev.magadiflo.app.common.Util;
import reactor.core.publisher.Flux;

import java.util.List;

public class Lec03FluxFromIterableOrArray {
    public static void main(String[] args) {
        List<String> letters = List.of("a", "b", "c");
        Flux.fromIterable(letters)
                .subscribe(Util.subscriber());

        Integer[] ages = {18, 20, 25};
        Flux.fromArray(ages)
                .subscribe(Util.subscriber());
    }
}
